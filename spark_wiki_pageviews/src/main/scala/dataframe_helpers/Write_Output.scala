package dataframe_helpers

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.DataFrame

object Write_Output {

  def saveDataFrameAsParquet(
                            wikiDF: DataFrame
                            ): Unit ={
    val demoFilePath = ConfigFactory.load().getString("spark.local.parquestOutputPath.value")

    wikiDF.write.mode("overwrite").parquet(demoFilePath+"/parquet")

  }
}
