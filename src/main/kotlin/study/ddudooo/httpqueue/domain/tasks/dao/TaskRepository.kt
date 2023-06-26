package study.ddudooo.httpqueue.domain.tasks.dao

import org.springframework.data.jpa.repository.JpaRepository
import study.ddudooo.httpqueue.domain.tasks.TaskEntity
import study.ddudooo.httpqueue.domain.tasks.model.TaskStatus
import java.util.UUID

interface TaskRepository : JpaRepository<TaskEntity, UUID> {

    fun countByStatus(status: TaskStatus): Int

    fun findFirstByStatusOrderByIdAsc(status: TaskStatus): TaskEntity?

    fun countByIdIsBeforeOrderByIdAsc(id: UUID): Int
}
