Feature: Update a feature in mongoDB

Rest URI: osiris/geolocation/territory/feature/{idGeoPoint} PUT

As an application developer
In order to manage features of territory
I want to update a feature (point, linestring, polygon, ...)

@deleteFeatures
Scenario: update a feature of type point in application
	  Given I store a feature of type point with latitude 50, longitude 140, altitude 160 and jsonObject with key "place" and value "burger" and applicationIdentifier "1"
 	  When I invoke a PUT to "osiris/geolocation/territory/feature" of type point and idFeature with latitude 59, longitude 149, altitude 169 and jsonObject with key "place" and value "burger"
      Then I receive a HTTP "OK"
      Then I invoke a GET to "osiris/geolocation/territory/feature" with Id to check and receive a "OK" with type point
      
@deleteFeatures
Scenario: update a feature of type lineString in application
 	  Given I store a feature of type lineString with latitude 50, longitude 140, altitude 160, latitude 24, longitude 14, altitude 98 and jsonObject with key "place" and value "burger" and applicationIdentifier "1"
      When I invoke a PUT to "osiris/geolocation/territory/feature" of type lineString and idFeature with latitude 59, longitude 149, altitude 169, latitude 35, longitude 22, altitude 24 and jsonObject with key "place" and value "burger"
      Then I receive a HTTP "OK"
      Then I invoke a GET to "osiris/geolocation/territory/feature" with Id to check and receive a "OK" with type lineString
      
@deleteFeatures
Scenario: update a feature of type polygon in application
 	  Given I store a feature of type polygon with latitude 0, longitude 0, altitude 80, latitude 0, longitude 20, altitude 52, latitude 20, longitude 20, altitude 14, latitude 20, longitude 0, altitude 82, latitude 5, longitude 5, altitude 52, latitude 5, longitude 15, altitude 80, latitude 15, longitude 15, altitude 52, latitude 15, longitude 5, altitude 80 and jsonObject with key "place" and value "market" and applicationIdentifier "1"
      When I invoke a PUT to "osiris/geolocation/territory/feature" of type polygon and idFeature with latitude 50, longitude 0, altitude 80, latitude 80, longitude 0, altitude 52, latitude 80, longitude 50, altitude 14, latitude 50, longitude 50, altitude 82, latitude 60, longitude 10, altitude 52, latitude 70, longitude 10, altitude 80, latitude 70, longitude 30, altitude 52, latitude 60, longitude 30, altitude 80 and jsonObject with key "place" and value "garden"
      Then I receive a HTTP "OK"
      Then I invoke a GET to "osiris/geolocation/territory/feature" with Id to check and receive a "OK" with type polygon
      
@deleteFeatures
Scenario: update a feature with no valid geometry
	  Given I store a feature of type point with latitude 50, longitude 140, altitude 160 and jsonObject with key "place" and value "burger" and applicationIdentifier "1"
      When I invoke a PUT to "osiris/geolocation/territory/feature" of type errorGeometry with key "place" and value "burger"
      Then I receive a HTTP "BAD_REQUEST"  

@deleteFeatures
Scenario: check latitude is between -90 and 90
	  Given I store a feature of type point with latitude 58, longitude 148, altitude 168 and jsonObject with key "place" and value "burger" and applicationIdentifier "1"
	  When I invoke a PUT to "osiris/geolocation/territory/feature" of type point and idFeature with latitude 128, longitude 20, altitude 168 and jsonObject with key "place" and value "burger"
      Then I receive a HTTP "BAD_REQUEST"      

@deleteFeatures
Scenario: check longitude is between -180 and 180
	  Given I store a feature of type point with latitude 57, longitude 147, altitude 167 and jsonObject with key "place" and value "burger" and applicationIdentifier "1"
      When I invoke a PUT to "osiris/geolocation/territory/feature" of type point and idFeature with latitude 50, longitude 200, altitude 160 and jsonObject with key "place" and value "burger"
      Then I receive a HTTP "BAD_REQUEST"

@deleteFeatures      
Scenario: idFeature not found      
	  When I invoke a PUT to "osiris/geolocation/territory/feature/-1" of type point with latitude 50, longitude 140, altitude 160 and jsonObject with key "place" and value "burger"
      Then I receive a HTTP "NOT_FOUND"      