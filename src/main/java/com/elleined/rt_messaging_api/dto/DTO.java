package com.elleined.rt_messaging_api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class DTO extends HateoasDTO {
    private int id;
    private LocalDateTime createdAt;

    public DTO(int id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }
}
