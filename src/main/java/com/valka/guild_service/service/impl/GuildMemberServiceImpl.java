package com.valka.guild_service.service.impl;

import bg.senpai.common.dtos.EntityAlreadyExists;
import com.valka.guild_service.config.CacheNames;
import com.valka.guild_service.kafka.producer.GuildMemberProducer;
import com.valka.guild_service.model.dto.guildmember.GuildMemberGetRequestDTO;
import com.valka.guild_service.model.dto.guildmember.JoinRequestDTO;
import com.valka.guild_service.model.dto.guildmember.LeaveRequestDTO;
import com.valka.guild_service.model.dto.guildmember.UpdateRequestDTO;
import com.valka.guild_service.model.entity.Guild;
import com.valka.guild_service.model.entity.GuildMember;
import com.valka.guild_service.model.event.JoinRequestEvent;
import com.valka.guild_service.model.event.LeaveRequestEvent;
import com.valka.guild_service.model.event.UpdateRequestEvent;
import com.valka.guild_service.repository.GuildMemberRepository;
import com.valka.guild_service.service.GuildMemberService;
import com.valka.guild_service.service.GuildService;
import com.valka.guild_service.model.entity.EGuildRank;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuildMemberServiceImpl implements GuildMemberService {
    private final GuildService guildService;
    private final GuildMemberRepository guildMemberRepository;
    private final GuildMemberProducer producer;

    @Override
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

    @CacheEvict(
            cacheNames = CacheNames.GUILD_MEMBER_DETAILS,
            key = "#event.guildMemberId"
    )
    @Override
    public void leaveGuildMemberFromGuild(LeaveRequestEvent event){
        UUID memberId = UUID.fromString(event.getGuildMemberId());

        if(!guildMemberRepository.existsById(memberId)){
            throw new IllegalArgumentException("GuildMember does not exist");
        }

        guildMemberRepository.deleteById(memberId);
    }

    @CacheEvict(
            cacheNames = CacheNames.GUILD_MEMBER_DETAILS,
            key = "#event.id"
    )
    @Override
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

    @Cacheable(
            cacheNames = CacheNames.GUILD_MEMBER_DETAILS,
            key = "#memberId"
    )
    public GuildMemberGetRequestDTO getMember(UUID memberId){
        GuildMember member = findById(memberId);

        return GuildMemberGetRequestDTO.builder()
                    .id(member.getId())
                    .guild(member.getGuild())
                    .characterId(member.getCharacterId())
                .build();
    }

    public GuildMember findById(UUID id){
        return guildMemberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("GuildMember not found"));
    }

    public void sendJoinRequest(JoinRequestDTO dto) {
        JoinRequestEvent event = JoinRequestEvent.newBuilder()
                .setGuildId(dto.getGuildId().toString())
                .setName(dto.getName())
                .setCharacterId(dto.getCharacterId().toString())
                .setRank(com.valka.guild_service.model.event.EGuildRank.valueOf(dto.getRank().toUpperCase()))
                .build();

        producer.sendJoinRequestEvent(event);
    }

    public void sendLeaveRequest(LeaveRequestDTO dto) {
        LeaveRequestEvent event = LeaveRequestEvent.newBuilder()
                .setGuildMemberId(dto.getGuildMemberId().toString())
                .build();

        producer.sendLeaveRequestEvent(event);
    }

    public void sendUpdateRequest(UpdateRequestDTO dto) {
        UpdateRequestEvent.Builder builder = UpdateRequestEvent.newBuilder()
                .setId(dto.getId().toString());

        if (dto.getGuildId() != null) {
            builder.setGuildId(dto.getGuildId().toString());
        }

        if (dto.getRank() != null) {
            builder.setRank(com.valka.guild_service.model.event.EGuildRank.valueOf(dto.getRank().toUpperCase()));
        }

        UpdateRequestEvent event = builder.build();

        producer.sendUpdateRequestEvent(event);
    }
}
