package com.valka.guild_service.model.dto;

import com.valka.guild_service.model.entity.Guild;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class GuildMemberGetRequestDTO {
    private UUID id;
    private Guild guild;
    private UUID characterId;
    private String rank;
}
