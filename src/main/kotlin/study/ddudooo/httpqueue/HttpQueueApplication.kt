package study.ddudooo.httpqueue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class HttpQueueApplication

fun main(args: Array<String>) {
    runApplication<HttpQueueApplication>(*args)
}
