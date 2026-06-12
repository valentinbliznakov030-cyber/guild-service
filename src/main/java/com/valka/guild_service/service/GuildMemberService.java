package com.valka.guild_service.service;

import com.valka.guild_service.config.CacheNames;
import com.valka.guild_service.model.dto.guildmember.GuildMemberGetRequestDTO;
import com.valka.guild_service.model.dto.guildmember.JoinRequestDTO;
import com.valka.guild_service.model.dto.guildmember.UpdateRequestDTO;
import com.valka.guild_service.model.entity.GuildMember;
import com.valka.guild_service.model.event.JoinRequestEvent;
import com.valka.guild_service.model.event.LeaveRequestEvent;
import com.valka.guild_service.model.event.UpdateRequestEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.UUID;

public interface GuildMemberService {
    GuildMember joinGuild(JoinRequestEvent event);

    void sendJoinRequest(JoinRequestDTO dto);

    void sendLeaveRequest(UUID dto);

    void sendUpdateRequest(UUID userId, UpdateRequestDTO dto);

    @CacheEvict(
            cacheNames = CacheNames.GUILD_MEMBER_DETAILS,
            key = "#memberId"
    )
    void leaveGuildMemberFromGuild(LeaveRequestEvent event);

    @CacheEvict(
            cacheNames = CacheNames.GUILD_MEMBER_DETAILS,
            key = "#memberId"
    )
    GuildMember updateGuildMember(UpdateRequestEvent event);

    @Cacheable(
            cacheNames = CacheNames.GUILD_MEMBER_DETAILS,
            key = "#memberId"
    )
    GuildMemberGetRequestDTO getMember(UUID memberId);

    GuildMember findById(UUID id);

}
