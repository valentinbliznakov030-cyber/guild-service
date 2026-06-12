package com.valka.guild_service.model.dto.guild;

import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Builder
public class GuildCreateRequestDTO {
    private String name;
    private String description;
}
