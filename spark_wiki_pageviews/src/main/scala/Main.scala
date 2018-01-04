import com.typesafe.config.ConfigFactory
import dataframe_helpers.{Cleaning_Data, Parquet_helper}

import spark_helpers.SessionBuilder
import org.apache.spark.sql.functions._

object Main {
  def main(args: Array[String]) {
    val env = args(0)
    val spark = SessionBuilder.buildSession(env)
    val demoFilePath = ConfigFactory.load().getString("spark.local.pageviewsPath.value")

    val wikiPageViewsDF = Cleaning_Data.create_dataframe(
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

    // save as a parquet
    val parguetPath = ConfigFactory.load().getString("spark.local.parquetOutPutPath.value")
    Parquet_helper.saveDataFrameAsParquet(wikiPageViewsDF, parguetPath, spark)
    val pageViewsParquetDF = Parquet_helper.readParquetAsDataFrame(parguetPath, spark)

    println("========================== \n")
    println("========================== \n")
    println("========================== \n")
    println("After saving as a Parquet: \n")
    println("number of requests: \n")
    println(pageViewsParquetDF.count())
    pageViewsParquetDF.printSchema()

    pageViewsParquetDF.show(20, false)

    spark.close()
  }
}