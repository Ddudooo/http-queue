package study.ddudooo.httpqueue.queue.model

import study.ddudooo.httpqueue.queue.task.model.Task

data class Queue(
    val size: Int,
    val next: Task?,
)
