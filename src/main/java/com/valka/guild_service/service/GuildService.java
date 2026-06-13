package com.valka.guild_service.service;

import com.valka.guild_service.model.dto.guild.GuildCreateRequestDTO;
import com.valka.guild_service.model.dto.guild.GuildGetResponseDTO;
import com.valka.guild_service.model.dto.guild.GuildUpdateRequestDTO;
import com.valka.guild_service.model.entity.Guild;
import com.valka.guild_service.model.event.GuildCreateEvent;
import com.valka.guild_service.model.event.GuildDeleteEvent;
import com.valka.guild_service.model.event.GuildUpdateEvent;

import java.util.UUID;

public interface GuildService {
    Guild createGuild(GuildCreateEvent event);

    Guild updateGuild(GuildUpdateEvent event);

    Guild findById(UUID id);

    void deleteGuild(GuildDeleteEvent event);

    GuildGetResponseDTO getGuild(UUID guildId);

    void sendCreateRequest(UUID characterId, GuildCreateRequestDTO dto);

    void sendUpdateRequest(UUID leaderId, GuildUpdateRequestDTO dto);

    void sendDeleteRequest(UUID memberId, UUID guildId);
}
