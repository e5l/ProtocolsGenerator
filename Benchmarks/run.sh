#!/usr/bin/env bash

filename=$(date +"%d%b%Y_%H:%M")
tar -zcvf "tests/test_$filename.tar.gz" src/ *.xml
echo "SAVE [DONE]"

path="tests/test_$filename.txt"

SCRIPTS=./*.xml
for file in $SCRIPTS
do
    echo "[START] $file" 
    echo "[START] $file"  >> $path
    mvn -q -f $file clean install
    java -jar target/benchmarks.jar MergeSort.* -wi 20 -i 20 -f 1 -t 1 >> $path
    echo "[DONE] $file"  >> $path
    echo "[DONE] $file"
done
