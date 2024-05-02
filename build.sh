#! /bin/bash

rm -rf ./build
mkdir ./build
mkdir ./build/classes
mkdir ./build/resources

cp -r ./src/main/resources ./build/

cpath=./build/classes:\
./src/main/java:\
./lib/jar/lwjgl.jar:\
./lib/jar/lwjgl-glfw.jar:\
./lib/jar/lwjgl-opengl.jar:\
./lib/jar/joml-1.10.5.jar:\
./lib/jar/lwjgl-stb.jar:\


javac -cp $cpath -d ./build/classes ./src/main/java/Main.java

#javac -cp ./src/main/java:./lib/jar/lwjgl.jar -d ./build ./src/main/java/learnlwjglone/engineTester/MainGameLoop.java


