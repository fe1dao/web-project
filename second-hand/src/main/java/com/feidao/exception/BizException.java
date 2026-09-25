package com.feidao.exception;

import lombok.Data;

@Data
public class BizException extends RuntimeException {
    public BizException(String message) {
        super(message);
    }
}
