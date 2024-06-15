package com.elleined.rt_messaging_api.model.user;

import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.chat.PrivateChat;
import com.elleined.rt_messaging_api.model.mention.Mention;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(
        name = "tbl_user",
        indexes = @Index(name = "created_at_idx", columnList = "created_at")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class User extends PrimaryKeyIdentity {

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "receiver")
    private List<PrivateChat> privateChats; // Receive private chat. Basically created chats are not bidirectional

    @ManyToMany(mappedBy = "receivers")
    private Set<GroupChat> groupChats; // Received group chats. Basically created chats are not bidirectional

    @OneToMany(mappedBy = "creator")
    private List<Message> messages;

    @OneToMany(mappedBy = "creator")
    private List<Reaction> reactions;

    @OneToMany(mappedBy = "mentionedUser")
    private List<Mention> receivedMentions;

    public List<Integer> privateChatIds() {
        return this.getPrivateChats().stream()
                .map(PrimaryKeyIdentity::getId)
                .toList();
    }

    public Set<Integer> groupChatIds() {
        return this.getGroupChats().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }

    public List<Integer> messageIds() {
        return this.getMessages().stream()
                .map(PrimaryKeyIdentity::getId)
                .toList();
    }

    public List<Integer> reactionIds() {
        return this.getReactions().stream()
                .map(PrimaryKeyIdentity::getId)
                .toList();
    }

    public List<Integer> receivedMentionIds() {
        return this.getReceivedMentions().stream()
                .map(PrimaryKeyIdentity::getId)
                .toList();
    }

    public boolean notOwned(Reaction reaction) {
        return !this.getReactions().contains(reaction);
    }
}
