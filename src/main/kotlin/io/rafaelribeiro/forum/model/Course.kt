package io.rafaelribeiro.forum.model

import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
class Course(
    val name: String,
    val category: String
) : Audit() {
    val creationDate: LocalDateTime = LocalDateTime.now()
// TODO: Fix later.
//    override fun contribute(metadataBuilder: MetadataBuilder) {
//        metadataBuilder.applySqlFunction(
//            "search",
//            SQLFunctionTemplate(
//                BooleanType.INSTANCE,
//                "name_tokens @@ plainto_tsquery(?1)"
//            )
//        )
//    }
}
