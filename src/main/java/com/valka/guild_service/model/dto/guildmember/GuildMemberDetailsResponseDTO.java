package com.valka.guild_service.model.dto.guildmember;

import com.valka.guild_service.model.dto.PageResponse;
import com.valka.guild_service.model.entity.GuildMember;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuildMemberDetailsResponseDTO extends PageResponse {
    private List<GuildMemberResponseDTO> guildMembers;

    public GuildMemberDetailsResponseDTO(PageResponse page, List<GuildMemberResponseDTO> guildMembers){
        super(page);
        this.guildMembers = guildMembers;
    }
}
