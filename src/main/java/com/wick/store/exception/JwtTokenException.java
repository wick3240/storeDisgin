package com.wick.store.exception;

public class JwtTokenException extends Exception{
    public JwtTokenException(String message,Throwable cause){
        super(message,cause);
    }
}
