package dataframe_helpers
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object ParquetHelper {
  def saveDataFrameAsParquet(
                              wikiDF: DataFrame,
                              demoFilePath: String,
                              spark: SparkSession
                            ): Unit ={
    wikiDF
      .write
      .partitionBy("year", "month", "day")
      .mode(SaveMode.Overwrite)
      .option("compression","snappy")
      .parquet(s"$demoFilePath")

  }

  def readParquetAsDataFrame(
                              demoFilePath: String,
                              spark: SparkSession
                            ): DataFrame ={
    spark.read.parquet(demoFilePath)
  }

}
