package com.valka.guild_service.service;

import com.valka.guild_service.model.entity.Guild;

import java.util.UUID;

public interface GuildService {
    Guild findById(UUID id);
}
