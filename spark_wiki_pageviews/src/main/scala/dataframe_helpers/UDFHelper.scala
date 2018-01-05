package dataframe_helpers
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

object UDFHelper {

    // ex filename: pageviews-20180101-000000 - (not useful-year,month,day-hour)
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

  def get_year_file_name: UserDefinedFunction = udf { fullPath: String =>
  extractDateFromFileName(fullPath, "year")
  }
  def get_month_file_name: UserDefinedFunction = udf { fullPath: String =>
  extractDateFromFileName(fullPath, "month")
  }
  def get_day_file_name: UserDefinedFunction  = udf { fullPath: String =>
  extractDateFromFileName(fullPath, "day")
  }
  def get_hour_file_name: UserDefinedFunction  = udf { fullPath: String =>
  extractDateFromFileName(fullPath, "hour")
  }

  def get_timestamp_file_name: UserDefinedFunction  = udf { fullPath: String =>
    val viewYear = extractDateFromFileName(fullPath, "year")
    val viewmonth = extractDateFromFileName(fullPath, "month")
    val viewday = extractDateFromFileName(fullPath, "day")
    val viewhour = extractDateFromFileName(fullPath, "hour")
    val viewDate = viewYear + "-" + viewmonth + "-" + viewday + "T" + viewhour + ":00:00.000+0000"
    viewDate
  }

}