package study.ddudooo.httpqueue.common.error

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult

@JsonInclude(NON_EMPTY)
data class ErrorResponse(
    val message: String,
    val status: Int,
    val errors: List<FieldError> = emptyList(),
) {
    companion object {
        @JvmStatic
        fun of(bindingResult: BindingResult): ErrorResponse {
            return ErrorResponse(message = "Invalid Request", status = 400, errors = FieldError.of(bindingResult))
        }

        @JvmStatic
        fun of(message: String, status: Int): ErrorResponse {
            return ErrorResponse(message = message, status = status)
        }

        @JvmStatic
        fun of(message: String, status: HttpStatus): ErrorResponse {
            return ErrorResponse(message = message, status = status.value())
        }
    }
}

data class FieldError(
    val field: String,
    val value: String?,
    val reason: String?,
) {
    companion object {
        @JvmStatic
        fun of(bindingResult: BindingResult): List<FieldError> {
            val fieldErrors = bindingResult.fieldErrors
            return fieldErrors
                .stream()
                .map { fieldError ->
                    FieldError(
                        field = fieldError.field,
                        value = fieldError.rejectedValue?.toString(),
                        reason = fieldError.defaultMessage,
                    )
                }
                .toList()
        }
    }
}
