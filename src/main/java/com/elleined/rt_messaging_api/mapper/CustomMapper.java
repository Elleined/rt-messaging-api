package com.elleined.rt_messaging_api.mapper;

import com.elleined.rt_messaging_api.dto.DTO;
import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;

public interface CustomMapper<ENTITY extends PrimaryKeyIdentity,
        DTO extends com.elleined.rt_messaging_api.dto.DTO> {
    DTO toDTO(ENTITY entity);
}