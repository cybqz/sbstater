package com.cyb.common.exception;

/**
 * 比较异常
 * @author CYB
 */
public class CompareException extends RuntimeException{

    private static final long serialVersionUID = -1227502189811299311L;

    public CompareException() {}

    public CompareException(String message) {
        super(message);
    }
}
