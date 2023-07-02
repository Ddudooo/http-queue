package study.ddudooo.httpqueue.persistence.aggregate.queue.task.dao

import org.springframework.data.jpa.repository.JpaRepository
import study.ddudooo.httpqueue.common.enum.task.TaskStatus
import study.ddudooo.httpqueue.persistence.aggregate.queue.task.TaskEntity
import java.util.UUID

interface TaskRepository : JpaRepository<TaskEntity, UUID> {

    fun countByStatus(status: TaskStatus): Int

    fun findFirstByStatusOrderByIdAsc(status: TaskStatus): TaskEntity?

    fun countByIdIsBeforeOrderByIdAsc(id: UUID): Int
}
