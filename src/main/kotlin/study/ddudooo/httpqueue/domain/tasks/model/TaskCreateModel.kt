package study.ddudooo.httpqueue.domain.tasks.model

import jakarta.validation.constraints.NotBlank

data class Request(
    @NotBlank
    val name: String,
    val description: String?,
)

data class Response(
    val id: String,
    val name: String,
)
