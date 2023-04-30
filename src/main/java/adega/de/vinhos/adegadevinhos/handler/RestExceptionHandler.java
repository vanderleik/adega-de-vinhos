package adega.de.vinhos.adegadevinhos.handler;

import adega.de.vinhos.adegadevinhos.exception.BadRequestException;
import adega.de.vinhos.adegadevinhos.exception.BadRequestExceptionDetails;
import adega.de.vinhos.adegadevinhos.util.TranslationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails
                        .builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title(TranslationConstants.BAD_REQUEST_EXCEPTION)
                        .details(ex.getMessage())
                        .developerMessage(ex.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);
    }
}
