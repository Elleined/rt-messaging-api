package com.elleined.rt_messaging_api.service;

import com.elleined.rt_messaging_api.exception.resource.ResourceNotFoundException;
import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;
import com.elleined.rt_messaging_api.model.message.Message;

import java.util.List;

public interface CustomService<T extends PrimaryKeyIdentity> {
    T getById(int id) throws ResourceNotFoundException;
    List<T> getAllById(List<Integer> ids);
}
