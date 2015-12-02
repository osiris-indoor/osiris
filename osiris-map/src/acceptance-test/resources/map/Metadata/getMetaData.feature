Feature: Get metadata of map

Rest URI: /osiris/geolocation/territory/map/metadata GET

As an application developer
In order to get metadata of map
I want to query metadata of map

@deleteMetadata
Scenario: get a metadata of map  
	 Given I have a map with appId "1" and metadata osmChecksum "osmChecksum", routingChecksum "routingChecksum", minLat "-1.0", minLon "-2.0", maxLat "-3.0" and maxLon "-4.0"
     When I invoke a GET metadata to "/osiris/geolocation/territory/map/metadata" and applicationIdentifier "1"
     Then I receive a HTTP "OK" 
     Then I verify metadata has same fields osmChecksum "osmChecksum", routingChecksum "routingChecksum", minLat "-1.0", minLon "-2.0", maxLat "-3.0" and maxLon "-4.0"

@deleteMetadata     
Scenario: get a metadata and not exist                                   
      When I invoke a GET metadata to "/osiris/geolocation/territory/map/metadata" and applicationIdentifier "1"    
      Then I receive a HTTP "NOT_FOUND"