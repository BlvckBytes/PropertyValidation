package me.blvckbytes.propertyvalidation.validatior

import kotlin.reflect.KProperty

class CompareToMinMax<T : Comparable<T>>(
  override val field: KProperty<T?>,
  override val fieldValue: T?,
  val min: T,
  val max: T
): ApplicableValidator<T> {

  override fun validate(): Boolean {
    if (fieldValue == null)
      return true

    return fieldValue >= min && fieldValue <= max
  }
}