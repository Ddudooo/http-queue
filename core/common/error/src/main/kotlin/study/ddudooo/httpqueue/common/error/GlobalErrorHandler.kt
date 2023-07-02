package study.ddudooo.httpqueue.common.error

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalErrorHandler {

    private val log = LoggerFactory.getLogger(GlobalErrorHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handledMethodArgumentNotValidException(
        e: MethodArgumentNotValidException,
    ): ResponseEntity<ErrorResponse> {
        log.error("MethodArgumentNotValidException: ${e.message}", e)
        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(e.bindingResult))
    }

    @ExceptionHandler(BindException::class)
    protected fun handledBindException(e: BindException): ResponseEntity<ErrorResponse> {
        log.error("BindException: ${e.message}", e)
        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(e.bindingResult))
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handledMethodArgumentTypeMismatchException(
        e: MethodArgumentTypeMismatchException,
    ): ResponseEntity<ErrorResponse> {
        log.error("MethodArgumentTypeMismatchException: ${e.message}", e)
        return ResponseEntity.badRequest()
            .body(ErrorResponse.of("Type Mismatch", BAD_REQUEST))
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun handledHttpRequestMethodNotSupportedException(
        e: HttpRequestMethodNotSupportedException,
    ): ResponseEntity<ErrorResponse> {
        log.error("HttpRequestMethodNotSupportedException: ${e.message}", e)
        return ResponseEntity.status(METHOD_NOT_ALLOWED)
            .body(ErrorResponse.of("Method Not Allowed", METHOD_NOT_ALLOWED))
    }

    @ExceptionHandler(AccessDeniedException::class)
    protected fun handledAccessDeniedException(
        e: AccessDeniedException,
    ): ResponseEntity<ErrorResponse> {
        log.error("AccessDeniedException: ${e.message}", e)
        return ResponseEntity.status(FORBIDDEN)
            .body(ErrorResponse.of("Access denied", FORBIDDEN))
    }

    @ExceptionHandler(NoSuchElementException::class)
    protected fun handledNoSuchElementException(
        e: NoSuchElementException,
    ): ResponseEntity<ErrorResponse> {
        log.error("NoSuchElementException: ${e.message}", e)
        return ResponseEntity.status(NOT_FOUND)
            .body(ErrorResponse.of("Not Found", NOT_FOUND))
    }

    @ExceptionHandler(BusinessError::class)
    protected fun handledBusinessError(e: BusinessError): ResponseEntity<ErrorResponse> {
        log.error("BusinessError: ${e.message}", e)
        return ResponseEntity.status(BAD_REQUEST)
            .body(
                ErrorResponse.of(
                    e.message
                        ?: e.javaClass.name,
                    BAD_REQUEST,
                ),
            )
    }

    @ExceptionHandler(Exception::class)
    protected fun handledException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("Exception: ${e.message}", e)
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of("Internal Server Error", INTERNAL_SERVER_ERROR))
    }
}
