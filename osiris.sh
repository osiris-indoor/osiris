#!/bin/sh

command -v mongo >/dev/null 2>&1 || { echo >&2 -e "\e[31mMongoDB is required but it is not installed.  Aborting.\e[0m"; exit 1; }

mongo --eval "db.stats()" >/dev/null 2>&1 # do a simple harmless command of some sort

RESULT=$?   # returns 0 if mongo eval succeeds

if [ $RESULT -ne 0 ]; then
    echo >&2 -e "\e[31mMongoDB is not running.  Aborting.\e[0m"
    exit 1
fi

cd osiris-map
java -Denv=local -jar target/osiris-map.jar server src/main/resources/profiles/local/EnvConf.yml