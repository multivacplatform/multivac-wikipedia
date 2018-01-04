package dataframe_helpers
import org.apache.spark.sql.DataFrame

object Parquet_helper {
  def saveDataFrameAsParquet(
                              wikiDF: DataFrame,
                              demoFilePath: String
                            ): Unit ={

    wikiDF.write.mode("overwrite").parquet(demoFilePath)

  }

}
