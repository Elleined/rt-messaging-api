package com.elleined.rt_messaging_api.dto.chat;

import com.elleined.rt_messaging_api.dto.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ChatDTO extends DTO {
    private int creatorId;
    private List<Integer> messageIds;
    private List<Integer> pinMessageIds;
    private Map<Integer, String> nicknames;
}
