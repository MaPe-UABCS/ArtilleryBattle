#!/bin/sh
# (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧ Manu java runner for linux

# Ouput dir
mkdir -p bin

# Jars
mkdir -p lib
jars="lib/$(/usr/bin/ls -1 lib)"
jars=$(echo $jars | sed 's/ /:lib\//g')

# Compile and run ヾ(⌐■_■)ノ♪
javac -cp "src:${jars}" -d bin src/app/Main.java && java -cp "bin:${jars}" app.Main
