package com.hzl.hadoop.util.zxing.exception;

/**
 * <p>Title: Nepxion Zxing</p>
 * <p>Description: Nepxion Zxing QR Code</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public class ZxingException extends RuntimeException {
    private static final long serialVersionUID = 7867107448835589117L;

    public ZxingException() {
        super();
    }

    public ZxingException(String message) {
        super(message);
    }

    public ZxingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZxingException(Throwable cause) {
        super(cause);
    }
}