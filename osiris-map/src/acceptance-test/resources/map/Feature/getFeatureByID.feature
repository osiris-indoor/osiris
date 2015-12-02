Feature: Get a feature of mongoDB

Rest URI: osiris/geolocation/territory/feature/{id} GET

As an application developer
In order to manage features of territory
I want to query features according to document ID

@deleteFeatures
Scenario: get a feature of type point with document ID  
	  Given I store a feature of type point with latitude 50, longitude 140, altitude 160 and jsonObject with key "place" and value "burger" and applicationIdentifier "1"       
      When I invoke a GET to "osiris/geolocation/territory/feature" with id
      Then I receive a HTTP "OK"
      Then I invoke a GET to "osiris/geolocation/territory/feature" with Id to check and receive a "OK" with type point

@deleteFeatures      
Scenario: get a feature of type lineString with document ID         
      Given I store a feature of type lineString with latitude 50, longitude 140, altitude 160, latitude 24, longitude 14, altitude 98 and jsonObject with key "place" and value "burger" and applicationIdentifier "1"
      When I invoke a GET to "osiris/geolocation/territory/feature" with id
      Then I receive a HTTP "OK"
      Then I invoke a GET to "osiris/geolocation/territory/feature" with Id to check and receive a "OK" with type lineString

@deleteFeatures      
Scenario: get a feature of type polygon with document ID         
      Given I store a feature of type polygon with latitude 0, longitude 0, altitude 80, latitude 0, longitude 20, altitude 52, latitude 20, longitude 20, altitude 14, latitude 20, longitude 0, altitude 82, latitude 5, longitude 5, altitude 52, latitude 5, longitude 15, altitude 80, latitude 15, longitude 15, altitude 52, latitude 15, longitude 5, altitude 80 and jsonObject with key "place" and value "market" and applicationIdentifier "1"
      When I invoke a GET to "osiris/geolocation/territory/feature" with id
      Then I receive a HTTP "OK"
      Then I invoke a GET to "osiris/geolocation/territory/feature" with Id to check and receive a "OK" with type polygon         
  
@deleteFeatures           
Scenario: get a feature with not exist ID                             
      When I invoke a GET to "osiris/geolocation/territory/feature/-1"      
      Then I receive a HTTP "NOT_FOUND"
             
      	
      
      
	