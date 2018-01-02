# multivac-wikipedia
Wonderful reusable codes, libraries and scripts to process Wikipedia dumps (page content, page views, etc.) by using Apache Spark (SQL, ML, and GraphX).

## spark_wiki_pageviews
This repo represents:
* downloading the latest hour of wikipedia page views (script)
* cleaning up and putting the data in appropriate columns (Spark DataFrame)
* save as partitioned parquets (Spark DataFrame)

## Testing Environment

* Spark 2.2 Local / IntelliJ
* Spark 2.2 / Cloudera CDH 5.13 / YARN (cluster - client)

## Code of Conduct

This, and all github.com/multivacplatform projects, are under the [Multivac Platform Open Source Code of Conduct](https://github.com/multivacplatform/code-of-conduct/blob/master/code-of-conduct.md). Additionally, see the [Typelevel Code of Conduct](http://typelevel.org/conduct) for specific examples of harassing behavior that are not tolerated.

## Copyright and License

Code and documentation copyright (c) 2017 [ISCPIF - CNRS](http://iscpif.fr). Code released under the [MIT license](https://github.com/multivacplatform/multivac-wikipedia/blob/master/LICENSE).
