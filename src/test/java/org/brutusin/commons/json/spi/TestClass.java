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
package org.brutusin.commons.json.spi;

import java.util.Map;
import org.brutusin.commons.json.annotations.IndexableProperty;
import org.brutusin.commons.json.annotations.JsonProperty;

/**
 *
 * @author Ignacio del Valle Alles idelvall@brutusin.org
 */
class TestClass {

    @JsonProperty(required = true, description = "A string", title = "a title", defaultJsonExp = "3", values = "[\"2\",\"4\"]")
    private String string;

    @IndexableProperty
    @JsonProperty(required = true, description = "A aint", title = "a title aint", defaultJsonExp = "3")
    private Integer aint;

    @JsonProperty(defaultJsonExp = "[true,true]")
    private boolean[] bolArr;

    private Map<String, Boolean> booleanMap;

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
}
