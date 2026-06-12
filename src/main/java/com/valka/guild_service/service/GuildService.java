package com.valka.guild_service.service;

import com.valka.guild_service.model.entity.Guild;
import com.valka.guild_service.model.event.GuildCreateEvent;

import java.util.UUID;

public interface GuildService {
    Guild createGuild(GuildCreateEvent event);

    Guild findById(UUID id);
}
