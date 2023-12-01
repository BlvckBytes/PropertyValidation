package me.blvckbytes.propertyvalidation.validatior

import kotlin.reflect.KProperty

class NotNull(
  override val field: KProperty<Any?>,
  override val fieldValue: Any?
): ApplicableValidator<Any> {

  override fun validate(): Boolean {
    return fieldValue != null
  }
}