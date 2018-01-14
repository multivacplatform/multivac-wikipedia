#!/usr/bin/env bash
echo "start downloading yesterday's pageviews"
./download_daily.sh
echo "start pulling latest code, cleaning and creating fat JAR"
./build_lib.sh
#change the following base on your environment: local / cluster
echo "start preparing everything for HDFS"
./prepare_hdfs.sh
echo "start Spark job on YARN cluster"
./start_spark.sh -m yarn -i hdfs:///tmp/wiki/*.gz -o hdfs:///path-to-output -j hdfs:///tmp/wiki/build_pageviews-assembly-0.1.jar