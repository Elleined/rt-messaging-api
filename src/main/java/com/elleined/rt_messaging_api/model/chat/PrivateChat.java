package com.elleined.rt_messaging_api.model.chat;

import com.elleined.rt_messaging_api.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "tbl_private_chat",
        indexes = @Index(name = "created_at_idx", columnList = "created_at")
)
@PrimaryKeyJoinColumn(
        name = "id",
        referencedColumnName = "id"
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PrivateChat extends Chat {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "receiver_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User receiver;
}
