package study.ddudooo.httpqueue.queue.task.model

import study.ddudooo.httpqueue.common.enum.task.TaskStatus
import study.ddudooo.httpqueue.persistence.aggregate.queue.task.TaskEntity
import java.time.Instant

data class Task(
    val id: String,
    val name: String,
    val description: String?,
    val status: TaskStatus?,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    companion object {
        fun of(entity: TaskEntity): Task {
            return Task(
                id = entity.id.toString(),
                name = entity.name,
                description = entity.description,
                status = entity.status,
                createdAt = entity.createdAt(),
                updatedAt = entity.updatedAt(),
            )
        }
    }
}

data class InsertTaskCommand(
    val name: String,
    val description: String? = "",
)

data class TaskStatusModel(
    val task: Task,
    val wait: Int?,
) {
    constructor(task: Task) : this(task, null)
}
