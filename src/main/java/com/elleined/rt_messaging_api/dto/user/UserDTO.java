package com.elleined.rt_messaging_api.dto.user;

import com.elleined.rt_messaging_api.dto.chat.GroupChatDTO;
import com.elleined.rt_messaging_api.dto.chat.PrivateChatDTO;
import com.elleined.rt_messaging_api.dto.mention.MentionDTO;
import com.elleined.rt_messaging_api.dto.message.MessageDTO;
import com.elleined.rt_messaging_api.dto.reaction.ReactionDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserDTO {
    private String name;
    private List<PrivateChatDTO> privateChats;
    private Set<GroupChatDTO> groupChats;
    private List<MessageDTO> messages;
    private List<ReactionDTO> reactions;
    private List<MentionDTO> receivedMentions;
}
