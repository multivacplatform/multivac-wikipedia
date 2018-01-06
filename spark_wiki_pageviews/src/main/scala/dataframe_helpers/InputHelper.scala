package dataframe_helpers

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._


object InputHelper {

  def create_dataframe(
                  filePath: String,
                  spark: SparkSession
                ): (DataFrame) ={

    println(s"file path: $filePath")
    //read the .gz files from `data/pageviews`
    val rawDF = spark.read
      .option("header", "false")
      .option("delimiter", " ")
      .option("inferSchema", true)
      .option("mode", "DROPMALFORMED")
      .csv(filePath)
      .withColumn("year", UDFHelper.get_year_file_name(input_file_name)) //extract year from filename
      .withColumn("month", UDFHelper.get_month_file_name(input_file_name)) //extract month from filename
      .withColumn("day", UDFHelper.get_day_file_name(input_file_name)) //extract day from filename
      .withColumn("hour", UDFHelper.get_hour_file_name(input_file_name)) //extract hour from filename
      .withColumn("timestamp", UDFHelper.get_timestamp_file_name(input_file_name)) //extract timestamp from filename

    val wikiPageViewsDF = rawDF.select(
      rawDF("_c0").cast(StringType).as("project"),
      rawDF("_c1").cast(StringType).as("article"),
      rawDF("_c2").cast(IntegerType).as("requests"),
      rawDF("_c3").cast(IntegerType).as("bytes_served"),
      rawDF("year").cast(IntegerType),
      rawDF("month").cast(IntegerType),
      rawDF("day").cast(IntegerType),
      rawDF("hour").cast(IntegerType),
      rawDF("timestamp").cast(TimestampType)
    )

    wikiPageViewsDF
  }
}
