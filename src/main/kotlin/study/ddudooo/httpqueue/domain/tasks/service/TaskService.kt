package study.ddudooo.httpqueue.domain.tasks.service

import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.ddudooo.httpqueue.domain.tasks.TaskEntity
import study.ddudooo.httpqueue.domain.tasks.dao.TaskRepository
import study.ddudooo.httpqueue.domain.tasks.exception.DuplicatedTaskError
import study.ddudooo.httpqueue.domain.tasks.exception.NotSupportedCancelTaskError
import study.ddudooo.httpqueue.domain.tasks.model.Task
import study.ddudooo.httpqueue.domain.tasks.model.TaskStatus
import study.ddudooo.httpqueue.domain.tasks.model.TaskStatus.ACTIVE
import study.ddudooo.httpqueue.domain.tasks.model.TaskStatusModel
import java.util.UUID

@Service
@Transactional(readOnly = true)
class TaskService(
    private val taskRepository: TaskRepository,
) {
    private final val log = LoggerFactory.getLogger(TaskService::class.java)

    @Transactional
    fun putTask(name: String, description: String?, status: TaskStatus? = ACTIVE): Task {
        try {
            return taskRepository.saveAndFlush(TaskEntity(name, description ?: "", status ?: ACTIVE))
                .toModel()
        } catch (e: DataIntegrityViolationException) {
            throw DuplicatedTaskError()
        }
    }

    @Transactional
    fun popTask(): Task? = taskRepository.findFirstByStatusOrderByIdAsc(ACTIVE)
        ?.pop()
        ?.toModel()
        ?.also {
            log.info("task pop: $it")
        }

    fun getTaskStatus(taskId: String): TaskStatusModel {
        log.info("task status: $taskId")
        val task: Task = taskRepository.findById(UUID.fromString(taskId))
            .map(TaskEntity::toModel)
            .orElseThrow { throw NoSuchElementException("task not found: $taskId") }

        if (task.status == ACTIVE) {
            val wait = taskRepository.countByIdIsBeforeOrderByIdAsc(UUID.fromString(taskId))
            return TaskStatusModel(task, wait)
        }
        return TaskStatusModel(task)
    }

    @Transactional
    fun cancelTask(taskId: String) {
        log.info("task cancel: $taskId")
        val task = taskRepository.findById(UUID.fromString(taskId))
            .orElseThrow { throw NoSuchElementException("task not found: $taskId") }

        when (task.status) {
            ACTIVE -> {
                task.cancel()
            }
            else -> {
                throw NotSupportedCancelTaskError()
            }
        }
    }
}
