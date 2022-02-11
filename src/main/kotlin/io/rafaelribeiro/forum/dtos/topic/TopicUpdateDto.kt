package io.rafaelribeiro.forum.dtos.topic

import io.rafaelribeiro.forum.support.NullOrNotBlank
import org.hibernate.validator.constraints.Length

data class TopicUpdateDto(
    @field:NullOrNotBlank
    @field:Length(min = 5)
    val title: String? = null,

    @field:NullOrNotBlank
    @field:Length(min = 10)
    val message: String? = null
)
