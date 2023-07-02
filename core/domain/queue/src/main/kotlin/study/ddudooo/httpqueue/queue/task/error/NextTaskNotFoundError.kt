package study.ddudooo.httpqueue.queue.task.error

import study.ddudooo.httpqueue.common.error.BusinessError

class NextTaskNotFoundError : BusinessError() {
    override val message: String
        get() = "다음 작업이 없습니다."
}
