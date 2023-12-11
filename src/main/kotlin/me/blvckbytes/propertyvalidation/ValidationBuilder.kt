package me.blvckbytes.propertyvalidation

import me.blvckbytes.propertyvalidation.validatior.ApplicableValidator
import me.blvckbytes.propertyvalidation.validatior.Validator

class ValidationBuilder {

  private val failedValidators = mutableListOf<Validator<*>>()

  inline fun callIf(condition: Boolean, executable: ValidationBuilder.() -> Unit): ValidationBuilder {
    if (!condition)
      return this

    executable(this)
    return this
  }

  fun addValidator(validator: ApplicableValidator<*>): ValidationBuilder {
    if (!validator.validate())
      failedValidators.add(validator)
    return this
  }

  fun throwIfApplicable() {
    if (failedValidators.isNotEmpty())
      throw PropertyValidationException(failedValidators)
  }
}