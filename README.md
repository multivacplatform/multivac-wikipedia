# multivac-wikipedia
Wonderful reusable codes, libraries and scripts to process Wikipedia dumps (page content, page views, etc.) by using Apache Spark (SQL, ML, and GraphX).

## spark_wiki_pageviews
This repo represents:
* TODO: downloading the latest hour of wikipedia page views (script)
* cleaning up and putting the data in appropriate columns (Spark DataFrame)
* save as partitioned parquets (Spark DataFrame)
### Showcase
#### Wikipedia PageViews in December 2017

**Number of rows: 4,529,669,792** (4.5 billion)

**Sum of requests: 15,278,050,138** (15.3 billion)

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
[Read more](https://github.com/multivacplatform/multivac-wikipedia/tree/master/spark_wiki_pageviews)



## Testing Environment

* Spark 2.2 Local / IntelliJ
* Spark 2.2 / Cloudera CDH 5.13 / YARN (cluster - client)

## Code of Conduct

This, and all github.com/multivacplatform projects, are under the [Multivac Platform Open Source Code of Conduct](https://github.com/multivacplatform/code-of-conduct/blob/master/code-of-conduct.md). Additionally, see the [Typelevel Code of Conduct](http://typelevel.org/conduct) for specific examples of harassing behavior that are not tolerated.

## Copyright and License

Code and documentation copyright (c) 2017 [ISCPIF - CNRS](http://iscpif.fr). Code released under the [MIT license](https://github.com/multivacplatform/multivac-wikipedia/blob/master/LICENSE).
