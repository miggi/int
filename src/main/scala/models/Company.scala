package models

/**
 * Company data model
 */
case class Company(
                    name: String,
                    revenue: Double,
                    employees: Int
                  ) {
  /**
   * Calculates revenue per one employee
   */
  def revenuePerEmployee: Double = revenue / employees
}
