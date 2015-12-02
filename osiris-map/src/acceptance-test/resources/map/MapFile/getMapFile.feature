Feature: Get .map file from mongoDB

Rest URI: /osiris/geolocation/territory/map/file GET

As an application developer
In order to draw map from client
I want to get .map file  

Scenario: get .map file of application
      Given I have a map file with appId "1"
      When I invoke a GET to "osiris/geolocation/territory/map/file" and applicationIdentifier "1" to get .map file
      Then I receive a HTTP "OK"   
      
Scenario: get .map file of application without stored .map
     When I invoke a GET to "osiris/geolocation/territory/map/file" and applicationIdentifier "98761205478" to get .map file
     Then I receive a HTTP "NOT_FOUND"                    