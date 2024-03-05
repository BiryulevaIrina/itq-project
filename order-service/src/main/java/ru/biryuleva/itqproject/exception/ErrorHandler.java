package ru.biryuleva.itqproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        return new ApiError(e.getMessage(), "Нужный объект не найден", "NOT_FOUND");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleRuntimeExceptions(final RuntimeException e) {
        return new ApiError(e.getMessage(), "Произошла непредвиденная ошибка.", "INTERNAL_SERVER_ERROR");
    }
}
