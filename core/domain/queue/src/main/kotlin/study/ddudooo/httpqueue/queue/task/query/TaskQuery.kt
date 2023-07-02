package study.ddudooo.httpqueue.queue.task.query

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.ddudooo.httpqueue.common.enum.task.TaskStatus.ACTIVE
import study.ddudooo.httpqueue.persistence.aggregate.queue.task.dao.TaskRepository
import study.ddudooo.httpqueue.queue.task.model.Task
import study.ddudooo.httpqueue.queue.task.model.TaskStatusModel
import java.util.UUID

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Service
@Transactional(readOnly = true)
class TaskQuery(
    private val taskRepository: TaskRepository,
) {

    private val log = LoggerFactory.getLogger(TaskQuery::class.java)

    fun getTaskStatus(taskId: String): TaskStatusModel {
        log.info("task get status: '{}'", taskId)

        val task = taskRepository.findById(UUID.fromString(taskId))
            .map { Task.of(it) }
            .orElseThrow { throw NoSuchElementException("task not found: $taskId") }

        if (task.status == ACTIVE) {
            val wait = taskRepository.countByIdIsBeforeOrderByIdAsc(UUID.fromString(taskId))
            return TaskStatusModel(task, wait)
        }
        return TaskStatusModel(task)
    }
}
