package com.elleined.rt_messaging_api.ws;

import com.elleined.rt_messaging_api.model.mention.Mention;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.reaction.Reaction;

public interface WSService {
    void broadcast(Message message);
    void broadcast(Message message, Reaction reaction);
    void broadcast(Message message, Mention mention);
}
