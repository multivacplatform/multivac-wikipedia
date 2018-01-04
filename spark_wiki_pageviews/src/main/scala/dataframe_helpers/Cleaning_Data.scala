package dataframe_helpers

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._


object Cleaning_Data {

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
      .csv(filePath)
      .withColumn("year", Extract_Date_UDF.get_year_file_name(input_file_name)) //extract year from filename
      .withColumn("month", Extract_Date_UDF.get_month_file_name(input_file_name)) //extract month from filename
      .withColumn("day", Extract_Date_UDF.get_day_file_name(input_file_name)) //extract day from filename
      .withColumn("hour", Extract_Date_UDF.get_hour_file_name(input_file_name)) //extract hour from filename

    val wikiPageViewsDF = rawDF.select(
      rawDF("_c0").cast(StringType).as("project"),
      rawDF("_c1").cast(StringType).as("article"),
      rawDF("_c2").cast(IntegerType).as("requests"),
      rawDF("_c3").cast(IntegerType).as("bytes_served"),
      rawDF("year").cast(IntegerType),
      rawDF("month").cast(IntegerType),
      rawDF("day").cast(IntegerType),
      rawDF("hour").cast(IntegerType)
    )

    wikiPageViewsDF
  }
}
