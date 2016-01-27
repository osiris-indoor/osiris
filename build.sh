#!/bin/sh

command -v mvn >/dev/null 2>&1 || { echo >&2 -e "\e[31mmvn command is required but it is not installed.  Aborting.\e[0m"; exit 1; }


echo -e "\e[32mBuilding core libraries...\e[0m"
cd osiris-bitmonlab-core  
mvn clean install
STATUS=$?
if [ $STATUS -ne 0 ]; then
    echo -e "\e[31mBuild failed!\e[0m"
    exit 1;
fi

echo -e "\e[32mBuilding map common libraries...\e[0m"
cd ../osiris-map-commons  
mvn clean install
STATUS=$?
if [ $STATUS -ne 0 ]; then
    echo -e "\e[31mBuild failed!\e[0m"
    exit 1;
fi

echo -e "\e[32mBuilding map import services...\e[0m"
cd ../osiris-map-import  
mvn clean install -P FatJar
STATUS=$?
if [ $STATUS -ne 0 ]; then
    echo -e "\e[31mBuild failed!\e[0m"
    exit 1;
fi

echo -e "\e[32mBuilding core map services...\e[0m"
cd ../osiris-map-services-core  
mvn clean install
STATUS=$?
if [ $STATUS -ne 0 ]; then
    echo -e "\e[31mBuild failed!\e[0m"
    exit 1;
fi

echo -e "\e[32mBuilding Osiris services...\e[0m"
cd ../osiris-map
mvn clean install -P FatJar
STATUS=$?
if [ $STATUS -ne 0 ]; then
    echo -e "\e[31mBuild failed!\e[0m"
    exit 1;
fi

echo -e "\e[32mCopying files to bin directory...\e[0m"
cd ..
cp osiris-map-import/target/osiris-map-import.jar bin/
cp osiris-map/target/osiris-map.jar bin/
cp osiris-map/src/main/resources/profiles/local/EnvConf.yml bin/
cp osiris-map/src/main/resources/profiles/local/env.properties bin/