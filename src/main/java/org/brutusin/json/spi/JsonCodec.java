/*
 * Copyright 2015 brutusin.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.brutusin.json.spi;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.brutusin.json.ParseException;
import org.brutusin.json.util.JsonNodeVisitor;

/**
 * Decouples application logic from JSON parsing providers.
 *
 * @author Ignacio del Valle Alles idelvall@brutusin.org
 */
public abstract class JsonCodec implements JsonDataCodec, JsonSchemaCodec, JsonStreamCodec {
    
    private static JsonCodec instance;
    private final ConcurrentHashMap<Type, JsonSchema> schemaCache = new ConcurrentHashMap();
    
    static {
        ServiceLoader<JsonCodec> sl = ServiceLoader.load(JsonCodec.class);
        Iterator<JsonCodec> it = sl.iterator();
        List<JsonCodec> instances = new ArrayList<JsonCodec>();
        while (it.hasNext()) {
            instances.add(it.next());
        }
        if (instances.isEmpty()) {
            throw new Error("No '" + JsonCodec.class.getSimpleName() + "' service provider found.");
        } else if (instances.size() > 1) {
            throw new Error("Multiple '" + JsonCodec.class.getSimpleName() + "' service providers found: " + instances);
        } else {
            instance = instances.get(0);
        }
    }
    
    @Override
    public final String getSchemaString(Type type, String title, String description) {
        String ret = getSchemaString(type);
        if (ret != null && (title != null || description != null)) {
            StringBuilder sb = new StringBuilder(ret.trim());
            if (description != null) {
                sb.insert(1, "\"description\":\"" + quoteAsUTF8(description) + "\",");
            }
            if (title != null) {
                sb.insert(1, "\"title\":\"" + quoteAsUTF8(title) + "\",");
            }
            ret = sb.toString();
        }
        return ret;
    }
    
    @Override
    public JsonSchema getSchema(Type type) {
        try {
            JsonSchema ret = schemaCache.get(type);
            if (ret == null) {
                ret = parseSchema(getSchemaString(type));
                schemaCache.putIfAbsent(type, ret);
            }
            return ret;
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public Map<String, InputStream> getStreams(JsonNode node) {
        final Map<String, InputStream> map = new HashMap<String, InputStream>();
        visit("$", node, new JsonNodeVisitor() {
            public void visit(String name, JsonNode node) {
                if (node.asStream() != null) {
                    map.put(name, node.asStream());
                }
            }
        });
        return map;
    }
    
    @Override
    public Integer getReferencedStreamCount(JsonNode node, final JsonSchema schema) {
        final AtomicInteger counter = new AtomicInteger();
        visit("$", node, new JsonNodeVisitor() {
            public void visit(String name, JsonNode node) {
                Expression exp = compile(name);
                JsonSchema sch = exp.projectSchema(schema);
                if (sch.getSchemaType() == JsonNode.Type.STRING) {
                    JsonNode format = sch.get("format");
                    if (format != null && "inputstream".equals(format.asString())) {
                        counter.incrementAndGet();
                    }
                }
            }
        });
        return counter.get();
    }
    
    private static void visit(String name, JsonNode node, JsonNodeVisitor visitor) {
        visitor.visit(name, node);
        if (node.getNodeType() == JsonNode.Type.OBJECT) {
            Iterator<String> properties = node.getProperties();
            while (properties.hasNext()) {
                String prop = properties.next();
                visit(name + "." + prop, node.get(prop), visitor);
            }
        } else if (node.getNodeType() == JsonNode.Type.ARRAY) {
            int size = node.getSize();
            for (int i = 0; i < size; i++) {
                visit(name + "[" + i + "]", node.get(i), visitor);
            }
        }
    }
    
    public final Expression compile(String expression) {
        return Expression.compile(expression);
    }
    
    public static JsonCodec getInstance() {
        return instance;
    }
}
