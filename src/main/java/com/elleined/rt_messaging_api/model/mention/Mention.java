package com.elleined.rt_messaging_api.model.mention;

import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;
import com.elleined.rt_messaging_api.model.message.Message;
import com.elleined.rt_messaging_api.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "tbl_mention",
        indexes = @Index(name = "created_at_idx", columnList = "created_at")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Mention extends PrimaryKeyIdentity {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User creator;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "mentioned_user_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User mentionedUser;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "message_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Message message;
}
