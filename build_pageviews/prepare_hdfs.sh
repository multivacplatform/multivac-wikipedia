#!/usr/bin/env bash

echo "cleaning /tmp/wiki on HDFS"
hadoop fs -rm -r -skipTrash /tmp/wiki
echo "creating /tmp/wiki on HDFS"
hadoop fs -mkdir /tmp/wiki/
echo "copying data/pageviews to /tmp/wiki/pageviews on HDFS"
hadoop fs -put data/pageviews/*.gz /tmp/wiki/
echo "copying target/scala-2.11/*.jar to /tmp/wiki on HDFS"
hadoop fs -put -f target/scala-2.11/*.jar /tmp/wiki/
echo "preparation has been done!"