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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.brutusin.commons.Pair;
import org.brutusin.commons.io.MetaDataInputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ignacio del Valle Alles idelvall@brutusin.org
 */
public abstract class DataCodecTest {

    public DataCodecTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testJsonPropertyAnnotationSupport() throws Exception {
        TestClass instance = new TestClass();
        instance.setAint(3);
        instance.setBolArr(new boolean[]{true, false});
        instance.setString("aaa");
        instance.setBooleanMap(new HashMap());
        instance.getBooleanMap().put("1", Boolean.TRUE);

        String json = JsonCodec.getInstance().transform(instance);
        TestClass instance2 = JsonCodec.getInstance().parse(json, TestClass.class);
        String json2 = JsonCodec.getInstance().transform(instance2);
        assertEquals(json, json2);
        assertEquals(instance.getAint(), instance2.getAint());
    }
    
    @Test
    public void testTransientNonSerialization() throws Exception {
        TestClass instance = new TestClass();
        String json = JsonCodec.getInstance().transform(instance);
        System.out.println(json);
        assert(!json.contains("nonSerializable"));
    }
    
    @Test
    public void testInputStream() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("META-INF/services/org.brutusin.json.spi.JsonCodec");
        TestClass instance = new TestClass();
        String json = JsonCodec.getInstance().transform(instance);
        assertTrue(!json.contains("\"inputStream\""));
        instance.setInputStream(is);
        json = JsonCodec.getInstance().transform(instance);
        assertTrue(json.contains("\"inputStream\":\"#1#"));
        JsonNode node = JsonCodec.getInstance().toJsonNode(instance);
        assertEquals(node.get("inputStream").asStream(), is);
        TestClass instance2 = JsonCodec.getInstance().load(node, TestClass.class);
        assertEquals(instance.getInputStream(), instance2.getInputStream());
        Map<String, InputStream> streams = new HashMap();
        streams.put(node.get("inputStream").asString(), is);
        JsonNode node2 = JsonCodec.getInstance().parse(node.toString(), streams);
        assertEquals(node2.get("inputStream").asStream(), is);
        Pair<TestClass, Integer> streamParsing = JsonCodec.getInstance().parse(node.toString(), TestClass.class, streams);
        TestClass instance3 = streamParsing.getElement1();
        assertEquals(instance3.getInputStream(), instance3.getInputStream());
        assertTrue(streamParsing.getElement2().equals(1));
    }
    
    @Test
    public void testMetadataInputStream() throws Exception {
        MetaDataInputStream mis = new MetaDataInputStream(getClass().getClassLoader().getResourceAsStream("META-INF/services/org.brutusin.json.spi.JsonCodec"), null, null, Long.MIN_VALUE);
        TestClass instance = new TestClass();
        String json = JsonCodec.getInstance().transform(instance);
        assertTrue(!json.contains("\"metaDataInputStream\""));
        instance.setMetaDataInputStream(mis);
        json = JsonCodec.getInstance().transform(instance);
        assertTrue(json.contains("\"metaDataInputStream\":\"#1#"));
        JsonNode node = JsonCodec.getInstance().toJsonNode(instance);
        assertEquals(node.get("metaDataInputStream").asStream(), mis);
        TestClass instance2 = JsonCodec.getInstance().load(node, TestClass.class);
        assertEquals(instance.getMetaDataInputStream(), instance2.getMetaDataInputStream());
        Map<String, InputStream> streams = new HashMap();
        streams.put(node.get("metaDataInputStream").asString(), mis);
        JsonNode node2 = JsonCodec.getInstance().parse(node.toString(), streams);
        assertEquals(node2.get("metaDataInputStream").asStream(), mis);
        Pair<TestClass, Integer> streamParsing = JsonCodec.getInstance().parse(node.toString(), TestClass.class, streams);
        TestClass instance3 = streamParsing.getElement1();
        assertEquals(instance3.getMetaDataInputStream(), instance3.getMetaDataInputStream());
        assertTrue(streamParsing.getElement2().equals(1));
    }
}
