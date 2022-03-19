package io.rafaelribeiro.forum.dtos.topic

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank

data class TopicCreateDto(
    @field:NotBlank
    @field:Length(min = 5)
    val title: String,

    @field:NotBlank
    @field:Length(min = 5)
    val message: String,

    @field:NotBlank
    val courseName: String
)
