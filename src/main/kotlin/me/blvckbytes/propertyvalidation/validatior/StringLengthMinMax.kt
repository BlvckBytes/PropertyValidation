package me.blvckbytes.propertyvalidation.validatior

import kotlin.reflect.KProperty

class StringLengthMinMax(
  override val field: KProperty<String?>,
  override val fieldValue: String?,
  val min: Int?,
  val max: Int?
): ApplicableValidator<String> {

  override fun validate(): Boolean {
    if (fieldValue == null)
      return true

    val stringLength = fieldValue.length

    return (min == null || stringLength >= min) && (max == null || stringLength <= max)
  }
}