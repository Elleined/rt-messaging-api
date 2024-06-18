package com.elleined.rt_messaging_api.exception.message;

import com.elleined.rt_messaging_api.exception.SystemException;

public class MessageException extends SystemException {
    public MessageException(String message) {
        super(message);
    }
}
