package com.elleined.rt_messaging_api.model.chat;

import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.message.PinMessage;
import com.elleined.rt_messaging_api.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Table(
        name = "tbl_chat",
        indexes = @Index(name = "created_at_idx", columnList = "created_at")
)
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class Chat extends PrimaryKeyIdentity {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User creator;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    @OneToMany(mappedBy = "chat")
    private List<PinMessage> pinMessages;

    @ElementCollection
    @CollectionTable(
            name = "tbl_nickname",
            joinColumns = @JoinColumn(
                    name = "chat_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    @Column(
            name = "nickname",
            nullable = false,
            unique = true
    )
    private Map<User, String> nicknames;

    public List<Integer> messageIds() {
        return this.getMessages().stream()
                .map(PrimaryKeyIdentity::getId)
                .toList();
    }

    public List<Integer> pinMessageIds() {
        return this.getPinMessages().stream()
                .map(PrimaryKeyIdentity::getId)
                .toList();
    }

    public boolean notOwned(Message message) {
        return !this.getMessages().contains(message);
    }

    public boolean notOwned(PinMessage pinMessage) {
        return !this.getPinMessages().contains(pinMessage);
    }

    public boolean isPinnedAlready(Message message) {
        return this.getPinMessages().stream()
                .map(PinMessage::getMessage)
                .anyMatch(message::equals);
    }

    public void setNickname(User user, String nickname) {
        this.getNicknames().put(user, nickname);
    }

    public String getNickname(User user) {
        return this.getNicknames().get(user);
    }

    public Map<Integer, String> getNicknameDTOs() {
        Map<Integer, String> userIdNicknames = new HashMap<>();
        for (Map.Entry<User, String> entry : nicknames.entrySet()) {
            userIdNicknames.put(entry.getKey().getId(), entry.getValue());
        }
        return userIdNicknames;
    }
}
