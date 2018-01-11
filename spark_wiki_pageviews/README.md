## Intro
##### There are 3 scripts:
##### 1. build_lib.sh:
This will clean your sbt, compile and create a fat JAR
##### 2. download_daily.sh:
This will download yesterday's hourly pageviews from Wikipedia
##### 2. start_spark.sh:
This will run the Spark job to clean, craete DataFrame and save it as dynamic partionned parquets

**NOTE:** Spark `write.parquet` function removes the entire existing partitions if it's in `Overwrite` mode instead of only overwriting requested partitions. To Avoid this situation, we will write data into each partition separately by filtering data first. 
Therefore, even if you have downloaded more than one day it will still save them one by one into their own partitions dynamically.

I suggest to keep this order to run these scripts if you have a cron job (daily), but it's really up to you and your use case :)

## How To

### Run Spark Job
#### Create a fat JAR
NOTE: This and more are included in `build_lib.sh` so please use the script
```$xslt
sbt clean && sbt compile && sbt assembly -mem 2048
```
This will create a JAR in `target/scala-2.11/`

#### Run Spark job

Explaining `start_spark.sh` passing arguments:
```$xslt
-m Spark master : local[*] Or yarn
-i Path to input directory containing *.gz files
-o Path to output directory to save parquets
-j Path to fat JAR you build by sbt assembly
```
Example for Spark standalone (local):
```
./start_spark.sh
```
Same as:
```
./start_spark.sh \
-m local[*] \
-i data/pageviews/*.gz \
-o data/output/parquet \
-j target/scala-2.11/*.jar
```
You can chose the number of threads in local mode by changing `*` to `N` when N is the parallel threads you desire.

example: ```local[2]```

To run this on YARN cluster (you need to have your files/JAR on HDFS):
```
./start_spark.sh \
-m yarn \
-i hdfs://PATH-TO-FILES/*.gz \
-o hdfs://PATH-TO-OUTPUT/ \
-j hdfs://PATH-TO-JAR
```

## Data
If you don't want to use the script to download daily pageviews or you just want to test, please download one or more files from this address and place them inside `data/pageviews`
https://dumps.wikimedia.org/other/pageviews/

NOTE: Leave the file(s) as is! Meaning, do not unzip them :)

## Showcase
### Top projects on Wikipeida 

#### How to download the first 3 days of January 2018 
```
wget https://dumps.wikimedia.org/other/pageviews/2018/2018-01/pageviews-201801{01..3}-{00..23}0000.gz
```
This will give you `72 files` which require more than `10 GB` of storage.
NOTE: The double digit range only works in new version of bash.

**Number of rows: 444,207,853**

**Sum of requests: 1,585,374,370**

```
+---------+-------------+
|project  |sum(requests)|
+---------+-------------+
|en.m     |417178457    |
|en       |375424248    |
|ja.m     |69891330     |
|de.m     |55251374     |
|es.m     |50549305     |
|de       |47931091     |
|ru       |44956667     |
|ru.m     |42369846     |
|it.m     |41446545     |
|fr.m     |40933403     |
|fr       |30317916     |
|ja       |29329313     |
|es       |23625669     |
|zh       |20300463     |
|pt.m     |16967521     |
|it       |16128285     |
|zh.m     |15717737     |
|pl.m     |15189628     |
|pl       |13902447     |
|ar.m     |11783053     |
|commons.m|9584541      |
|fa.m     |9401941      |
|pt       |9204766      |
|nl.m     |8612291      |
|id.m     |7786081      |
|sv.m     |6520812      |
|nl       |5822244      |
|cs       |5225808      |
|www.wd   |5127440      |
|en.zero  |4929695      |
+---------+-------------+
only showing top 30 rows

```

![Number of Reuqests to Wikipedia Projects per Hour: First 3 days of January 2018](https://github.com/multivacplatform/multivac-wikipedia/blob/master/spark_wiki_pageviews/data/images/wiki-pageviews-january2018.png)

#### Bigger demo: December 2017
```
wget https://dumps.wikimedia.org/other/pageviews/2017/2017-12/pageviews-201712{01..31}-{00..23}0000.gz
```
This will give you `744 files` which require more than `36 GB` of storage.
NOTE: The double digit range only works in new version of bash.


**Number of rows: 4,529,669,792**

**Sum of requests: 15,278,050,138**

```
+---------+-------------+
|project  |sum(requests)|
+---------+-------------+
|en.m     |3784911811   |
|en       |3632828923   |
|ja.m     |578906226    |
|ru       |532707570    |
|es.m     |507966307    |
|de.m     |464186949    |
|de       |463264619    |
|ja       |379715338    |
|ru.m     |369216509    |
|fr.m     |361069999    |
|it.m     |328056166    |
|fr       |318697185    |
|es       |314862963    |
|zh       |206919597    |
|pt.m     |172852499    |
|it       |161235234    |
|zh.m     |149878515    |
|ar.m     |127827169    |
|pl       |125353954    |
|pl.m     |113954004    |
|pt       |108576418    |
|commons.m|105668930    |
|id.m     |89284575     |
|fa.m     |88369910     |
|nl.m     |77441421     |
|nl       |67609149     |
|sv.m     |57038991     |
|en.zero  |52135201     |
|www.wd   |48210254     |
|ar       |42496761     |
+---------+-------------+
only showing top 30 rows
```