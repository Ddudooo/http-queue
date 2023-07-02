package study.ddudooo.httpqueue.web.application.queue.task.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import study.ddudooo.httpqueue.queue.task.model.Task

@JsonInclude(NON_NULL)
data class GetTaskStatusResponse(
    val task: Task,
    val wait: Int?,
)
