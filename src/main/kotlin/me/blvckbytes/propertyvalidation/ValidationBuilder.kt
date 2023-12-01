package me.blvckbytes.propertyvalidation

import me.blvckbytes.propertyvalidation.validatior.ApplicableValidator
import me.blvckbytes.propertyvalidation.validatior.Validator

class ValidationBuilder {

  private val failedValidators = mutableListOf<Validator<*>>()

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