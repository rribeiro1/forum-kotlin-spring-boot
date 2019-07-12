package br.com.alura.forum.config.exception

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ValidationErrorHandle(
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
}