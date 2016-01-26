Feature: Import a OSM file and transform in GeoJson format to mongoDB

process: com.bitmonlab.osiris.imports.map.MapImportMain

As an application developer
In order to import osm file
I want to store a osm file into mongoDB      

Scenario: store transform GeoJson data format from an not valid OSM file into mongoDB 
	   When I invoke a MapImportMain with "map_error.osm" and applicationIdentifier "1"
	   Then I receive a ParseMapException
      
Scenario: store transform GeoJson data format from an OSM file and transform in not valid Mongo GeoJson format
	   When I invoke a MapImportMain with "map_badgeometry.osm" and applicationIdentifier "1"	
      
Scenario: store transform GeoJson data format from an OSM file into mongoDB
	   When I invoke a MapImportMain with "map.osm" and applicationIdentifier "1"	                        