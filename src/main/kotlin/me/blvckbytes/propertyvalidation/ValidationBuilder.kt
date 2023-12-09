package me.blvckbytes.propertyvalidation

import me.blvckbytes.propertyvalidation.validatior.ApplicableValidator
import me.blvckbytes.propertyvalidation.validatior.Validator

class ValidationBuilder {

  private val failedValidators = mutableListOf<Validator<*>>()

  fun addValidatorIf(validator: ApplicableValidator<*>, condition: () -> Boolean): ValidationBuilder {
    if (!condition())
      return this

    return addValidator(validator)
  }

  fun addValidatorIf(validator: ApplicableValidator<*>, condition: Boolean): ValidationBuilder {
    if (!condition)
      return this

    return addValidator(validator)
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