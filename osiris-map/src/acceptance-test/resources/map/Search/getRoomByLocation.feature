Feature: Get room by location

Rest URI: osiris/geolocation/territory/search/room GET

As an application developer
In order to locate 
I want to query room given a location

@deleteSecurityCredentials
Scenario: get room with a valid location 
	  Given I have a map with APPLICATIONID "1"
     When I invoke a GET to "osiris/geolocation/territory/search/room" with appId "1", longitude -0.3343816, latitude 39.4771828 and floor 0
      Then I receive a HTTP "OK"
      Then returned room is "Experimental"

@deleteSecurityCredentials
Scenario: get room with a invalid location        
      Given I have a map with APPLICATIONID "1"
      When I invoke a GET to "osiris/geolocation/territory/search/room" with appId "1", longitude 39.4771567, latitude -0.3344323 and floor 0
      Then I receive a HTTP "INTERNAL_SERVER_ERROR"