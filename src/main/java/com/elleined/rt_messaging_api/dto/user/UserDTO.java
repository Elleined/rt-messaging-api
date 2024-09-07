package com.elleined.rt_messaging_api.dto.user;

import com.elleined.rt_messaging_api.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserDTO extends DTO  {
    private String email;
    private String name;
    private String image;
}
