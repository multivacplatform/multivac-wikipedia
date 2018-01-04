import com.typesafe.config.ConfigFactory
import dataframe_helpers.Cleaning_Data
import spark_helpers.SessionBuilder

object Main {
  def main(args: Array[String]) {
    val env = args(0)
    val spark = SessionBuilder.buildSession(env)



    var demoFilePath = ConfigFactory.load().getString("spark.local.pageviewsPath.value")

    Cleaning_Data.create_dataframe(
      demoFilePath,
      spark
    )
  }
}