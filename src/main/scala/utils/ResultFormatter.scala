package utils

import models.CompanyAnalysis

object ResultFormatter {

  /**
   * Formatting results of analysis into the list of the rows
   *
   * @param analysis analysis results
   * @return formatted rows
   */
  def format(analysis: CompanyAnalysis): List[String] = {
    val headerLines = List(
      "Analysis of companies (50+ workers):",
      "------------------------------------"
    )

    val companyLines = analysis.filteredCompanies.map { company =>
      f"${company.name}%-15s | " +
        f"Revenue to employee: ${company.revenuePerEmployee}%10.2f | " +
        f"Employees:  ${company.employees}"
    }

    val efficiencyLines = List(
      "------------------------------------",
      "Company with the highest revenue per employee: ",
      f"${analysis.mostEfficientCompany.name} " +
        f"(${analysis.mostEfficientCompany.revenuePerEmployee}%.2f per employee)"
    )

    headerLines ++ companyLines ++ efficiencyLines
  }
} 
