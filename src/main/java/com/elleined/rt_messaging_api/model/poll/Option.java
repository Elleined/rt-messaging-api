package com.elleined.rt_messaging_api.model.poll;

import com.elleined.rt_messaging_api.model.PrimaryKeyIdentity;
import com.elleined.rt_messaging_api.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Entity
@Table(
        name = "tbl_option",
        indexes = @Index(name = "created_at_idx", columnList = "created_at")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Option extends PrimaryKeyIdentity {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User creator;

    @Column(
            name = "option",
            nullable = false
    )
    private String option;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "poll_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Poll poll;

    @ManyToMany(mappedBy = "votedOptions")
    private Set<User> votingUsers;

    public List<Integer> votingUserIds() {
        return this.getVotingUsers().stream()
                .map(PrimaryKeyIdentity::getId)
                .toList();
    }

    public boolean isAlreadyVoted(User user) {
        return this.getVotingUsers().contains(user);
    }
}
