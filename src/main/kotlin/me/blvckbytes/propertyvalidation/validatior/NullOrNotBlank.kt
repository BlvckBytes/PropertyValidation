package me.blvckbytes.propertyvalidation.validatior

import kotlin.reflect.KProperty

class NullOrNotBlank(
  override val field: KProperty<String?>,
  override val fieldValue: String?
): ApplicableValidator<String> {

  override fun validate(): Boolean {
    return fieldValue == null || fieldValue.isNotBlank()
  }
}