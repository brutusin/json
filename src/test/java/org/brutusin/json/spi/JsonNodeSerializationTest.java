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
package org.brutusin.json.spi;

import org.brutusin.json.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ignacio del Valle Alles idelvall@brutusin.org
 */
public abstract class JsonNodeSerializationTest {
    
    private static final String SIMPLE_JSON  = "{\"name\":\"JsonNodeSerializationTest\"}";
    private static final String COMPLEX_JSON = "{\"node\":{\"name\":\"JsonNodeSerializationTest\"}}";

    @Test
    public void testSerializeSimple() throws ParseException {
        JsonNode node = JsonCodec.getInstance().parse(SIMPLE_JSON);
        assertEquals(node.toString(), JsonCodec.getInstance().transform(node));
    }

    @Test
    public void testSerializeComplex() throws ParseException {
        JsonNode node = JsonCodec.getInstance().parse(SIMPLE_JSON);
        Complex complex = new Complex();
        complex.setNode(node);
        assertEquals(COMPLEX_JSON, JsonCodec.getInstance().transform(complex));
    }
    
    @Test
    public void testDeserializeSimple() throws ParseException {
        JsonNode jsonNode = JsonCodec.getInstance().parse(SIMPLE_JSON, JsonNode.class);
        assertEquals(jsonNode.toString(), SIMPLE_JSON);
    }
    
    @Test
    public void testDeserializeComplex() throws ParseException {
        Complex complex = JsonCodec.getInstance().parse(COMPLEX_JSON, Complex.class);
        assertEquals(COMPLEX_JSON, JsonCodec.getInstance().transform(complex));
    }
}

class Complex {

    private JsonNode node;

    public JsonNode getNode() {
        return node;
    }

    public void setNode(JsonNode node) {
        this.node = node;
    }
}
