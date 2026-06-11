package com.valka.guild_service.model.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRequestDTO {
    private UUID id;
    private UUID guildId;
    private String rank;
}
