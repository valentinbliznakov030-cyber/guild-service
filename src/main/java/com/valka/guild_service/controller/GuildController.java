package com.valka.guild_service.controller;

import com.valka.guild_service.model.entity.GuildMember;
import com.valka.guild_service.service.GuildMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import com.valka.guild_service.model.dto.guildmember.GuildMemberGetRequestDTO;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/guild-member")
public class GuildController {
    private final GuildMemberService guildMemberService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMember(@PathVariable UUID id){
        GuildMemberGetRequestDTO member = guildMemberService.getMember(id);

        return ResponseEntity.ok(member);
    }
}
