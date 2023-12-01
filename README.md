<!-- This file is rendered by https://github.com/BlvckBytes/readme_helper -->

# PropertyValidation

A small and simplistic yet effective validation library.

## Table of Contents
- [How To Use](#how-to-use)
  - [Domain Models](#domain-models)
  - [Exception Mapping](#exception-mapping)

## How To Use

### Domain Models

When building my personal Spring projects, I like to strictly split domain models by their direction. The class' suffix indicates said direction, where `R` stands for read (`server->user`), `C` for create (`user->server`) and `U` for update (`user->server`). Most of the time, creating and updating a model offers the same fields, which is why both suffixes apply: `CU`.

Let's use a simple example to illustrate this separation:

```kotlin
open class IconCU protected constructor(
  val name: String,
  val description: String?,
) {
  companion object {
    fun fromParameters(name: String?, description: String?): IconCU {
      return IconCU(name!!, description)
    }
  }
}
```

The `IconCU` class can only be instantiated from within the domain and thus forces it's users to make use of `IconCU::fromParameters`. The reason for why a factory method is required, instead of a secondary constructor, is that the method signature in Java cannot differentiate between nullable and non-nullable types, as this is only a feature extension provided by Kotlin, which is mostly enforced at compile-time (with additional `@NotNull` annotations (not visible in the signature) or runtime assertions). This method is - while not ideal - to be preferred over wrapping all types in an `Optional<*>`, for example. Various programmatic validations can now take place in this factory method, without limiting oneself to expressing these requirements in the severely limited language of annotations.

```kotlin
class IconR(
  val id: UUID,
  name: String,
  description: String?,
  val systemPath: String,
  override val createdAt: LocalDateTime,
  override val updatedAt: LocalDateTime?
) : IconCU(name, description), BaseModel
```

The read model now inherits the `CU`-model, as those fields are a part of the full domain model.

Let's now add the validators to the factory method of `IconCU`:

```kotlin
fun fromParameters(name: String?, description: String?): IconCU {
  ValidationBuilder()
    .addValidator(NotNullAndNotBlank(IconCU::name, name))
    .addValidator(NullOrNotBlank(IconCU::description, description))
    .throwIfApplicable()
  
  return IconCU(name!!, description)
}
```

Every validator will take the field's reference as well as it's value as the first two parameters, followed by specific validator arguments, if necessary. The [ValidationBuilder](src/main/kotlin/me/blvckbytes/propertyvalidation/ValidationBuilder.kt) is a helpful utility to collect failing validators and later emit a collection of them as a [PropertyValidationException](src/main/kotlin/me/blvckbytes/propertyvalidation/PropertyValidationException.kt). This way, all validators are executed (in contrast to them throwing an exception themselves, which exits the factory method) and the user gets the most detailed and informative response possible.

It is inherently easier to denote more complex validations in this manner, constituted of cross-field comparisons, as the following example illustrates:

```kotlin
fun fromParameters(minimum: Double?, maximum: Double?, stepSize: Double?): DoubleRangeConstraint {
  ValidationBuilder()
    .addValidator(CompareToOther(
      DoubleRangeConstraint::minimum, minimum,
      DoubleRangeConstraint::maximum, maximum,
      Comparison.LESS_THAN
    ))
    .addValidator(CompareToConstant(
      DoubleRangeConstraint::stepSize, stepSize,
      0.0, Comparison.GREATER_THAN
    ))
    .throwIfApplicable()

  return DoubleRangeConstraint(minimum, maximum, stepSize)
}
```

### Exception Mapping

The following snippet is a bare-bones example of how failed validators can be mapped to an error response.

```kotlin
@ExceptionHandler(PropertyValidationException::class)
fun handlePropertyValidationException(exception: PropertyValidationException): ResponseEntity<Any> {
  for (failedValidator in exception.failedValidators) {
    val fieldName = failedValidator.field.name
    val rejectedValue = failedValidator.fieldValue

    val validationMessage: String = when (failedValidator) {
      is NotNull -> "..."
      is NullOrNotBlank -> "..."
      is NotNullAndNotBlank -> "..."
      is CompareToConstant -> "..."
      is CompareToOther -> "..."
      is CompareToMinMax -> "..."
      else -> "Unimplemented validator encountered"
    }

    // Build and collect your validation error
  }
  
  // Build and return your error response
}
```