package com.valka.guild_service.service;

import com.valka.guild_service.config.CacheNames;
import com.valka.guild_service.model.dto.JoinRequestDTO;
import com.valka.guild_service.model.dto.LeaveRequestDTO;
import com.valka.guild_service.model.dto.UpdateRequestDTO;
import com.valka.guild_service.model.entity.GuildMember;
import com.valka.guild_service.model.event.JoinRequestEvent;
import com.valka.guild_service.model.event.LeaveRequestEvent;
import com.valka.guild_service.model.event.UpdateRequestEvent;
import org.springframework.cache.annotation.CacheEvict;

import java.util.UUID;

public interface GuildMemberService {
    GuildMember joinGuild(JoinRequestEvent event);

    void sendJoinRequest(JoinRequestDTO dto);

    void sendLeaveRequest(LeaveRequestDTO dto);

    void sendUpdateRequest(UpdateRequestDTO dto);

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

    GuildMember findById(UUID id);
}
