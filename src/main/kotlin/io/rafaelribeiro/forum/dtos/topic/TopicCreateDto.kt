package io.rafaelribeiro.forum.dtos.topic

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class TopicCreateDto(
    @field:NotNull
    @field:NotEmpty
    @field:Length(min = 5)
    val title: String,

    @field:NotNull
    @field:NotEmpty
    @field:Length(min = 5)
    val message: String,

    @field:NotNull
    @field:NotEmpty
    val courseName: String
)