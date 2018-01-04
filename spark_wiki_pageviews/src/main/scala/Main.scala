import com.typesafe.config.ConfigFactory
import dataframe_helpers.Cleaning_Data
import spark_helpers.SessionBuilder

object Main {
  def main(args: Array[String]) {
    val env = args(0)
    val spark = SessionBuilder.buildSession(env)
    val demoFilePath = ConfigFactory.load().getString("spark.local.pageviewsPath.value")

    val wikiPageViewsDF = Cleaning_Data.create_dataframe(
      demoFilePath,
      spark
    )

    println("number of requests: \n")
    println(wikiPageViewsDF.count())

    import spark.implicits._
    wikiPageViewsDF.
      select($"project", $"requests").
      groupBy($"project").
      sum().
      orderBy($"sum(requests)".desc).
      show(30, false)
  }
}