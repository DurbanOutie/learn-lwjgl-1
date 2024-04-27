#! /bin/bash

cpath=./build:\
./lib/jar/lwjgl.jar:\
./lib/jar/lwjgl-glfw.jar:\
./lib/jar/lwjgl-opengl.jar:

java -Djava.library.path="./lib/native" -cp $cpath Main
