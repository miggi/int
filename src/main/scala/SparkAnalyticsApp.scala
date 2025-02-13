import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.DataFrame

/**
 * Data analysis based on Apache Spark
 */
object SparkAnalyticsApp extends App {

  val spark = SparkSession.builder()
    .appName("Company Analytics")
    .master("local[*]")
    .getOrCreate()

  // Program entrypoint
  args.headOption match {
    case Some(filename) =>
      try {
        analyzeCompanies(readCompanyData(filename))
      } catch {
        case e: Exception =>
          println(s"Error while processing file $filename: ${e.getMessage}")
      } finally {
        spark.stop()
      }

    case None =>
      println("Please set the path for the CSV file as an argument")
      spark.stop()
  }

  import spark.implicits._

  /**  Read data from CSV file */
  private def readCompanyData(path: String): DataFrame = {
    spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(path)
  }

  /**
   * Analysis of company data
   */
  private def analyzeCompanies(df: DataFrame): Unit = {
    val employeesLimit = 50

    val colRevenueEmployee = "revenuePerEmployee"
    val colCompany         = "CompanyName"
    val colEmployees       = "Employees"
    val colRevenue         = "Revenue"

    val revenuePerEmployee = df
      .withColumn(colRevenueEmployee, col(colRevenue) / col(colEmployees))
      .cache()

    val filteredCompanies = revenuePerEmployee
      .filter(col(colEmployees) >= employeesLimit)

    val mostEfficient = revenuePerEmployee
      .orderBy(col(colRevenueEmployee).desc)
      .first()

    filteredCompanies.select(
        col(colCompany),
        format_number(col(colRevenueEmployee), 2).as("Revenue per employee"),
        col(colEmployees).as("Employees")
      ).show()

    println(s"Company with biggest revenue per employee: ${mostEfficient.getAs[String]("CompanyName")} " +
      f"(${mostEfficient.getAs[Double](colRevenueEmployee)}%.2f per employee)")
  }
}
