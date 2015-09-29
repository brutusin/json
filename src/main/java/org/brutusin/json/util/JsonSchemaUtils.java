/*
 * Copyright 2015 Ignacio del Valle Alles idelvall@brutusin.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.brutusin.json.util;

import java.util.Iterator;
import org.brutusin.json.ParseException;
import org.brutusin.json.spi.JsonCodec;
import org.brutusin.json.spi.JsonNode;
import org.brutusin.json.spi.JsonSchema;

/**
 *
 * @author Ignacio del Valle Alles idelvall@brutusin.org
 */
public class JsonSchemaUtils {

    /**
     * Returns the type of the elements of map (an object with
     * additionalProperties of type specified) schema.
     *
     * @param schema
     * @return null if is not map schema
     */
    public static JsonNode.Type getMapValueType(JsonNode schema) {
        JsonNode.Type type = JsonNode.Type.valueOf(schema.get("type").asString().toUpperCase());
        JsonNode child;
        if (type == JsonNode.Type.OBJECT && (child = schema.get("additionalProperties")) != null) {
            return getFirstNonArrayValueType(child);
        }
        return null;
    }

    /**
     * Returns the type of the elements of an array.
     *
     * @param schema
     * @return null if is not map schema
     */
    public static JsonNode.Type getArrayValueType(JsonNode schema) {
        JsonNode.Type type = JsonNode.Type.valueOf(schema.get("type").asString().toUpperCase());
        JsonNode child;
        if (type == JsonNode.Type.ARRAY && (child = schema.get("items")) != null) {
            return getFirstNonArrayValueType(child);
        }
        return null;
    }

    /**
     * 
     * @param schema
     * @return 
     */
    public static JsonNode.Type getFirstNonArrayValueType(JsonNode schema) {
        JsonNode.Type type = JsonNode.Type.valueOf(schema.get("type").asString().toUpperCase());
        JsonNode child;
        if (type == JsonNode.Type.ARRAY && (child = schema.get("items")) != null) {
            return getFirstNonArrayValueType(child);
        } else {
            return type;
        }
    }
    
    /**
     * 
     * @param clazz
     * @param required
     * @param values
     * @return 
     */
    public static JsonSchema createSchema(Class clazz, String title, String description, boolean required, Object values) {
        JsonSchema schema = JsonCodec.getInstance().getSchema(clazz);
        StringBuilder sb = new StringBuilder("{");
        Iterator<String> properties = schema.getProperties();
        while (properties.hasNext()) {
            String prop = properties.next();
            if (prop.equals("required") || prop.equals("enum")) {
                continue;
            }
            if (sb.length() > 1) {
                sb.append(",");
            }
            sb.append("\"").append(prop).append("\"").append(":").append(schema.get(prop));
        }
        if (title!=null) {
            sb.append(",");
            sb.append("\"").append("title").append("\"").append(":").append("\"").append(title).append("\"");
        }
        if (description!=null) {
            sb.append(",");
            sb.append("\"").append("description").append("\"").append(":").append("\"").append(description).append("\"");
        }
        if (required) {
            sb.append(",");
            sb.append("\"").append("required").append("\"").append(":").append("true");
        }
        if (values != null) {
            sb.append(",");
            sb.append("\"").append("enum").append("\"").append(":").append(JsonCodec.getInstance().transform(values));
        }
        sb.append("}");
        try {
            return JsonCodec.getInstance().parseSchema(sb.toString());
        } catch (ParseException ex) {
            throw new AssertionError(ex);
        }
    }
}
