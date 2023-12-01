package me.blvckbytes.propertyvalidation.validatior

enum class Comparison(
  private val function: ComparisonFunction,
  val messagePart: String
) {
  EQUALS(object : ComparisonFunction {
    override fun <T : Comparable<T>> apply(a: T?, b: T?): Boolean {
      return a == b
    }
  }, "equal to"),
  NOT_EQUALS(object : ComparisonFunction {
    override fun <T : Comparable<T>> apply(a: T?, b: T?): Boolean {
      return a != b
    }
  }, "not equal to"),
  LESS_THAN(object : ComparisonFunction {
    override fun <T : Comparable<T>> apply(a: T?, b: T?): Boolean {
      return (a ?: return false) < (b ?: return false)
    }
  }, "less than"),
  LESS_THAN_OR_EQUALS(object : ComparisonFunction {
    override fun <T : Comparable<T>> apply(a: T?, b: T?): Boolean {
      return (a ?: return false) <= (b ?: return false)
    }
  }, "less than or equal to"),
  GREATER_THAN(object : ComparisonFunction {
    override fun <T : Comparable<T>> apply(a: T?, b: T?): Boolean {
      return (a ?: return false) > (b ?: return false)
    }
  }, "greater than"),
  GREATER_THAN_OR_EQUALS(object : ComparisonFunction {
    override fun <T : Comparable<T>> apply(a: T?, b: T?): Boolean {
      return (a ?: return false) >= (b ?: return false)
    }
  }, "greater than or equal to"),
  ;

  fun <T : Comparable<T>> apply(a: T?, b: T?): Boolean {
    return function.apply(a, b)
  }
}