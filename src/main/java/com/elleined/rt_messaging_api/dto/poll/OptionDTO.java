package com.elleined.rt_messaging_api.dto.poll;

import com.elleined.rt_messaging_api.dto.DTO;
import com.elleined.rt_messaging_api.dto.user.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class OptionDTO extends DTO {
    private UserDTO creatorDTO;
    private String option;
    private PollDTO pollDTO;
}
