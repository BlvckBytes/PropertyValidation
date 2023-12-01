package me.blvckbytes.propertyvalidation.validatior

import kotlin.reflect.KProperty

class CompareToConstant<T : Comparable<T>>(
  override val field: KProperty<T?>,
  override val fieldValue: T?,
  val constant: T,
  val comparison: Comparison
): ApplicableValidator<T> {

  override fun validate(): Boolean {
    if (fieldValue == null)
      return true

    return comparison.apply(fieldValue, constant)
  }
}