package com.elleined.rt_messaging_api.dto.poll;

import com.elleined.rt_messaging_api.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PollDTO extends DTO {
    private int creatorId;
    private String question;
    private int groupChatId;
    private List<Integer> optionIds;
}
