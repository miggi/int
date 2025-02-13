package services

import models.{Company, CompanyAnalysis}

/**
 * Service for data analysis of companies
 */
object CompanyAnalyzer {

  /* Minimal employees count for filtration */
  private val MinEmployees = 50

  /**
   * Analyze list of the companies
   *
   * @param companies list of the companies for analysis
   * @return analysis result
   */
  def analyze(companies: List[Company]): CompanyAnalysis = {
    val filteredCompanies = companies
      .filter(_.employees >= MinEmployees)
      .sortBy(-_.revenuePerEmployee)

    CompanyAnalysis(
      filteredCompanies = filteredCompanies,
      mostEfficientCompany = companies.maxBy(_.revenuePerEmployee)
    )
  }
}
