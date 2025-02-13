package utils

import scala.io.Source
import scala.util.Try
import models.Company

/**
 *  CSV Files reader with company data
 */
object CsvReader {
  /**
   * Reads company data from CSV file
   *
   * @param filename path to file
   * @return list of the companies
   */
  def readCompanies(filename: String): List[Company] = {
    Source.fromFile(filename)
      .getLines()
      .drop(1) // Skip header
      .flatMap(parseLine)
      .toList
  }

  /**
   * Parse CSV string to Company object
   *
   * @param line CSV row
   * @return Option of Company
   */
  private def parseLine(line: String): Option[Company] = {
    Try {
      val fields = line.split(",").map(_.trim)
      Company(
        name = fields(0),
        revenue = fields(1).toDouble,
        employees = fields(2).toInt
      )
    }.toOption
  }
}
