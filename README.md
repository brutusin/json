#org.brutusin:json [![Build Status](https://api.travis-ci.org/brutusin/json.svg?branch=master)](https://travis-ci.org/brutusin/json) [![Maven Central Latest Version](https://maven-badges.herokuapp.com/maven-central/org.brutusin/json/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.brutusin/json/)

**Table of Contents:** 

- [org.brutusin:json](#)
  - [Maven dependency](#maven-dependency)
  - [APIs](#apis)
    - [JsonCodec](#jsoncodec)
  - [Data](#data)
    - [Object-JsonNode binding]
  - [Schema](#schema)
    - [Class-JsonSchema binding]
    - [Data validation](#data-validation)
    - [JSON Schema extension](#json-schema-extension)
  - [Path expressions]
    - [Projections] 

##Maven dependency 
```xml
<dependency>
    <groupId>org.brutusin</groupId>
    <artifactId>json</artifactId>
    <version>${json.version}</version>
</dependency>
```

Click [here](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.brutusin%22%20a%3A%22json%22) to see the latest available version released to the Maven Central Repository.

If you are not using maven and need help you can ask [here](https://github.com/brutusin/json/issues).

## JSON SPI

[JSON SPI](src/main/java/org/brutusin/json/spi) is a service provider interface ([SPI](http://en.wikipedia.org/wiki/Service_provider_interface)) that defines all the JSON-related functionality needed by the rest of Brutusin modules, allowing to use different pluggable implementations (service providers) and decoupling client modules from them. 

#### Supported annotations
The following annotations can be used to customize schema generation, and must be supported by all providers:
* [`@JsonProperty`](src/main/java/org/brutusin/json/annotations/JsonProperty.java). Lets specify standard schema properties like, default value, enumeration, title, description,...
* [`@IndexableProperty`](src/main/java/org/brutusin/json/annotations/IndexableProperty.java). Adds custom `"index":"index"` or `"index":"facet"` properties to the schema generated.

#### Validation tests for implementing providers

Add the following dependency to the provider pom:
```xml
<dependency>
     <groupId>org.brutusin</groupId>
     <artifactId>json</artifactId>
     <type>test-jar</type>
     <version>${json.version}</version>
     <scope>test</scope>
</dependency>
```
in order to extend the [predefined tests](https://github.com/brutusin/json/tree/master/src/test/java/org/brutusin/json/spi) and verify they are passed. 

See also:
* [ServiceLoader](http://docs.oracle.com/javase/6/docs/api/java/util/ServiceLoader.html) for more details.
* [json-codec-jackson](https://github.com/brutusin/json-codec-jackson), the default JSON service provider.

## Support bugs and requests
https://github.com/brutusin/json/issues

## Authors

- Ignacio del Valle Alles (<https://github.com/idelvall/>)

Contributions are always welcome and greatly appreciated!

##License
Apache License, Version 2.0
