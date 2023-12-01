package me.blvckbytes.propertyvalidation.validatior

interface ComparisonFunction {

  fun <T : Comparable<T>> apply(a: T?, b: T?): Boolean

}