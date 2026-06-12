package com.valka.guild_service.controller;

import bg.senpai.common.config.MemberData;
import com.valka.guild_service.model.dto.guildmember.JoinRequestDTO;
import com.valka.guild_service.model.dto.guildmember.LeaveRequestDTO;
import com.valka.guild_service.model.dto.guildmember.UpdateRequestDTO;
import com.valka.guild_service.service.GuildMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.UUID;
import com.valka.guild_service.model.dto.guildmember.GuildMemberGetRequestDTO;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/guild-member")
public class GuildMemberController {
    private final GuildMemberService guildMemberService;

    @GetMapping("/me")
    public ResponseEntity<GuildMemberGetRequestDTO> getMember(@AuthenticationPrincipal MemberData memberData){
        GuildMemberGetRequestDTO member = guildMemberService.getMember(memberData.getUserId());

        return ResponseEntity.ok(member);
    }

    @PostMapping("/join")
    public ResponseEntity<Void> joinGuild(@RequestBody JoinRequestDTO dto) {
        guildMemberService.sendJoinRequest(dto);

        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/leave/{id}")
    public ResponseEntity<Void> leaveGuild(@PathVariable UUID id) {
        guildMemberService.sendLeaveRequest(id);

        return ResponseEntity.accepted().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateMember(@AuthenticationPrincipal MemberData memberData,
                                             @RequestBody UpdateRequestDTO dto) {
        guildMemberService.sendUpdateRequest(memberData.getUserId(), dto);
        return ResponseEntity.accepted().build();
    }
}
