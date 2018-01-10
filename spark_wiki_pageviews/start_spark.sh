#!/usr/bin/env bash

now="$(date +'%d-%m-%Y')"
echo "today is $now"

echo "fetch the latest changes from GitHub"

git -C ../ pull

echo "compile and making a fat JAR!"
sbt assembly -mem 2048

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

if [[ "$MASTER" == "yarn"* ]]; then
MODE="cluster"
elif [[ "$MASTER" == "local"* ]]; then
MODE="client"
fi

echo "$MASTER"
echo "$INPUTPATH"
echo "$OUTPUTPATH"
echo "$JAR"
echo "$MODE"

spark-submit \
--class "Main" \
--master "$MASTER" \
--deploy-mode "$MODE" \
--driver-memory 4g \
--executor-cores 5 \
--executor-memory 5g \
--conf spark.dynamicAllocation.enabled=true \
--conf spark.conf.master.value="$MASTER" \
--conf spark.conf.inputPath.value="$INPUTPATH" \
--conf spark.conf.outputPath.value="$OUTPUTPATH" \
$JAR
