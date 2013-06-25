package com.wcj;

public class NioException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -8777730564051607696L;

    public NioException(String message) {
        super(message);
    }
    
    public NioException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    public NioException(Throwable throwable) {
        super(throwable);
    }
}
