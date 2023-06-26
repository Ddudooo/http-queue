package study.ddudooo.httpqueue.domain.tasks.exception

import study.ddudooo.httpqueue.common.error.BusinessError

class NotSupportedCancelTaskError : BusinessError() {
    override val message: String
        get() = "취소할 수 없는 작업입니다."
}
