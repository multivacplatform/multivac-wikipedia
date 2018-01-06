import com.typesafe.config.ConfigFactory
import dataframe_helpers.{InputHelper, ParquetHelper}

import spark_helpers.SparkSessionHelper
import org.apache.spark.sql.functions._

object Main {
  def main(args: Array[String]) {
    val env = args(0)
    val spark = SparkSessionHelper.buildSession(env)
    val demoFilePath = ConfigFactory.load().getString("spark.local.pageviewsPath.value")

    val wikiPageViewsDF = InputHelper.create_dataframe(
      demoFilePath,
      spark
    )
    import spark.implicits._

    println("========================== \n")
    println("========================== \n")
    println("========================== \n")
    println("Before saving as a Parquet: \n")
    println("number of requests: \n")
    println(wikiPageViewsDF.count())
    wikiPageViewsDF.printSchema()

    wikiPageViewsDF.show(20, false)

    wikiPageViewsDF.
      select(dayofmonth($"timestamp").as("day")).
      groupBy("day").
      count().
      show(31, false)

    wikiPageViewsDF.
      select($"project", $"requests").
      groupBy($"project").
      sum().
      orderBy($"sum(requests)".desc).
      show(30, false)

    // point to output of parquets
    val parguetPath = ConfigFactory.load().getString("spark.local.parquetOutPutPath.value")

    // save as a parquet
    ParquetHelper.saveDataFrameAsParquet(wikiPageViewsDF, parguetPath, spark)

    // read saved parquets to test
    val pageViewsParquetDF = ParquetHelper.readParquetAsDataFrame(parguetPath, spark)

    println("========================== \n")
    println("========================== \n")
    println("========================== \n")
    println("After saving as a Parquet: \n")

//    pageViewsParquetDF.rdd.partitions.foreach(print)

    println("number of requests: \n")
    println(pageViewsParquetDF.count())
    pageViewsParquetDF.printSchema()

    pageViewsParquetDF.show(20, false)

    spark.close()
  }
}