package com.yhp.phoneproxy.proxy.mitm.exception;

public class FakeCertificateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FakeCertificateException(String message, Throwable cause) {
        super(message, cause);
    }
}
