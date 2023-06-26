package study.ddudooo.httpqueue.common.jpa

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditBaseEntity : BaseEntity() {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected var createdAt: Instant = Instant.now()

    @LastModifiedDate
    @Column(nullable = false)
    protected var updatedAt: Instant = Instant.now()
}
