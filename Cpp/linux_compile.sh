#!/bin/sh
mkdir -p lc_release
g++ -std=c++11 -O2 Game_of_survival/*.cpp -o lc_release/gos.sh
