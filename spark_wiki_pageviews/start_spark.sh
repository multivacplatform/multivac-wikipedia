#!/usr/bin/env bash

now="$(date +'%d-%m-%Y')"
echo "today is $now"

while getopts m:i:o:j: option
do
 case "${option}"
 in
 m) MASTER=${OPTARG};;
 i) INPUTPATH=${OPTARG};;
 o) OUTPUTPATH=${OPTARG};;
 j) JAR=${OPTARG};;
 esac
done

echo "$MASTER"
echo "$INPUTPATH"
echo "$OUTPUTPATH"
echo "$JAR"

spark-submit \
--class "Main" \
--master $MASTER \
--conf spark.conf.inputPath.value="$INPUTPATH" \
--conf spark.conf.outputPath.value="$OUTPUTPATH" \
$JAR
