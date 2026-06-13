package com.valka.guild_service.model.dto.guild;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuildGetResponseDTO {
    private UUID guildId;
    private String name;
    private UUID leaderCharacterId;
    private String description;
    private LocalDateTime createdAt;
    private long memberCount;
}
