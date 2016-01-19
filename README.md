# Osiris
Osiris is a solution providing indoor mapping and APIs to access indoor GIS data. It is targeted to small / medium projects (smart buildings and campuses) and is provided as a software package ready to be installed and used. 
Some of the Osiris's key features are the following:

- Building data is sent as a vector information, making easy the creation of applications representing your venues.
- Osiris provides an additional Points of Interest API which can be used to point external outdoor places or services.
- A batch process generates a map in Mapsforge format that can be used to display the outdoor maps in your application.

#Requirements
Osiris needs Maven to be built and MongoDB to store data

#Building Osiris
There is a build script which creates all the libraries and the fatjars needed.

First, get the code of the project cloning the repository or downloading the zip file. Using the first method:
```sh
git clone https://github.com/osiris-indoor/osiris.git
```
To build the project in linux, execute:
```sh
cd osiris-master
./build.sh
```
The script builds all the components and runs the unit tests for each of them.

#Running Osiris
There are two thhings to do in order to set an Osiris environment. First we need to import a map, second we will launch our Rest Services application. For both processes a MongoDB instance should be running.

1. First the map import command:

  To import a map called map.osm having MyMapId as identificator you can use
  ```sh
  cd osiris-map-import
  java -Denv=local -jar target/map-import.jar MyMapId /path_to_my_map/map.osm
  ```
  The first time you use the previous command the identificator will be created for the new map. Use again the same command to   update the map: the map will be rewriten with the same identificator.

2. Then the core services for building your apps:
  ```sh
  cd osiris-map
  java -Denv=local -jar target/osiris-map.jar server src/main/resources/profiles/local/EnvConf.yml
  ```
  After this you can start by uploading a map. Then you will be able to query Osiris and retrieve information of your buildings.

#Android demo application
You can use our sample aplication for testing your maps. Just get the code from its [repository] (https://github.com/osiris-indoor/mapviewer-android) and follow the instrucions to compile it using your own server adress.

#Mapping
To create your maps follow thsi [instructions] (https://github.com/osiris-indoor/sample-maps/wiki/How-to-map-a-building) and check our sample maps [repository] (https://github.com/osiris-indoor/sample-maps)
