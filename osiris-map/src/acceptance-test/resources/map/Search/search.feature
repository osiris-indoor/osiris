Feature: Get features according to query

Rest URI: osiris/geolocation/territory/search POST

As an application developer
In order to manage features of territory
I want to query featurs according to query

@deleteFeatures
Scenario: get features within box of features
	Given I store a feature of type point with latitude 5, longitude 5, altitude 10 and jsonObject with key "place" and value "burger" and applicationIdentifier "2"
	Given I store a feature of type lineString with latitude 1, longitude 1, altitude 2, latitude 4, longitude 10, altitude 98 and jsonObject with key "place" and value "burger" and applicationIdentifier "2"
    Given I store a feature of type polygon with latitude 0, longitude 0, altitude 80, latitude 0, longitude 15, altitude 52, latitude 15, longitude 15, altitude 14, latitude 15, longitude 0, altitude 82, latitude 2, longitude 2, altitude 52, latitude 2, longitude 7, altitude 80, latitude 7, longitude 7, altitude 52, latitude 7, longitude 2, altitude 80 and jsonObject with key "place" and value "market" and applicationIdentifier "2"
    
    Given I store a feature of type point with latitude 60, longitude 60, altitude 160 and jsonObject with key "place" and value "mcdonalds" and applicationIdentifier "2"
    Given I store a feature of type point with latitude 14, longitude 8, altitude 160 and jsonObject with key "place" and value "bar" and applicationIdentifier "1"
	Given I store a feature of type lineString with latitude 13, longitude 9, altitude 160, latitude 11, longitude 4, altitude 98 and jsonObject with key "place" and value "cinema" and applicationIdentifier "1"
	
	Given I have a map with appId "1"
	
	Given I have a map with appId "2"
   
    When I invoke a POST to "osiris/geolocation/territory/search" to feature and query "{geometry: {$geoWithin: {$geometry: {type: 'Polygon', coordinates: [[ [-1,-1], [0,50], [50,50], [50,0], [-1,-1] ]] }}}}" and applicationIdentifier "2"
    Then I receive a HTTP "OK"
    And I check that 3 features are returned
    
@deleteFeatures
Scenario: get features within box of map
	Given I store a feature of type point with latitude 5, longitude 5, altitude 10 and jsonObject with key "place" and value "burger" and applicationIdentifier "2"
	Given I store a feature of type lineString with latitude 1, longitude 1, altitude 2, latitude 4, longitude 10, altitude 98 and jsonObject with key "place" and value "burger" and applicationIdentifier "2"
    Given I store a feature of type polygon with latitude 0, longitude 0, altitude 80, latitude 0, longitude 15, altitude 52, latitude 15, longitude 15, altitude 14, latitude 15, longitude 0, altitude 82, latitude 2, longitude 2, altitude 52, latitude 2, longitude 7, altitude 80, latitude 7, longitude 7, altitude 52, latitude 7, longitude 2, altitude 80 and jsonObject with key "place" and value "market" and applicationIdentifier "2"
    
    Given I store a feature of type point with latitude 60, longitude 60, altitude 160 and jsonObject with key "place" and value "mcdonalds" and applicationIdentifier "2"
    Given I store a feature of type point with latitude 14, longitude 8, altitude 160 and jsonObject with key "place" and value "bar" and applicationIdentifier "1"
	Given I store a feature of type lineString with latitude 13, longitude 9, altitude 160, latitude 11, longitude 4, altitude 98 and jsonObject with key "place" and value "cinema" and applicationIdentifier "1"
	
	Given I have a map with appId "1"
	
	Given I have a map with appId "2"
   
    When I invoke a POST to "osiris/geolocation/territory/search" to map and query "{geometry: {$geoWithin: {$geometry: {type: 'Polygon', coordinates: [ [ [ -4.658203125 , 39.926588421909436] , [ -4.658203125 , 40.83043687764923] , [ -2.39501953125 , 40.83043687764923] , [ -2.39501953125 , 39.926588421909436] , [ -4.658203125 , 39.926588421909436]]] }}}}" and applicationIdentifier "2"
    Then I receive a HTTP "OK"
    And I check that 11 features are returned
    
@deleteFeatures
Scenario: get features within box of all
	Given I store a feature of type point with latitude 5, longitude 5, altitude 10 and jsonObject with key "place" and value "burger" and applicationIdentifier "2"
	Given I store a feature of type lineString with latitude 1, longitude 1, altitude 2, latitude 4, longitude 10, altitude 98 and jsonObject with key "place" and value "burger" and applicationIdentifier "2"
    Given I store a feature of type polygon with latitude 0, longitude 0, altitude 80, latitude 0, longitude 15, altitude 52, latitude 15, longitude 15, altitude 14, latitude 15, longitude 0, altitude 82, latitude 2, longitude 2, altitude 52, latitude 2, longitude 7, altitude 80, latitude 7, longitude 7, altitude 52, latitude 7, longitude 2, altitude 80 and jsonObject with key "place" and value "market" and applicationIdentifier "2"
    
    Given I store a feature of type point with latitude 60, longitude 60, altitude 160 and jsonObject with key "place" and value "mcdonalds" and applicationIdentifier "2"
    Given I store a feature of type point with latitude 14, longitude 8, altitude 160 and jsonObject with key "place" and value "bar" and applicationIdentifier "1"
	Given I store a feature of type lineString with latitude 13, longitude 9, altitude 160, latitude 11, longitude 4, altitude 98 and jsonObject with key "place" and value "cinema" and applicationIdentifier "1"
	
	Given I have a map with appId "1"
	
	Given I have a map with appId "2"
   
    When I invoke a POST to "osiris/geolocation/territory/search" to all and query "{geometry: {$geoWithin: {$geometry: {type: 'Polygon', coordinates: [[ [-5,-5], [-5,50], [50,50], [50,-5], [-5,-5] ]] }}}}" and applicationIdentifier "2"
    Then I receive a HTTP "OK"
    And I check that 17 features are returned
    

Scenario: get features without features
	When I invoke a POST to "osiris/geolocation/territory/search" to all and query "{geometry: {$geoWithin: {$geometry: {type: 'Polygon', coordinates: [[ [-1,-1], [0,50], [50,50], [50,0], [-1,-1] ]] }}}}" and applicationIdentifier "9999"
	Then I receive a HTTP "OK"
	And I check that 0 features are returned
	
Scenario: get features error query
	When I invoke a getFeatureByQuery to all and query "{geometry: $geoWithin: {$geometry: {type: 'Polygon', coordinates: [[ [-1,-1], [0,50], [50,50], [50,0], [-1,-1] ]] }}}}" and applicationIdentifier "9999"
	Then I receive a HTTP "BAD_REQUEST"