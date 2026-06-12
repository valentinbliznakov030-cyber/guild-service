package com.valka.guild_service.kafka.consumer;

import com.valka.guild_service.model.event.JoinRequestEvent;
import com.valka.guild_service.model.event.LeaveRequestEvent;
import com.valka.guild_service.model.event.UpdateRequestEvent;
import com.valka.guild_service.service.GuildMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(
        topics = "${app.kafka.topics.guild-member-events}",
        groupId = "guild-service-group"
)
@RequiredArgsConstructor
@Slf4j
public class GuildMemberConsumer {

    private final GuildMemberService guildMemberService;

    @KafkaHandler
    public void joinGuild(JoinRequestEvent event) {
        guildMemberService.joinGuild(event);
    }

    @KafkaHandler
    public void leaveGuild(LeaveRequestEvent event) {
        guildMemberService.leaveGuildMemberFromGuild(event);
    }

    @KafkaHandler
    public void updateGuildMember(UpdateRequestEvent event) {
        guildMemberService.updateGuildMember(event);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object event) {
        log.warn("Unknown event received: {}", event);
    }
}