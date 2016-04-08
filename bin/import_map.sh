#!/bin/bash


# imports a map to OSIRIS DB
#
# BEGIN SCRIPT

usage()
{
cat << EOF

Usage: $0  <mapid> <mapfile>
    This script imports a map to a MongoDB database. The map is stored with an identificator to be used in an application

EOF
}

MAPNAME=
FILE=

if [ $# -eq 2 ]
then    
    #Check if we can write in the data base path
    if [[ $EUID -ne 0 ]]; then
        echo >&2 -e "\e[31mYou must be a super user to import a map\e[0m"
        exit 1
    fi
    
    #Check if the map exists
    if [ ! -f $2 ]; then
        echo >&2 -e "\e[31mFile ($2) not found!\e[0m"
        exit 1
    fi
    
    #Check if mongoDB is runnung
    mongo --eval "db.stats()" >/dev/null 2>&1 # do a simple harmless command of some sort

    RESULT=$?   # returns 0 if mongo eval succeeds

    if [ $RESULT -ne 0 ]; then
        echo >&2 -e "\e[31mMongoDB is not running.  Aborting.\e[0m"
        exit 1
    fi

    #Try to import the map
    echo -e "\e[32mImporting $2\e[0m"
    java -Denv=local -jar osiris-map-import.jar $1 "$2"
else
    usage
fi
