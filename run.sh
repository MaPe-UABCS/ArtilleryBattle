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
mkdir -p bin/lib
cp src/lib/*.jar bin/lib

# Compile and run ヾ(⌐■_■)ノ♪
javac -cp "src:src/lib/*" -d bin src/app/Main.java && java -cp "bin:bin/lib/*" app.Main
