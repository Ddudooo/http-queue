package study.ddudooo.httpqueue.domain.tasks

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import study.ddudooo.httpqueue.common.jpa.AuditBaseEntity
import study.ddudooo.httpqueue.domain.tasks.model.Task
import study.ddudooo.httpqueue.domain.tasks.model.TaskStatus

@Entity
@Table(name = "tasks")
class TaskEntity(
    name: String,
    description: String = "",
    status: TaskStatus = TaskStatus.ACTIVE,
) : AuditBaseEntity() {
    @Column(nullable = false, unique = true)
    var name: String = name
        protected set

    @Column(nullable = false)
    val description = description

    @Enumerated(STRING)
    @Column(nullable = false)
    var status = status
        protected set

    fun toModel(): Task =
        Task(
            id = id.toString(),
            name = name,
            description = description,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    fun pop(): TaskEntity {
        name = "$name-$id"
        status = TaskStatus.POPPED
        return this
    }

    fun cancel() {
        name = "$name-$id"
        status = TaskStatus.CANCELED
    }
}
