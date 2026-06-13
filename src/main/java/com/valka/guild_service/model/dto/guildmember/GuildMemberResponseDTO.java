package com.valka.guild_service.model.dto.guildmember;

import com.valka.guild_service.model.entity.Guild;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class GuildMemberResponseDTO {
    private UUID id;
    private UUID guildId;
    private UUID characterId;
    private String rank;
}
