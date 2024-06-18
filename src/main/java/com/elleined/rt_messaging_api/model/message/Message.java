package com.elleined.rt_messaging_api.model.message;

import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;
import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.mention.Mention;
import com.elleined.rt_messaging_api.model.reaction.Reaction;
import com.elleined.rt_messaging_api.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(
        name = "tbl_message",
        indexes = @Index(name = "created_at_idx", columnList = "created_at")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Message extends PrimaryKeyIdentity {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User creator;

    @Column(
            name = "content",
            nullable = false
    )
    private String content;

    @Column(
            name = "content_type",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "chat_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Chat chat;

    @JoinColumn(
            name = "status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "message")
    private List<Reaction> reactions;

    @OneToMany(mappedBy = "message")
    private List<Mention> mentions;

    public enum ContentType {
        TEXT,
        ATTACHMENT,
        LOCATION,
        VOICE_MESSAGE
    }

    public List<Integer> reactionIds() {
        return this.getReactions().stream()
                .map(PrimaryKeyIdentity::getId)
                .toList();
    }

    public List<Integer> mentionIds() {
        return this.getMentions().stream()
                .map(PrimaryKeyIdentity::getId)
                .toList();
    }

    public boolean notOwned(Reaction reaction) {
        return !this.getReactions().contains(reaction);
    }

    public enum Status {
        ACTIVE,
        IN_ACTIVE
    }

    public boolean isActive() {
        return this.getStatus().equals(Status.ACTIVE);
    }

    public boolean isInactive() {
        return this.getStatus().equals(Status.IN_ACTIVE);
    }
}
