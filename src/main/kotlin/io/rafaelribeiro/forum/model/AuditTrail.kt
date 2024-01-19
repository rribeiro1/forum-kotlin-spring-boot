package io.rafaelribeiro.forum.model

import jakarta.persistence.Entity

@Entity
class AuditTrail(
    val itemId: Long,
    val message: String
) : Audit()
