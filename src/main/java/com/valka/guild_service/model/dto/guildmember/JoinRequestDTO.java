package com.valka.guild_service.model.dto.guildmember;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRequestDTO {
    private UUID guildId;
    private String name;
    private UUID characterId;
    private String rank;
}
