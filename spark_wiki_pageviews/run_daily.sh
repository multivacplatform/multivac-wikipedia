##############################################################################
## Building Wikipedia Page Views #############################################
## This should be used everyday to construct yesterdays pageviews parquets  ##
##############################################################################
#!/usr/bin/env bash

Y=$(date +'%Y')
M=$(date +'%m')
D=$(date -v-1d +'%d')

now="$(date +'%d-%m-%Y')"
echo "today is $now"

yesterday="$(date -v-1d +%F)"
echo "yesterdy's date is $yesterday"

echo "YEAR: $Y MONTH: $M DAY: $D"

echo "cleaning old downloads"
rm data/pageviews/*.gz
echo "old downloads has been cleaned"

echo "start downloading yesterday's pageviews"

#sample hourly file: https://dumps.wikimedia.org/other/pageviews/2018/2018-01/pageviews-20180107-000000.gz
for i in 0{0..9}0000 {10..23}0000 ; do
    wget -nc https://dumps.wikimedia.org/other/pageviews/$Y/$Y-$M/pageviews-$Y$M$D-$i.gz -P data/pageviews/
done

