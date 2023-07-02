package study.ddudooo.httpqueue.web.application.queue.task.api

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import study.ddudooo.httpqueue.queue.model.Queue
import study.ddudooo.httpqueue.queue.query.QueueQuery
import study.ddudooo.httpqueue.queue.task.command.TaskCommand
import study.ddudooo.httpqueue.queue.task.error.NextTaskNotFoundError
import study.ddudooo.httpqueue.queue.task.model.InsertTaskCommand
import study.ddudooo.httpqueue.queue.task.query.TaskQuery
import study.ddudooo.httpqueue.web.application.queue.task.dto.GetTaskStatusResponse
import study.ddudooo.httpqueue.web.application.queue.task.dto.InsertTaskRequest
import study.ddudooo.httpqueue.web.application.queue.task.dto.InsertTaskResponse
import study.ddudooo.httpqueue.web.application.queue.task.dto.PopTaskResponse

@RestController
@RequestMapping("/queue")
class TaskApi(
    private val queueQuery: QueueQuery,
    private val taskCommand: TaskCommand,
    private val taskQuery: TaskQuery,
) {

    private val log = LoggerFactory.getLogger(TaskApi::class.java)

    @GetMapping("/status")
    fun getTasks(): Queue {
        log.info("get queue status")
        return queueQuery.getQueueStatus()
    }

    @PostMapping("/tasks")
    fun insertTask(
        @RequestBody request: InsertTaskRequest,
    ): InsertTaskResponse {
        log.info("postTasks")
        val task = taskCommand.insert(InsertTaskCommand(request.name, request.description))

        return InsertTaskResponse(task.id, task.name)
    }

    @GetMapping("/{taskId}/status")
    fun getTaskStatus(
        @PathVariable taskId: String,
    ): GetTaskStatusResponse {
        log.info("getTaskStatus")
        val taskStatus = taskQuery.getTaskStatus(taskId)
        return GetTaskStatusResponse(taskStatus.task, taskStatus.wait)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{taskId}")
    fun cancelTask(
        @PathVariable taskId: String,
    ) {
        log.info("cancelTask")
        taskCommand.remove(taskId)
    }

    @PostMapping("/pop")
    fun popTask(): PopTaskResponse {
        log.info("popTask")
        taskCommand.pop()
            ?.let { task ->
                return PopTaskResponse(task.id, task.name)
            } ?: throw NextTaskNotFoundError()
    }
}
