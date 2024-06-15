package com.trade.aquariux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TickerNotFoundException extends RuntimeException{
    public TickerNotFoundException(String message){
        super(message);
    }
}
