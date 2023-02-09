package com.wick.store.service.ex;

//用户名被占用的情况的异常
public class UserNameDuplicatedException extends ServiceException{
    public UserNameDuplicatedException() {
        super();
    }

    public UserNameDuplicatedException(String message) {
        super(message);
    }

    public UserNameDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameDuplicatedException(Throwable cause) {
        super(cause);
    }

    protected UserNameDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
