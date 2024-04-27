#! /bin/bash

rm -rf ./build
mkdir ./build

cpath=./build:\
./src/main/java:\
./lib/jar/lwjgl.jar:\
./lib/jar/lwjgl-glfw.jar:\
./lib/jar/lwjgl-opengl.jar:\


javac -cp $cpath -d ./build ./src/main/java/Main.java

#javac -cp ./src/main/java:./lib/jar/lwjgl.jar -d ./build ./src/main/java/learnlwjglone/engineTester/MainGameLoop.java


