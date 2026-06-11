package com.valka.guild_service.service.impl;

import com.valka.guild_service.kafka.producer.GuildMemberProducer;
import com.valka.guild_service.model.entity.Guild;
import com.valka.guild_service.model.entity.GuildMember;
import com.valka.guild_service.model.event.JoinRequestEvent;
import com.valka.guild_service.repository.GuildRepository;
import com.valka.guild_service.service.GuildMemberService;
import com.valka.guild_service.service.GuildService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuildServiceImpl {
    private final GuildRepository guildRepository;
    private final GuildService guildService;
    private final GuildMemberProducer producer;

    public Guild findById(UUID guildId){
        return guildRepository.findById(guildId)
                .orElseThrow(() -> new EntityNotFoundException("Guild not found"));
    }
}
