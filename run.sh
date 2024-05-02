#! /bin/bash

cpath=./build/classes:\
./lib/jar/lwjgl.jar:\
./lib/jar/lwjgl-glfw.jar:\
./lib/jar/lwjgl-opengl.jar:\
./lib/jar/joml-1.10.5.jar:\
./lib/jar/lwjgl-stb.jar:\


java -Djava.library.path="./lib/native" -cp $cpath Main

