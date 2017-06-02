# Osiris
Osiris is a solution providing indoor mapping and APIs to access indoor GIS data. It is targeted to small / medium projects (smart buildings and campuses) and is provided as a software package ready to be installed and used. 
Some of the Osiris's key features are the following:

- Building data is sent as a vector information, making easy the creation of applications representing your venues.
- Osiris provides an additional Points of Interest API which can be used to point external outdoor places or services.
- A batch process generates a map in Mapsforge format that can be used to display the outdoor maps in your application.

# Requirements
Osiris needs Maven to be built and MongoDB to store data

# Building Osiris
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

# Running Osiris
There are two things to do in order to set an Osiris environment. First we need to import a map, second we will launch our Rest Services application. For both processes a MongoDB instance should be running.

1. First the map import command:

  The importer reads OpenStreeMap maps in .osm format. You can find some sample maps [here](https://github.com/osiris-indoor/sample-maps)
  To import a map called map.osm having MyMapId as identifier you can use
  ```sh
  cd bin
  sudo ./import_map.sh MyMapId /path_to_my_map/map.osm
  ```
  The first time you use the previous command the identificator will be created for the new map. Use again the same command to   update the map: the map will be rewriten with the same identifier.

2. Now, we need to set up the security. 
 
    2.1. First we need to generate the string to use in the mongo collection as an encrypted password
    
      ```sh
      java -jar osiris-encrypt-password.jar your_password
      ```
      This will output: zZpRQvC79xErr9l0yz8Wwg==

    2.2. now, you have to create a collection in Mogo for the credentials. Note that we will use MyMapId as the identifier of the map / application:
    
    ```sh
    mongo
    >use osirisGeolocation
    >db.createCollection("credentials_app_MyMapId", {} );
    >db.credentials_app_MyMapId.insert({"_id" : "your_username", "password" : "zZpRQvC79xErr9l0yz8Wwg=="}
    >db.commit
    ```
    
    You can add as many users as you want using the db.credentials_app_MyMapId.insert command 

3. Then the core services for building your apps:
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

4. To call the services it is necessary to include the credentials as follows:
  
  The user name and password should be encoded in Base64 (ISO-8895-1) using the format user:password In our example your_username:your_password is  eW91cl91c2VybmFtZTp5b3VyX3Bhc3N3b3Jk and this credential has to be added to the Authorization header: 
  
  Authorization: Basic eW91cl91c2VybmFtZTp5b3VyX3Bhc3N3b3Jk


5. As an example using curl, you can call the search service this way:

  ```sh
  curl -i -H "api_key: MyMapId" -H "Content-Type: application/json" -H "Authorization: Basic eW91cl91c2VybmFtZTp5b3VyX3Bhc3N3b3Jk"  -X POST -d '{ $and: [ {properties.indoor:{$exists: true}} , {properties.indoor: "level"}] }' http://127.0.0.1:8020/osiris/geolocation/territory/search?layer=MAP&pageSize=2000
  ```
  
  If you imported a map (as explained in 1 ), you will see a JSON with information of your buildings.

# Leaflet demo applications
You can use our sample aplications for testing your maps. Just get the code from the exmaples [repository] (https://github.com/osiris-indoor/osiris-examples).

# Mapping
To create your maps follow thsi [instructions] (https://github.com/osiris-indoor/sample-maps/wiki/How-to-map-a-building) and check our sample maps [repository] (https://github.com/osiris-indoor/sample-maps)
