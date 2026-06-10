package com.valka.guild_service.service.impl;

import bg.senpai.common.dtos.EntityAlreadyExists;
import com.valka.guild_service.model.entity.Guild;
import com.valka.guild_service.model.entity.GuildMember;
import com.valka.guild_service.model.event.JoinRequestEvent;
import com.valka.guild_service.model.event.LeaveRequestEvent;
import com.valka.guild_service.model.event.UpdateRequestEvent;
import com.valka.guild_service.repository.GuildMemberRepository;
import com.valka.guild_service.service.GuildService;
import com.valka.guild_service.model.entity.EGuildRank;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuildMemberServiceImpl {
    private final GuildService guildService;
    private final GuildMemberRepository guildMemberRepository;

    public GuildMember joinGuild(JoinRequestEvent event){
        if(guildMemberRepository.existsByCharacterId(UUID.fromString(event.getCharacterId()))){
            throw new EntityAlreadyExists("GuildMember already exists");
        }

        Guild guild = guildService.findById(UUID.fromString(event.getGuildId()));

        GuildMember guildMember = GuildMember.builder()
                    .rank(EGuildRank.valueOf(event.getRank().name()))
                    .guild(guild)
                    .characterId(UUID.fromString(event.getCharacterId()))
                .build();

        return guildMemberRepository.save(guildMember);
    }

    public void leaveGuildMemberFromGuild(LeaveRequestEvent event){
        UUID memberId = UUID.fromString(event.getGuildMemberId());

        if(!guildMemberRepository.existsById(memberId)){
            throw new IllegalArgumentException("GuildMember does not exist");
        }

        guildMemberRepository.deleteById(memberId);
    }

    public GuildMember updateGuildMember(UpdateRequestEvent event){
        GuildMember guildMember = findById(UUID.fromString(event.getId()));

        String guildId = event.getGuildId();
        if(!guildId.isBlank() && guildId != null){
            Guild guild = guildService.findById(UUID.fromString(guildId));
            guildMember.setGuild(guild);
        }

        com.valka.guild_service.model.event.EGuildRank guildRank = event.getRank();
        if(guildRank != null){
            guildMember.setRank(EGuildRank.valueOf(guildRank.name()));
        }

        return guildMemberRepository.save(guildMember);
    }

    public GuildMember findById(UUID id){
        return guildMemberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("GuildMember not found"));
    }
}
