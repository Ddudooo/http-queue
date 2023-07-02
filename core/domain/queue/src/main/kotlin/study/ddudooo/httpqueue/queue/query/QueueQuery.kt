package study.ddudooo.httpqueue.queue.query

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.ddudooo.httpqueue.common.enum.task.TaskStatus.ACTIVE
import study.ddudooo.httpqueue.persistence.aggregate.queue.task.dao.TaskRepository
import study.ddudooo.httpqueue.queue.model.Queue
import study.ddudooo.httpqueue.queue.task.model.Task

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Service
@Transactional(readOnly = true)
class QueueQuery(
    private val taskRepository: TaskRepository,
) {
    private val log = LoggerFactory.getLogger(QueueQuery::class.java)

    fun getQueueStatus(): Queue {
        log.info("Queue get status")
        val activatedTaskSize = taskRepository.countByStatus(ACTIVE)
        log.info("get next task")
        val nextTask = taskRepository.findFirstByStatusOrderByIdAsc(ACTIVE)
            ?.let { Task.of(it) }

        return Queue(activatedTaskSize, nextTask)
    }
}
