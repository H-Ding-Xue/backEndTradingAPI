package com.trade.aquariux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotEnoughBuyingPowerException extends RuntimeException{
    public NotEnoughBuyingPowerException(String message){
        super(message);
    }
}
