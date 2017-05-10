echo @off
REM mmvn command is required to build OSIRIS

echo "Building core libraries..."
cd osiris-bitmonlab-core  
call mvn clean install

echo "Building map common libraries..."
cd ..\osiris-map-commons  
call mvn clean install

echo "Building map import services..."
cd ..\osiris-map-import  
call mvn clean install -P FatJar

echo "Building core map services..."
cd ..\osiris-map-services-core  
call mvn clean install


echo "Building Osiris services..."
cd ..\osiris-map
call mvn clean install -P FatJar

echo "Building Osiris password tool..."
cd ..\osiris-encrypt-password
call mvn clean install -P FatJar

echo "Copying files to bin directory..."
cd ..
copy osiris-map-import\target\osiris-map-import.jar bin\
copy osiris-map\target\osiris-map.jar bin\
copy osiris-encrypt-password\target\osiris-encrypt-password.jar bin
copy osiris-map\src\main\resources\profiles\local\EnvConf.yml bin\
copy osiris-map\src\main\resources\profiles\local\env.properties bin\
