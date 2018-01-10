import dataframe_helpers.{InputHelper, ParquetHelper}
import spark_helpers.SparkSessionHelper

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.functions._

object Main {
  def main(args: Array[String]) {
    val spark = SparkSessionHelper.buildSession()
    val demoFilePath = ConfigFactory.load().getString("spark.conf.inputPath.value")

    val wikiPageViewsDF = InputHelper.create_dataframe(
      demoFilePath,
      spark
    )
    import spark.implicits._

    println("========================== \n")
    println("========================== \n")
    println("========================== \n")
    println("Before saving as a Parquet: \n")
    println("Number of rows in DataFrame: \n")
    println(wikiPageViewsDF.count())
    println("Schema of DataFrame: \n")
    wikiPageViewsDF.printSchema()

    println("Displaying DataFrame: \n")
    wikiPageViewsDF.show(20, false)

    println("How many days are imported: \n")
    wikiPageViewsDF
      .select(dayofmonth($"timestamp").as("day"))
      .groupBy("day")
      .count()
      .show(31, false)

//    wikiPageViewsDF
//      .select($"project", $"requests")
//      .groupBy($"project")
//      .sum()
//      .orderBy($"sum(requests)".desc)
//      .show(30, false)

    // point to output of parquets
    val parguetPath = ConfigFactory.load().getString("spark.conf.outputPath.value")

    // save as a parquet
    ParquetHelper.saveDataFrameAsParquet(wikiPageViewsDF, parguetPath, spark)

    // read saved parquets to test
    val pageViewsParquetDF = ParquetHelper.readParquetAsDataFrame(parguetPath, spark)

    println("========================== \n")
    println("========================== \n")
    println("========================== \n")
    println("After saving as a Parquet: \n")
    // pageViewsParquetDF.rdd.partitions.foreach(print)
    println("Number of rows in DataFrame: \n")
    println(pageViewsParquetDF.count())
    println("Schema of DataFrame: \n")
    pageViewsParquetDF.printSchema()

    pageViewsParquetDF.show(20, false)

    spark.close()
  }
}