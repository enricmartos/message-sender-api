package org.emartos.messagesender.web.handler;

import org.emartos.messagesender.model.exceptions.InvalidMessageException;
import org.emartos.messagesender.model.exceptions.ProviderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ProviderNotFoundException.class)
    private ResponseEntity<String> providerNotFoundExceptionHandler(final ProviderNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidMessageException.class)
    private ResponseEntity<String> invalidMessageExceptionHandler(final InvalidMessageException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
