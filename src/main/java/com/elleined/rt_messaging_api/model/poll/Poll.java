package com.elleined.rt_messaging_api.model.poll;

import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;
import com.elleined.rt_messaging_api.model.chat.GroupChat;
import com.elleined.rt_messaging_api.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(
        name = "tbl_poll",
        indexes = @Index(name = "created_at_idx", columnList = "created_at")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Poll extends PrimaryKeyIdentity {

    @Column(
            name = "question",
            nullable = false
    )
    private String question;

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
            name = "group_chat_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private GroupChat groupChat;

    @OneToMany(mappedBy = "poll")
    private List<Option> options;

    public List<Integer> optionIds() {
        return this.getOptions().stream()
                .map(PrimaryKeyIdentity::getId)
                .toList();
    }

    public boolean notOwned(Option option) {
        return !this.getOptions().contains(option);
    }
}
