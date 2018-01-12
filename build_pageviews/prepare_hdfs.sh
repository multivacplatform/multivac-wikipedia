#!/usr/bin/env bash

hadoop fs -rm -r -skipTrash /tmp/wiki
hadoop fs -mkdir /tmp/wiki/
hadoop fs -put data/pageviews/* /tmp/wiki/
rm data/pageviews/*.gz
hadoop fs -put -f target/scala-2.11/*.jar /tmp/wiki/