#!/bin/sh
# (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧ Manu java runner for linux

# Ouput dir
mkdir -p bin
mkdir -p bin/img
mkdir -p bin/sounds
mkdir -p bin/fonts

# including assets
cp assets/img/* bin/img/
cp assets/sounds/* bin/sounds
cp assets/fonts/* bin/fonts

# Jars
mkdir -p src/lib
jars="src/lib/$(ls -1 src/lib)"
jars=$(echo $jars | sed 's/ /:lib\//g')

# Compile and run ヾ(⌐■_■)ノ♪
javac -cp "src:${jars}" -d bin src/app/Main.java && java -cp "bin:${jars}" app.Main
