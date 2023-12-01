package me.blvckbytes.propertyvalidation

import me.blvckbytes.propertyvalidation.validatior.Validator

class PropertyValidationException(
  val failedValidators: List<Validator<*>>
) : RuntimeException()