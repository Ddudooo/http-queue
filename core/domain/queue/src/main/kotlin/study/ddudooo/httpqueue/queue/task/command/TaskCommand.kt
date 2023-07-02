package study.ddudooo.httpqueue.queue.task.command

import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.ddudooo.httpqueue.common.enum.task.TaskStatus.ACTIVE
import study.ddudooo.httpqueue.persistence.aggregate.queue.task.TaskEntity
import study.ddudooo.httpqueue.persistence.aggregate.queue.task.dao.TaskRepository
import study.ddudooo.httpqueue.queue.task.error.DuplicatedTaskError
import study.ddudooo.httpqueue.queue.task.error.NotSupportedCancelTaskError
import study.ddudooo.httpqueue.queue.task.model.InsertTaskCommand
import study.ddudooo.httpqueue.queue.task.model.Task
import java.util.UUID

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Service
@Transactional
class TaskCommand(
    private val taskRepository: TaskRepository,
) {

    private val log = LoggerFactory.getLogger(TaskCommand::class.java)

    fun insert(requestTask: InsertTaskCommand): Task {
        log.info("task insert: '{}'", requestTask)
        try {
            val insertedTask =
                taskRepository.saveAndFlush(TaskEntity(requestTask.name, requestTask.description ?: "", ACTIVE))
            return Task.of(insertedTask)
        } catch (e: DataIntegrityViolationException) {
            throw DuplicatedTaskError()
        }
    }

    fun pop(): Task? = taskRepository.findFirstByStatusOrderByIdAsc(ACTIVE)
        ?.also { pop() }
        ?.let { Task.of(it) }
        ?.also { log.info("task pop: '{}'", it) }

    fun remove(taskId: String) {
        log.info("task remove: '{}'", taskId)

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
