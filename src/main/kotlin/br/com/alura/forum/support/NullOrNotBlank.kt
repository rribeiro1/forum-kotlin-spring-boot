package br.com.alura.forum.support

import org.hibernate.validator.constraints.CompositionType
import org.hibernate.validator.constraints.ConstraintComposition
import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.ReportAsSingleViolation
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Null
import kotlin.reflect.KClass

@ConstraintComposition(CompositionType.OR)
@Null
@NotBlank
@ReportAsSingleViolation
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [])
annotation class NullOrNotBlank(
    val message: String = "must be either null or not blank",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = []
)
