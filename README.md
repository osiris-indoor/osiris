# Osiris
Osiris is a solution providing indoor mapping and APIs to access indoor GIS data. It is targeted to small / medium projects (smart buildings and campuses) and is provided as a software package ready to be installed and used. 
Some of the Osiris's key features are the following:

- Building data is sent as a vector information, making easy the creation of applications representing your venues.
- Osiris provides an additional Points of Interest API which can be used to point external outdoor places or services.
- A batch process generates a map in Mapsforge format that can be used to display the outdoor maps in your application.

#Requirements
Osiris needs Maven to be built and MongoDB to store data

#Building Osiris
There is a build.sh script which creates all the libraries and the fatjars needed. After executing it, you will find a collection of jar files and scripts within the bin directory. You can use this folder as a sandbox for trying Osiris, if you want to customize your installation take a look at the configuration files EnvConf.yml and env.properties

First, get the code of the project cloning the repository or downloading the zip file. Using the first method:
```sh
git clone https://github.com/osiris-indoor/osiris.git
```
To build the project in linux, execute:
```sh
cd osiris
./build.sh
```
The script builds all the components and runs the unit tests for each of them.

#Running Osiris
There are two thhings to do in order to set an Osiris environment. First we need to import a map, second we will launch our Rest Services application. For both processes a MongoDB instance should be running.

1. First the map import command:

  The importer reads OpenStreeMap maps in .osm format. You can find some sample maps [here](https://github.com/osiris-indoor/sample-maps)
  To import a map called map.osm having MyMapId as identificator you can use
  ```sh
  cd bin
  sudo ./import_map.sh MyMapId /path_to_my_map/map.osm
  ```
  The first time you use the previous command the identificator will be created for the new map. Use again the same command to   update the map: the map will be rewriten with the same identificator.

2. Then the core services for building your apps:
  ```sh
  ./osiris.sh
  ```
  If everything goes well, you will see some debug information and fnially something like this:
  
  GET     /osiris/geolocation/territory/map/metadata (com.bitmonlab.osiris.api.map.rest.impl.MetaDataResourceImpl)  
  GET     /osiris/geolocation/territory/map/file (com.bitmonlab.osiris.api.map.rest.impl.MapFileResourceImpl)  
  DELETE  /osiris/geolocation/territory/feature/{idFeature} (com.bitmonlab.osiris.api.map.rest.impl.FeatureResourceImpl)  
  GET     /osiris/geolocation/territory/feature/{idFeature} (com.bitmonlab.osiris.api.map.rest.impl.FeatureResourceImpl)  
  POST    /osiris/geolocation/territory/feature (com.bitmonlab.osiris.api.map.rest.impl.FeatureResourceImpl)  
  PUT     /osiris/geolocation/territory/feature/{idFeature} (com.bitmonlab.osiris.api.map.rest.impl.FeatureResourceImpl)  
  POST    /osiris/geolocation/territory/search (com.bitmonlab.osiris.api.map.rest.impl.SearchResourceImpl)  

  Now you can try this HTTP petition in your browser
  
  http://localhost:8020/osiris/geolocation/territory/map/metadata
  
  If you see a JSON script you can go one step further and if you have imported a map (as explained in 1 ), then you will be able to query Osiris and retrieve information of your buildings.

#Android demo application
You can use our sample aplication for testing your maps. Just get the code from its [repository] (https://github.com/osiris-indoor/mapviewer-android) and follow the instrucions to compile it using your own server adress.

#Mapping
To create your maps follow thsi [instructions] (https://github.com/osiris-indoor/sample-maps/wiki/How-to-map-a-building) and check our sample maps [repository] (https://github.com/osiris-indoor/sample-maps)
