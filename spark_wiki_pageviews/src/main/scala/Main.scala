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

    println("Before saving as a Parquet: \n")
    println("number of requests: \n")
    println(wikiPageViewsDF.count())
    wikiPageViewsDF.printSchema()

    import spark.implicits._

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
    Parquet_helper.saveDataFrameAsParquet(wikiPageViewsDF, parguetPath)


    spark.close()
  }
}