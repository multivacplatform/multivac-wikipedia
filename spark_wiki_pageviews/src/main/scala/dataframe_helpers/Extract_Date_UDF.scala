package dataframe_helpers
import org.apache.spark.sql.functions.udf

object Extract_Date_UDF {

  def extractDateFromFileName(fullPath: String, dateUnit: String):String = {
    val str = fullPath.split("/").last
    dateUnit match {
      case "hour"  =>
        str.split("-")(2).toArray.slice(0, 2).mkString
      case "day"  =>
        str.split("-")(1).toArray.slice(6, 8).mkString
      case "month"  =>
        str.split("-")(1).toArray.slice(4, 6).mkString
      case "year"  =>
        str.split("-")(1).toArray.slice(0, 4).mkString
    }
  }

  def get_year_file_name = udf { fullPath: String =>
    extractDateFromFileName(fullPath, "year")
  }
  def get_month_file_name = udf { fullPath: String =>
    extractDateFromFileName(fullPath, "month")
  }
  def get_day_file_name = udf { fullPath: String =>
    extractDateFromFileName(fullPath, "day")
  }
  def get_hour_file_name = udf { fullPath: String =>
    extractDateFromFileName(fullPath, "hour")
  }
  def get_timestamp_file_name = udf { fullPath: String =>
    val year = extractDateFromFileName(fullPath, "year")
    val month = extractDateFromFileName(fullPath, "month")
    val day = extractDateFromFileName(fullPath, "day")
    val hour = extractDateFromFileName(fullPath, "hour")
    val date = year + "-" + month + "-" + day + "T" + hour + ":00:00"
    date
  }
}