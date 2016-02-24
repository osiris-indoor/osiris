## Paths
### Store a feature
```
POST /osiris/geolocation/territory/feature
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|HeaderParameter|api_key|Application identifier|true|string||
|BodyParameter|body|Feature|true|FeatureDTO||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|success|FeatureDTO|
|400|Invalid input parameter (header)|No Content|


#### Consumes

* application/json

#### Produces

* application/json

#### Tags

* osirisgeolocationterritoryfeature

### Delete a feature
```
DELETE /osiris/geolocation/territory/feature/{idFeature}
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|HeaderParameter|api_key|Application identifier|true|string||
|PathParameter|idFeature|Feature identifier|true|string||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|204|Feature was deleted|No Content|
|404|Feature was not found|No Content|
|400|Invalid input parameter (header)|No Content|


#### Consumes

* application/json

#### Produces

* application/json

#### Tags

* osirisgeolocationterritoryfeature

### Update a feature
```
PUT /osiris/geolocation/territory/feature/{idFeature}
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|HeaderParameter|api_key|Application identifier|true|string||
|PathParameter|idFeature|Feature identifier|true|string||
|BodyParameter|body|Updated feature|true|FeatureDTO||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|404|Feature was not found|No Content|
|200|success|FeatureDTO|
|400|Invalid input parameter (header)|No Content|


#### Consumes

* application/json

#### Produces

* application/json

#### Tags

* osirisgeolocationterritoryfeature

### Get a feature by id
```
GET /osiris/geolocation/territory/feature/{idFeature}
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|HeaderParameter|api_key|Application identifier|true|string||
|PathParameter|idFeature|Feature identifier|true|string||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|404|Feature was not found|No Content|
|200|success|FeatureDTO|
|400|Invalid input parameter (header)|No Content|


#### Consumes

* application/json

#### Produces

* application/json

#### Tags

* osirisgeolocationterritoryfeature

### Get .map file
```
GET /osiris/geolocation/territory/map/file
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|HeaderParameter|api_key|Application identifier|true|string||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|404|.map file was not found|No Content|
|200|success|java.io.InputStream|
|400|Invalid input parameter (header)|No Content|


#### Tags

* osirisgeolocationterritorymapfile

### Get metadata of map
```
GET /osiris/geolocation/territory/map/metadata
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|HeaderParameter|api_key|Application identifier|true|string||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|success|MetaDataDTO|
|400|Metadata of map was not found|No Content|


#### Consumes

* application/json

#### Produces

* application/json

#### Tags

* osirisgeolocationterritorymapmetadata

### Get features according to query
```
POST /osiris/geolocation/territory/search
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|HeaderParameter|api_key|Application identifier|true|string||
|BodyParameter|body|Query|true|string||
|QueryParameter|layer|Layer|false|enum (ALL, MAP, FEATURES)|ALL|
|QueryParameter|pageIndex|Index of page|false|integer (int32)|0|
|QueryParameter|pageSize|Size of page|false|integer (int32)|20|
|QueryParameter|orderField|Order field|false|string||
|QueryParameter|order|Order|false|enum (ASC, DESC)|ASC|


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|success|FeatureDTO array|
|400|Query is not correct|No Content|


#### Consumes

* application/json

#### Produces

* application/json

#### Tags

* osirisgeolocationterritorysearch

