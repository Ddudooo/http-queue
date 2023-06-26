package study.ddudooo.httpqueue.domain.tasks.exception

import study.ddudooo.httpqueue.common.error.BusinessError

class DuplicatedTaskError : BusinessError() {
    override val message: String
        get() = "중복된 작업이 존재합니다."
}
