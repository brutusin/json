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

import java.lang.reflect.Type;
import org.brutusin.json.ParseException;

/**
 * @author Ignacio del Valle Alles idelvall@brutusin.org
 */
public interface JsonDataCodec {

    public String quoteAsUTF8(String s);

    public <T> T parse(String json, Class<T> clazz) throws ParseException;
   
    public Object parse(String json, Type type) throws ParseException;

    public JsonNode parse(String json) throws ParseException;

    public <T> T load(JsonNode node, Class<T> clazz);
    
    public Object load(JsonNode node, Type type);

    public String transform(Object o);

    public JsonNode toJsonNode(Object o);

    public String prettyPrint(String json) throws ParseException;
}
