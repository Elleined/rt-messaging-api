package com.elleined.rt_messaging_api.dto.poll;

import com.elleined.rt_messaging_api.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class OptionDTO extends DTO {
    private int creatorId;
    private String option;
    private int pollId;
    private Set<Integer> votingUserIds;
}
