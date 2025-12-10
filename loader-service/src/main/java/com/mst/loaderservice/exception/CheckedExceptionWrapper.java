package com.mst.loaderservice.exception;

public class CheckedExceptionWrapper extends RuntimeException{
    public CheckedExceptionWrapper(String message, Throwable cause){
        super(message,cause);
    }
}
