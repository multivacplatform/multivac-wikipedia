package dataframe_helpers
import org.apache.spark.sql.functions.udf

object Extract_Date_UDF {
  def get_year_file_name = udf { fullPath: String =>
    val str = fullPath.split("/").last
    str.split("-")(1).toArray.slice(0, 4).mkString
  }
  def get_month_file_name = udf { fullPath: String =>
    val str = fullPath.split("/").last
    str.split("-")(1).toArray.slice(4, 6).mkString
  }
  def get_day_file_name = udf { fullPath: String =>
    val str = fullPath.split("/").last
    str.split("-")(1).toArray.slice(6, 8).mkString
  }
  def get_hour_file_name = udf { fullPath: String =>
    val str = fullPath.split("/").last
    str.split("-")(2).toArray.slice(0, 2).mkString
  }
}
