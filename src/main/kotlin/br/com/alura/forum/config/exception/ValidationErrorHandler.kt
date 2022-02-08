package br.com.alura.forum.config.exception

import br.com.alura.forum.support.AuthenticationErrorException
import br.com.alura.forum.support.ResourceNotFoundException
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ValidationErrorHandler(
    private val messageSource: MessageSource
) {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(exception: MethodArgumentNotValidException): ArrayList<ErrorDto> {
        val errors = arrayListOf<ErrorDto>()
        exception.bindingResult.fieldErrors.forEach { error ->
            errors.add(ErrorDto(error.field, messageSource.getMessage(error, LocaleContextHolder.getLocale())))
        }
        return errors
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(exception: ResourceNotFoundException): ErrorDto {
        return ErrorDto("", exception.localizedMessage)
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthenticationErrorException::class)
    fun handleAuthenticationErrorException(exception: AuthenticationErrorException): ErrorDto {
        return ErrorDto("username", exception.localizedMessage)
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ErrorDto {
        return ErrorDto("Internal server error", "Contact support of the service")
    }
}
