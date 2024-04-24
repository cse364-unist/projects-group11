package cse364.project;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class CannotChangeIDAdvice {

    @ResponseBody
    @ExceptionHandler(CannotChangeIDException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String cannotChangeIDHandler(CannotChangeIDException ex) {
        return ex.getMessage();
    }
}