package study.ddudooo.httpqueue.web.application.queue.task.dto

import jakarta.validation.constraints.NotBlank

data class InsertTaskRequest(
    @NotBlank
    val name: String,
    val description: String?,
)

data class InsertTaskResponse(
    val id: String,
    val name: String,
)
