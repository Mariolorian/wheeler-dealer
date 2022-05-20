package com.mariolorian.wheelerdealer.api;

import com.mariolorian.wheelerdealer.exception.NbpExchangeServiceOfflineException;
import com.mariolorian.wheelerdealer.exception.NoSuchAccountFoundException;
import com.mariolorian.wheelerdealer.exception.NotEnoughFoundsException;
import com.mariolorian.wheelerdealer.exception.UserWithSuchPeselAlreadyExistedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(UserWithSuchPeselAlreadyExistedException.class)
    public ResponseEntity<ApiError> userWithSuchPeselAlreadyExisted(UserWithSuchPeselAlreadyExistedException ex, WebRequest webRequest) {
        ApiError apiError = getApiError(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), webRequest);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }

    @ExceptionHandler(NoSuchAccountFoundException.class)
    public ResponseEntity<ApiError> noSuchAccountFoundException(NoSuchAccountFoundException ex, WebRequest webrequest) {
        ApiError apiError = getApiError(HttpStatus.NOT_FOUND, ex.getMessage(), webrequest);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(NotEnoughFoundsException.class)
    public ResponseEntity<ApiError> notEnoughFoundsException(NotEnoughFoundsException ex, WebRequest webrequest) {
        ApiError apiError = getApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), webrequest);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(NbpExchangeServiceOfflineException.class)
    public ResponseEntity<ApiError> nNbpExchangeServiceOfflineException(NbpExchangeServiceOfflineException ex, WebRequest webrequest) {
        ApiError apiError = getApiError(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), webrequest);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(apiError);
    }

    private ApiError getApiError(HttpStatus httpStatus, String exceptionMessage, WebRequest webrequest) {
        return ApiError.builder()
                .statusCode(httpStatus.value())
                .timestamp(LocalDateTime.now(ZoneId.of("Europe/Warsaw")))
                .message(exceptionMessage)
                .description(webrequest.getDescription(false))
                .build();
    }

}
