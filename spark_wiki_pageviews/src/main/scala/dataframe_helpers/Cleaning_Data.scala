package dataframe_helpers

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._


object Cleaning_Data {

  def create_dataframe(
                  filePath: String,
                  spark: SparkSession
                ): Unit ={

    println("file path: ", filePath)

    val rawDF = spark.read
      .option("header", "false")
      .option("delimiter", " ")
      .option("inferSchema", true)
      .csv(filePath)
      .withColumn("year", Extract_Date_UDF.get_year_file_name(input_file_name))
      .withColumn("month", Extract_Date_UDF.get_month_file_name(input_file_name))
      .withColumn("day", Extract_Date_UDF.get_day_file_name(input_file_name))
      .withColumn("hour", Extract_Date_UDF.get_hour_file_name(input_file_name))

    rawDF.show(false)
  }
}
