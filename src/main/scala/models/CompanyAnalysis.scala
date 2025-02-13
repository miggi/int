package models

/**
 * Analysis result
 *
 * @param filteredCompanies list of the filtered companies
 * @param mostEfficientCompany company with the highest revenue per employee
 */
case class CompanyAnalysis(
                            filteredCompanies: List[Company],
                            mostEfficientCompany: Company
                          )
