package dataframe_helpers
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object ParquetHelper {

  def saveDataFrameAsParquet(
                              year: Int,
                              month: Int,
                              day: Int,
                              wikiDF: DataFrame,
                              demoFilePath: String,
                              spark: SparkSession
                            ): Unit ={
    import spark.implicits._

    val partitionPath = demoFilePath + "/year=" + year + "/month=" + month + "/day=" + day

    println("daily partiton path ", partitionPath)
    val tempDF = wikiDF
      .filter($"year" === year)
      .filter($"month" === month)
      .filter($"day" === day)

//    tempDF.repartition(24, $"hour")
//    tempDF.rdd.partitions.foreach(print)
    println(tempDF.rdd.partitions.length)

    tempDF
      .write
      .partitionBy("hour") // not needed since we filter and overwrite into one partition
      .mode(SaveMode.Overwrite)
      .option("compression","snappy")
      .parquet(s"$partitionPath")
  }

  def readParquetAsDataFrame(
                              demoFilePath: String,
                              spark: SparkSession
                            ): DataFrame ={
    spark.read.parquet(demoFilePath)
  }
}