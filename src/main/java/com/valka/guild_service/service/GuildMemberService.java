package com.valka.guild_service.service;

import com.valka.guild_service.model.entity.GuildMember;

import java.util.UUID;

public interface GuildMemberService {
    GuildMember findById(UUID id);
}
