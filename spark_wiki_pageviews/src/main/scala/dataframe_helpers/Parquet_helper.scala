package dataframe_helpers
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object Parquet_helper {
  def saveDataFrameAsParquet(
                              wikiDF: DataFrame,
                              demoFilePath: String,
                              spark: SparkSession
                            ): Unit ={
    import spark.implicits._

    wikiDF
      .repartition($"year", $"month", $"day")
      .write
      .mode(SaveMode.Overwrite)
      .option("compression","snappy")
      .partitionBy("year", "month", "day")
      .parquet(demoFilePath)
  }

  def readParquetAsDataFrame(
                              demoFilePath: String,
                              spark: SparkSession
                            ): DataFrame ={
    spark.read.parquet(demoFilePath)
  }

}
