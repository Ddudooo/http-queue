package study.ddudooo.httpqueue.domain.tasks.api

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import study.ddudooo.httpqueue.domain.tasks.exception.NextTaskNotFoundError
import study.ddudooo.httpqueue.domain.tasks.model.Queue
import study.ddudooo.httpqueue.domain.tasks.model.Request
import study.ddudooo.httpqueue.domain.tasks.model.Response
import study.ddudooo.httpqueue.domain.tasks.model.TaskStatusModel
import study.ddudooo.httpqueue.domain.tasks.service.QueueService
import study.ddudooo.httpqueue.domain.tasks.service.TaskService

@RestController
@RequestMapping("/queue")
class TaskApi(
    private val taskService: TaskService,
    private val queueService: QueueService,
) {
    private final val log = LoggerFactory.getLogger(TaskApi::class.java)

    @GetMapping("/status")
    fun getTasks(): Queue {
        log.info("get queue status")
        return queueService.getStatus()
    }

    @PostMapping("/tasks")
    fun postTasks(
        @RequestBody request: Request,
    ): Response {
        log.info("postTasks")
        val task = taskService.putTask(request.name, request.description)

        return Response(task.id, task.name)
    }

    @GetMapping("/{taskId}/status")
    fun getTaskStatus(
        @PathVariable taskId: String,
    ): TaskStatusModel {
        log.info("getTaskStatus")
        return taskService.getTaskStatus(taskId)
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{taskId}")
    fun cancelTask(
        @PathVariable taskId: String,
    ) {
        log.info("cancelTask")
        taskService.cancelTask(taskId)
    }

    @PostMapping("/pop")
    fun popTask(): Response {
        log.info("popTask")
        taskService.popTask()
            ?.let { task ->
                return Response(task.id, task.name)
            } ?: throw NextTaskNotFoundError()
    }
}
