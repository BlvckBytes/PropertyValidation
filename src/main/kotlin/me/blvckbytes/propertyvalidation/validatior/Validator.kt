package me.blvckbytes.propertyvalidation.validatior

import kotlin.reflect.KProperty

interface Validator<T> {
  val field: KProperty<T?>
  val fieldValue: T?
}