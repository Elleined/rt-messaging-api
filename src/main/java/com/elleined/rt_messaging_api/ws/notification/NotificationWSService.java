package com.elleined.rt_messaging_api.ws.notification;

public interface NotificationWSService {
    void notifyOnMessage();
    void notifyOnReaction();
    void notifyOnMention();
}
