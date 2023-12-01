package me.blvckbytes.propertyvalidation.validatior

import kotlin.reflect.KProperty

class CompareToOther<T : Comparable<T>>(
  override val field: KProperty<T?>,
  override val fieldValue: T?,
  val other: KProperty<T?>,
  val otherValue: T?,
  val comparison: Comparison
) : ApplicableValidator<T> {

  override fun validate(): Boolean {
    return comparison.apply(fieldValue, otherValue)
  }
}