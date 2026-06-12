package com.valka.guild_service.service.impl;

import bg.senpai.common.dtos.EntityAlreadyExists;
import com.valka.guild_service.model.entity.Guild;
import com.valka.guild_service.model.event.GuildCreateEvent;
import com.valka.guild_service.repository.GuildRepository;
import com.valka.guild_service.service.GuildService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class GuildServiceImpl implements GuildService {
    private final GuildRepository guildRepository;

    @Override
    public Guild createGuild(GuildCreateEvent event){
        UUID leaderCharacterId = UUID.fromString(event.getLeaderCharacterId());

        if(guildRepository.existsByLeaderCharacterId(leaderCharacterId)){
            throw new EntityAlreadyExists("Guild already exist");
        }

        Guild guild = Guild.builder()
                .name(event.getName())
                .leaderCharacterId(leaderCharacterId)
                .description(event.getDescription())
                .build();

        return guildRepository.save(guild);
    }

    public Guild findById(UUID guildId){
        return guildRepository.findById(guildId)
                .orElseThrow(() -> new EntityNotFoundException("Guild not found"));
    }
}
