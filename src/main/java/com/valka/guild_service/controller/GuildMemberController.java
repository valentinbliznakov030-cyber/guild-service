package com.valka.guild_service.controller;

import com.valka.guild_service.model.dto.guildmember.JoinRequestDTO;
import com.valka.guild_service.model.dto.guildmember.LeaveRequestDTO;
import com.valka.guild_service.model.dto.guildmember.UpdateRequestDTO;
import com.valka.guild_service.service.GuildMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import com.valka.guild_service.model.dto.guildmember.GuildMemberGetRequestDTO;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/guild-member")
public class GuildMemberController {
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

    @DeleteMapping("/leave")
    public ResponseEntity<Void> leaveGuild(@RequestBody LeaveRequestDTO dto) {
        guildMemberService.sendLeaveRequest(dto);

        return ResponseEntity.accepted().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateMember(@RequestBody UpdateRequestDTO dto) {
        guildMemberService.sendUpdateRequest(dto);
        return ResponseEntity.accepted().build();
    }
}
