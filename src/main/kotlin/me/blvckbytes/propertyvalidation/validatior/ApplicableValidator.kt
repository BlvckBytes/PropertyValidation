package me.blvckbytes.propertyvalidation.validatior

interface ApplicableValidator<T> : Validator<T> {
  fun validate(): Boolean
}