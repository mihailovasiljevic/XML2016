@echo off

mkdir %1
cd %1
mkdir services
cd services
echo off> %1.client.services.js
cd ..
mkdir controllers
cd controllers
echo off> %1.client.controllers.js
cd ..
mkdir config
cd config
echo off> %1.client.routes.js
cd ..
mkdir views
echo off> %1.client.module.js
cd ..