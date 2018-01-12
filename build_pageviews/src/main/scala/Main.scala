import dataframe_helpers.{InputHelper, ParquetHelper}
import spark_helpers.SparkSessionHelper
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object Main {
  def main(args: Array[String]) {
    val spark = SparkSessionHelper.buildSession()
    val demoFilePath = ConfigFactory.load().getString("spark.conf.inputPath.value")

    val wikiPageViewsDF = InputHelper.create_dataframe(
      demoFilePath,
      spark
    )

    println("========================== \n")
    println("========================== \n")
    println("========================== \n")
    println("Before saving as a Parquet: \n")
    println("Number of rows in DataFrame: \n")
    println(wikiPageViewsDF.count())
    println("Schema of DataFrame: \n")
    wikiPageViewsDF.printSchema()

    println("Displaying 10 rows of DataFrame: \n")
    wikiPageViewsDF.show(10, false)

    val filteredByDateDF = wikiPageViewsDF
      .select("year", "month", "day")
      .groupBy("year", "month", "day")
      .count()

    println("Display top 10 imported dates for partitioning: \n")
    filteredByDateDF.show(10, false)


    //    wikiPageViewsDF
    //      .select($"project", $"requests")
    //      .groupBy($"project")
    //      .sum()
    //      .orderBy($"sum(requests)".desc)
    //      .show(30, false)

    // point to output of parquets
    val parguetPath = ConfigFactory.load().getString("spark.conf.outputPath.value")

    // save as a parquet with dynamic partitions / overwrite into partition
    filteredByDateDF.collect().map({ x =>
      println("overwriting partition", x(0), x(1), x(2))
      writeIntoPartition(x.getInt(0), x.getInt(1), x.getInt(2), wikiPageViewsDF, parguetPath, spark)
    })

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

  def writeIntoPartition(year: Int, month: Int, day: Int, wikiPageViewsDF: DataFrame, parguetPath:String, spark: SparkSession): Unit ={
    println(year, month, day)
    ParquetHelper.saveDataFrameAsParquet(year, month, day, wikiPageViewsDF, parguetPath, spark)
  }
}

