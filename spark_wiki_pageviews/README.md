## Data
Please download one or more files from this address and place them inside `data/pageviews`
https://dumps.wikimedia.org/other/pageviews/

NOTE: Leave the file(s) as is! Meaning, do not unzip them :)

## Showcase
### Top projects on Wikipeida 

#### How to download the first 3 days of January 2018 
```
wget https://dumps.wikimedia.org/other/pageviews/2018/2018-01/pageviews-2018{01..03}{01..31}-{00..23}0000.gz
```
This will give you `72 files` which require more than `10 GB` of storage.


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