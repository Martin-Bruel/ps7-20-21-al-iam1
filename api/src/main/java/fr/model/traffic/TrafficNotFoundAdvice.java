package fr.model.traffic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class TrafficNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(TrafficNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String trafficNotFoundHandler(TrafficNotFoundException ex) {
        return ex.getMessage();
    }
}