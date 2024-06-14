package com.elleined.rt_messaging_api.exception.mention;

import com.elleined.rt_messaging_api.exception.SystemException;

public class MentionException extends SystemException {
    public MentionException(String message) {
        super(message);
    }
}
