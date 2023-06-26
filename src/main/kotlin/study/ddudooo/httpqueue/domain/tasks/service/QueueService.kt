package study.ddudooo.httpqueue.domain.tasks.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.ddudooo.httpqueue.domain.tasks.dao.TaskRepository
import study.ddudooo.httpqueue.domain.tasks.model.Queue
import study.ddudooo.httpqueue.domain.tasks.model.TaskStatus.ACTIVE

@Service
@Transactional(readOnly = true)
class QueueService(
    private val taskRepository: TaskRepository,
) {
    private final val logger = LoggerFactory.getLogger(QueueService::class.java)

    fun getStatus(): Queue {
        logger.info("get queue status")
        val size = taskRepository.countByStatus(ACTIVE)
        logger.info("get next task")
        val nextTask = taskRepository.findFirstByStatusOrderByIdAsc(ACTIVE)

        return Queue(size, nextTask?.toModel())
    }
}
