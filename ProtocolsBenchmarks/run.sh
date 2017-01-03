#!/usr/bin/env bash

filename=$(date +"%d%b%Y_%H:%M")


./gradlew jar
echo "BUILD [DONE]"

tar -zcvf "tests/test_$filename.tar.gz" src/
echo "SAVE [DONE]"

java -jar build/libs/ProtocolsBenchmarks-1.0-SNAPSHOT.jar >> "tests/test_$filename.txt"
echo "[DONE]"
