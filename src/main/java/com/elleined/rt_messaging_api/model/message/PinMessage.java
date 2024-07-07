package com.elleined.rt_messaging_api.model.message;

import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;
import com.elleined.rt_messaging_api.model.chat.Chat;
import com.elleined.rt_messaging_api.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "rtmaPinMessage", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(
        name = "tbl_pin_message",
        indexes = @Index(name = "created_at_idx", columnList = "created_at")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PinMessage extends PrimaryKeyIdentity {

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
            name = "message_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Message message;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "chat_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Chat chat;
}
