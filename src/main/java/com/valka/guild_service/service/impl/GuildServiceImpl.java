package com.valka.guild_service.service.impl;

import bg.senpai.common.dtos.EntityAlreadyExists;
import com.valka.guild_service.kafka.producer.GuildProducer;
import com.valka.guild_service.model.dto.guild.GuildCreateRequestDTO;
import com.valka.guild_service.model.dto.guild.GuildGetRequestDTO;
import com.valka.guild_service.model.dto.guild.GuildUpdateRequestDTO;
import com.valka.guild_service.model.entity.Guild;
import com.valka.guild_service.model.event.GuildCreateEvent;
import com.valka.guild_service.model.event.GuildDeleteEvent;
import com.valka.guild_service.model.event.GuildUpdateEvent;
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
    private final GuildProducer guildProducer;

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

    @Override
    public Guild updateGuild(GuildUpdateEvent event){
        Guild guild = findById(UUID.fromString(event.getGuildId()));

        String name = event.getName();
        if(name != null && !name.isBlank()){
            guild.setName(name);
        }

        String description = event.getDescription();
        if(description != null && !description.isBlank() ){
            guild.setDescription(description);
        }

        String leaderCharacterId = event.getLeaderCharacterId();
        if(leaderCharacterId != null && !leaderCharacterId.isBlank()){
            guild.setLeaderCharacterId(UUID.fromString(leaderCharacterId));
        }

        return guildRepository.save(guild);
    }

    public Guild findById(UUID guildId){
        return guildRepository.findById(guildId)
                .orElseThrow(() -> new EntityNotFoundException("Guild not found"));
    }

    @Override
    public void deleteGuild(GuildDeleteEvent event) {
        Guild guild = findById(UUID.fromString(event.getGuildId()));

        UUID trueLeaderId = guild.getLeaderCharacterId();
        UUID requesterId = UUID.fromString(event.getMemberId());

        if (!trueLeaderId.equals(requesterId)) {
            throw new IllegalArgumentException("Only the true guild leader can delete this guild!");
        }

        guildRepository.delete(guild);
    }

    @Override
    public GuildGetRequestDTO getGuild(UUID guildId){
        Guild guild = findById(guildId);

        GuildGetRequestDTO dto = GuildGetRequestDTO.builder()
                .name(guild.getName())
                .guildId(guild.getId())
                .createdAt(guild.getCreatedAt())
                .description(guild.getDescription())
                .leaderCharacterId(guild.getLeaderCharacterId())
                .memberCount(guild.getMembers().size())
                .build();

        return dto;
    }

    @Override
    public void sendCreateRequest(UUID characterId, GuildCreateRequestDTO dto) {
        GuildCreateEvent event = GuildCreateEvent.newBuilder()
                .setDescription(dto.getDescription())
                .setName(dto.getName())
                .setLeaderCharacterId(characterId.toString())
                .build();

        guildProducer.sendGuildCreatedEvent(event);
    }

    @Override
    public void sendUpdateRequest(UUID leaderId, GuildUpdateRequestDTO dto) {
        GuildUpdateEvent event = GuildUpdateEvent.newBuilder()
                .setMemberId(leaderId.toString())
                .setDescription(dto.getDescription())
                .setGuildId(dto.getGuildId().toString())
                .setLeaderCharacterId(dto.getLeaderCharacterId().toString())
                .build();

        guildProducer.sendGuildUpdatedEvent(event);
    }

    @Override
    public void sendDeleteRequest(UUID memberId, UUID guildId) {
        GuildDeleteEvent event = GuildDeleteEvent.newBuilder()
                .setGuildId(guildId.toString())
                .setMemberId(memberId.toString())
                .build();

        guildProducer.sendGuildDeletedEvent(event);
    }
}
