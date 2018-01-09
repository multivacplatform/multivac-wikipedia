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
--executor-cores 2 \
--executor-memory 2g \
--num-executors 10 \
--conf spark.dynamicAllocation.enabled=true \
--conf spark.conf.master.value="$MASTER" \
--conf spark.conf.inputPath.value="$INPUTPATH" \
--conf spark.conf.outputPath.value="$OUTPUTPATH" \
$JAR
