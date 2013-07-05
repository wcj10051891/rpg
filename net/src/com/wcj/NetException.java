package com.wcj;

/**
 * nio exception
 * @author wcj
 */
public class NetException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -8777730564051607696L;

    public NetException(String message) {
        super(message);
    }
    
    public NetException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    public NetException(Throwable throwable) {
        super(throwable);
    }
}
