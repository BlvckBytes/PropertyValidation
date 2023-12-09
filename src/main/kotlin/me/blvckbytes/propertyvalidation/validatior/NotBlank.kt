package me.blvckbytes.propertyvalidation.validatior

import kotlin.reflect.KProperty

class NotBlank(
  override val field: KProperty<String?>,
  override val fieldValue: String?
): ApplicableValidator<String> {

  override fun validate(): Boolean {
    if (fieldValue == null)
      return true

    return fieldValue.isNotBlank()
  }
}