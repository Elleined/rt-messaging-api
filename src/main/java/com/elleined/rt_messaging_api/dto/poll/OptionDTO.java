package com.elleined.rt_messaging_api.dto.poll;

import com.elleined.rt_messaging_api.dto.DTO;
import com.elleined.rt_messaging_api.dto.user.UserDTO;
import com.elleined.rt_messaging_api.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OptionDTO extends DTO {
    private UserDTO creatorDTO;
    private String option;
    private PollDTO pollDTO;

    @Builder
    public OptionDTO(int id,
                     LocalDateTime createdAt,
                     UserDTO creatorDTO,
                     String option,
                     PollDTO pollDTO) {
        super(id, createdAt);
        this.creatorDTO = creatorDTO;
        this.option = option;
        this.pollDTO = pollDTO;
    }

    @Override
    public OptionDTO addLinks(User currentUser, boolean doInclude) {
        super.addLinks(currentUser, doInclude);
        return this;
    }

    @Override
    protected List<Link> getAllRelatedLinks(User currentUser, boolean doInclude) {
        return List.of();
    }

    @Override
    protected List<Link> getAllSelfLinks(User currentUser, boolean doInclude) {
        return List.of();
    }
}
