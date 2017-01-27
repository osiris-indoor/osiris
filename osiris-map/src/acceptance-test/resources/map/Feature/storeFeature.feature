Feature: Store a feature in mongoDB

Rest URI: osiris/geolocation/territory/feature POST

As an application developer
In order to manage features of territory
I want to store a feature (point, linestring, polygon, ...)

@deleteSecurityCredentials
@deleteFeatures
Scenario: store a feature of point in application
      When I invoke a POST to "osiris/geolocation/territory/feature" of type point with latitude 50, longitude 140, altitude 160 and jsonObject with key "place" and value "burger"
      Then I receive a HTTP "OK"
      Then I invoke a GET to "osiris/geolocation/territory/feature" with Id to check and receive a "OK" with type point
  
@deleteSecurityCredentials      
@deleteFeatures
Scenario: store a feature of lineString in application
      When I invoke a POST to "osiris/geolocation/territory/feature" of type lineString with latitude 40, longitude 120, altitude 50, latitude 13, longitude 64, altitude 52 and jsonObject with key "street" and value "Fuencarral"
      Then I receive a HTTP "OK"
      Then I invoke a GET to "osiris/geolocation/territory/feature" with Id to check and receive a "OK" with type lineString

@deleteSecurityCredentials      
@deleteFeatures
Scenario: store a feature of polygon in application
      When I invoke a POST to "osiris/geolocation/territory/feature" of type polygon with latitude 0, longitude 0, altitude 80, latitude 0, longitude 20, altitude 52, latitude 20, longitude 20, altitude 14, latitude 20, longitude 0, altitude 82, latitude 5, longitude 5, altitude 52, latitude 5, longitude 15, altitude 80, latitude 15, longitude 15, altitude 52, latitude 15, longitude 5, altitude 80 and jsonObject with key "building" and value "School"
      Then I receive a HTTP "OK"
      Then I invoke a GET to "osiris/geolocation/territory/feature" with Id to check and receive a "OK" with type polygon
      
@deleteSecurityCredentials      
@deleteFeatures
Scenario: store a feature with no valid geometry
      When I invoke a POST to "osiris/geolocation/territory/feature" of type errorGeometry with key "place" and value "burger"
      Then I receive a HTTP "BAD_REQUEST"
              
@deleteSecurityCredentials
@deleteFeatures
Scenario: check latitude is between -90 and 90
	  When I invoke a POST to "osiris/geolocation/territory/feature" of type point with latitude 120, longitude 20, altitude 160 and jsonObject with key "place" and value "burger"
      Then I receive a HTTP "BAD_REQUEST"      

@deleteSecurityCredentials
@deleteFeatures
Scenario: check longitude is between -180 and 180
      When I invoke a POST to "osiris/geolocation/territory/feature" of type point with latitude 50, longitude 200, altitude 160 and jsonObject with key "place" and value "burger"
      Then I receive a HTTP "BAD_REQUEST"
