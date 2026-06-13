package com.valka.guild_service.model.dto.guild;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuildUpdateRequestDTO {
    private UUID guildId;
    private String name;
    private String description;
    private UUID leaderCharacterId;
    private int level;
}
