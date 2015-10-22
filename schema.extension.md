# `"$schema" : "http://brutusin.org/json/json-schema-spec"`

This document defines some extensions to the http://json-schema.org/draft-03/schema JSON schema version used by Brutusin modules 

##Path expressions
Path expressions allow referencing JSON data and schema subparts and traversing the JSON node tree. 

This document defines new expression semantics, supporting data and schema projections (wildcard expressions evaluating to multiple nodes), and also keeping explicit information of the schema structure, being useful to validate that a reusable expression is applied to a node of the same structure that the original one (this is the main point for not adopting [JsonPath](https://github.com/jayway/JsonPath) semantics).

| Operator                  | Applied to JsonNode  | Applied to JsonSchema
| :------------------------ | :------------------- |:-------------------- |
| `$`                       | The root node        | Schema of root node |
| `.<name>`                 | Dot-notated child    | Schema of child node
| `#`                       | Numeric wildcard. Selects all elements of an array | Schema of the array node
| `*`                       | String wildcard. Selects all properties of an object | Schema of the object node. Only valid in schemas having additionalProperties
| `['<name>']` | Bracket-notated child or children | Only valid in schemas having additionalProperties. Otherwise use dot-notation |                                 |
| `[<number>]` | number-th element in the array                                            |Schema of the element node
| `[$]` | Last element in the array | Schema of the element node

## Additional schema properties:
|Property|Supported values| Description
|--------|----------------|------------
|**`index`**|[`"index"`,`"facet"`]| For indexation purposes
|**`dependsOn`**|array of [path expressions](#path-expressions) strings| Used to state that this schema is dynamic and depends on the values of the instance document in a certain set of subparts represented by path expressions.
## Additional formats for `string` schemas:
|Format value|Description
|--------|-----------
|**`file`**| The instances of this schema are strings representing a file
|**`inputstream`** | The instances of this schema are strings referencing an attached input stream.
