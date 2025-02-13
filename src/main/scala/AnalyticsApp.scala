import scala.util.{Failure, Success, Try}
import utils.CsvReader
import utils.ResultFormatter
import services.CompanyAnalyzer

/**
 * Main class that provides analysis.
 */
object AnalyticsApp extends App {

  args.headOption match {
    case Some(filename) => processFile(filename)
    case None => println("CSV file is missing as argument.")
  }

  /**
   * Process file with company data
   *
   * @param filename path to file
   */
  private def processFile(filename: String): Unit = {
    Try {
      val companies = CsvReader.readCompanies(filename)
      if (companies.isEmpty) {
        println("This program expects file with CSV.")
      } else {
        val analysis = CompanyAnalyzer.analyze(companies)
        ResultFormatter.format(analysis).foreach(println)
      }
    } match {
      case Success(_) => ()
      case Failure(e) => println(s"Error when processing file: ${e.getMessage}")
    }
  }
}
