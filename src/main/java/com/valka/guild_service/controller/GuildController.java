package com.valka.guild_service.controller;

import com.valka.guild_service.model.dto.guildmember.JoinRequestDTO;
import com.valka.guild_service.model.entity.GuildMember;
import com.valka.guild_service.service.GuildMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import com.valka.guild_service.model.dto.guildmember.GuildMemberGetRequestDTO;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/guild-member")
public class GuildController {
    private final GuildMemberService guildMemberService;

    @GetMapping("/{id}")
    public ResponseEntity<GuildMemberGetRequestDTO> getMember(@PathVariable UUID id){
        GuildMemberGetRequestDTO member = guildMemberService.getMember(id);

        return ResponseEntity.ok(member);
    }

    @PostMapping("/join")
    public ResponseEntity<Void> joinGuild(@RequestBody JoinRequestDTO dto) {
        guildMemberService.sendJoinRequest(dto);

        return ResponseEntity.accepted().build();
    }
}
