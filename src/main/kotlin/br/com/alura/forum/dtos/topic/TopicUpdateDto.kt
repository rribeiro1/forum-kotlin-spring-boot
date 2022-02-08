package br.com.alura.forum.dtos.topic

import br.com.alura.forum.support.NullOrNotBlank
import org.hibernate.validator.constraints.Length

data class TopicUpdateDto(
    @field:NullOrNotBlank
    @field:Length(min = 5)
    val title: String?,

    @field:NullOrNotBlank
    @field:Length(min = 10)
    val message: String?
)
