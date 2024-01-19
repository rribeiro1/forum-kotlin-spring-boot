package io.rafaelribeiro.forum.dtos.course

import io.rafaelribeiro.forum.model.Course
import io.rafaelribeiro.forum.model.Course_
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDateTime
import java.util.*

data class CourseFilterDto(
    val name: String?,
    val category: String?,
    val from: LocalDateTime?,
    val to: LocalDateTime?
) {
    companion object {
        fun idIn(courseIds: List<UUID>): Specification<Course> {
            return Specification { root, _, _ ->
                return@Specification root.get(Course_.id).`in`(setOf(courseIds))
            }
        }
    }
    fun toSpecification(): Specification<Course> {
        return Specification { root, _, builder ->

            var predicate = builder.conjunction()

            if (name != null) {
                predicate = builder.isTrue(builder.function("search", Boolean::class.java, builder.literal(name)))
            }

            if (category != null) {
                predicate = builder.and(predicate, builder.equal(root.get(Course_.category), category))
            }

            val (hasFrom, hasTo) = arrayOf(from != null, to != null)

            when {
                hasFrom && hasTo -> {
                    predicate = builder.and(predicate, builder.between(root.get(Course_.creationDate), from, to))
                }
                hasFrom -> {
                    predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get(Course_.creationDate), from))
                }
                hasTo -> {
                    predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get(Course_.creationDate), to))
                }
            }

            return@Specification predicate
        }
    }
}
