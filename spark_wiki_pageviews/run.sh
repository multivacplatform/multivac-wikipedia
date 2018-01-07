#!/usr/bin/env bash

Y=$(date +'%Y')
M=$(date +'%m')
D=$(date +'%d')

now="$(date +'%d-%m-%Y')"
echo "today is $now"

echo "cleaning old downloads"
rm data/pageviews/*.gz
echo "old downloads has been cleaned"

echo "start downloading yesterda's pageviews"

#sample hourly file: https://dumps.wikimedia.org/other/pageviews/2018/2018-01/pageviews-20180107-000000.gz

for i in 0{0..9}0000 {10..23}0000 ; do
    wget -nc https://dumps.wikimedia.org/other/pageviews/$Y/$Y-$M/pageviews-$Y$M$D-$i.gz -P data/pageviews/
done

#
#printf "%02d " {0..23} ; echo
