package com.elleined.rt_messaging_api.model.reaction;

import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "rtmaReaction", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(
        name = "tbl_reaction",
        indexes = @Index(name = "created_at_idx", columnList = "created_at")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Reaction extends PrimaryKeyIdentity {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User creator;

    @Column(
            name = "emoji",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private Emoji emoji;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "message_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Message message;

    public enum Emoji {
        LIKE,
        HEART,
        CARE,
        HAHA,
        WOW,
        SAD,
        ANGRY
    }
}
