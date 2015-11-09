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

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.brutusin.commons.io.MetaDataInputStream;
import org.brutusin.json.annotations.DependentProperty;
import org.brutusin.json.annotations.IndexableProperty;
import org.brutusin.json.annotations.JsonProperty;

/**
 *
 * @author Ignacio del Valle Alles idelvall@brutusin.org
 */
public class TestClass {

    public enum Mode {
        mode1,
        mode2;
    }
    
    @JsonProperty(required = true, description = "A string", title = "a title", defaultJsonExp = "3", valuesMethod = "getStringValues")
    private String string;
    
    private Mode mode;

    private transient String nonSerializableString = "nonSerializableString";
    private transient boolean nonSerializableBoolean = true;

    public boolean isNonSerializableBoolean() {
        return nonSerializableBoolean;
    }

    public void setNonSerializableBoolean(boolean nonSerializableBoolean) {
        this.nonSerializableBoolean = nonSerializableBoolean;
    }

    private InputStream inputStream;
    private MetaDataInputStream metaDataInputStream;

    private File[] files;

    @IndexableProperty
    @DependentProperty(dependsOn = {"bolArr", "string"})
    @JsonProperty(required = true, description = "A aint", title = "a title aint", defaultJsonExp = "3")
    private Integer aint;

    @JsonProperty(defaultJsonExp = "[true,true]")
    private boolean[] bolArr;

    public InputStream getInputStream() {
        return inputStream;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private Map<String, Boolean> booleanMap;

    private TestClass2 tc;

    public static List getStringValues() {
        return Arrays.asList(new String[]{"2", "4"});
    }

    public String getNonSerializableString() {
        return nonSerializableString;
    }

    public void setNonSerializableString(String nonSerializableString) {
        this.nonSerializableString = nonSerializableString;
    }

    public Map<String, Boolean> getBooleanMap() {
        return booleanMap;
    }

    public void setBooleanMap(Map<String, Boolean> booleanMap) {
        this.booleanMap = booleanMap;
    }

    public Integer getAint() {
        return aint;
    }

    public void setAint(Integer aint) {
        this.aint = aint;
    }

    public MetaDataInputStream getMetaDataInputStream() {
        return metaDataInputStream;
    }

    public void setMetaDataInputStream(MetaDataInputStream metaDataInputStream) {
        this.metaDataInputStream = metaDataInputStream;
    }

    public String getString() {
        return string;
    }

    public boolean[] getBolArr() {
        return bolArr;
    }

    public void setBolArr(boolean[] bolArr) {
        this.bolArr = bolArr;
    }

    public void setString(String string) {
        this.string = string;
    }

    public TestClass2 getTc() {
        return tc;
    }

    public void setTc(TestClass2 tc) {
        this.tc = tc;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    private class TestClass2 {

        @JsonProperty(values = "[\"aaa\",\"bbb\"]")
        private String s;

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }
    }
}
