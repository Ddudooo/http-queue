package study.ddudooo.httpqueue.domain.tasks.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import java.time.Instant

data class Task(
    val id: String,
    val name: String,
    val description: String?,
    val status: TaskStatus?,
    val createdAt: Instant,
    val updatedAt: Instant,
)

@JsonInclude(NON_NULL)
data class TaskStatusModel(
    val task: Task,
    val wait: Int?,
) {
    constructor(task: Task) : this(task, null)
}

data class Queue(
    val size: Int,
    val next: Task?,
)
