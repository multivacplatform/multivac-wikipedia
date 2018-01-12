#!/usr/bin/env bash

now="$(date +'%d-%m-%Y')"
echo "today is $now"

#default value for Spark local mode
MASTER="local[4]"
INPUTPATH="data/pageviews/*.gz"
OUTPUTPATH="data/output/parquet"
JAR="target/scala-2.11/*.jar"
MODE="client"

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

echo "MASTER $MASTER"
echo "INPUT PATH $INPUTPATH"
echo "OUTPUT PATH $OUTPUTPATH"
echo "JAR $JAR"
echo "MODE $MODE"

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
