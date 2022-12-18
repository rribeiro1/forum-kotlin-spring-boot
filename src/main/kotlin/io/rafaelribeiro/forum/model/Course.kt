package io.rafaelribeiro.forum.model

import org.hibernate.boot.MetadataBuilder
import org.hibernate.boot.spi.MetadataBuilderContributor
import org.hibernate.dialect.function.SQLFunctionTemplate
import org.hibernate.type.BooleanType
import java.time.LocalDateTime
import javax.persistence.Entity

@Entity
class Course(
    val name: String,
    val category: String
) : Audit(), MetadataBuilderContributor {
    val creationDate: LocalDateTime = LocalDateTime.now()
    override fun contribute(metadataBuilder: MetadataBuilder) {
        metadataBuilder.applySqlFunction(
            "search",
            SQLFunctionTemplate(
                BooleanType.INSTANCE,
                "name_tokens @@ plainto_tsquery(?1)"
            )
        )
    }
}
