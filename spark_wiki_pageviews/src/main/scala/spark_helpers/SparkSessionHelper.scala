package spark_helpers

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession

object SparkSessionHelper {
  def buildSession(): SparkSession = {

    val sparkMaster = ConfigFactory.load().getString("spark.conf.master.value")

    println("Spark Master: ", sparkMaster)

    val spark: SparkSession = SparkSession.builder
      .appName("spark-wiki-pageviews")
      .master(sparkMaster)
      .config("spark.local.dir", "/tmp/spark-temp")
      .getOrCreate

    spark.sparkContext.setLogLevel("WARN")

    spark
  }

  def getSparkSession(): SparkSession ={
    SparkSession.builder().getOrCreate()
  }
}