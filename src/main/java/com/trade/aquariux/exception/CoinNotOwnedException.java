package com.trade.aquariux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CoinNotOwnedException extends RuntimeException{

    public CoinNotOwnedException(String message){
        super(message);
    }
}
