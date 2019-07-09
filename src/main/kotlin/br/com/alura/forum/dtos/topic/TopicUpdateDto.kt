package br.com.alura.forum.dtos.topic

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class TopicUpdateDto (
        @field:NotNull
        @field:NotEmpty
        @field:Length(min = 5)
        val title: String,

        @field:NotNull
        @field:NotEmpty
        @field:Length(min = 10)
        val message: String
)