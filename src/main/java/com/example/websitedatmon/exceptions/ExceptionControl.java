package com.example.websitedatmon.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControl {
    @ExceptionHandler(NullPointerException.class)
    public String nullPoniter(Exception ex){
        ex.printStackTrace();
        return "errors/null-exception";
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgument(Exception ex){
        ex.printStackTrace();
        return "errors/illegalArgument-exception";
    }
    @ExceptionHandler(Exception.class)
    public  String exception(Exception ex){
        ex.printStackTrace();
        return "errors/exception";
    }
}
