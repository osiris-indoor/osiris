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

