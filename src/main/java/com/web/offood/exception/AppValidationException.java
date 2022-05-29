package com.web.offood.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AppValidationException extends javax.validation.ValidationException {

    private String type;

    private List<MsgInfo> msgList = new ArrayList<>();

    public AppValidationException(String msgId, List<String> msgArgs) {
        this.msgList.add(MsgInfo.by(msgId, msgArgs));
    }


    public AppValidationException() {
        super();
    }


    public AppValidationException(String message) {
        super(message);
    }

    public AppValidationException(String message, String type) {
        super(message);
        this.type = type;
    }

    public AppValidationException add(String msgId, List<String> msgArgs) {
        this.msgList.add(MsgInfo.by(msgId, msgArgs));
        return this;
    }

    public AppValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppValidationException(Throwable cause) {
        super(cause);
    }
}
