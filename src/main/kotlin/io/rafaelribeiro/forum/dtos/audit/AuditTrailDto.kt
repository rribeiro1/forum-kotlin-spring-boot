package io.rafaelribeiro.forum.dtos.audit

import io.rafaelribeiro.forum.model.AuditTrail
import java.util.UUID

class AuditTrailDto(
    val id: UUID,
    val itemId: Long,
    val message: String
) {
    companion object Factory {
        fun of(auditTrail: AuditTrail): AuditTrailDto {
            return AuditTrailDto(
                auditTrail.id!!,
                auditTrail.itemId,
                auditTrail.message
            )
        }
    }
}
