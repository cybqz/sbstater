package com.cyb.authority.exception;

/**
 * 未实现异常
 */
public class UndefinedException extends Throwable {

    @Override
    public void printStackTrace() {
        System.err.println("implementation class is not defined");
    }
}
