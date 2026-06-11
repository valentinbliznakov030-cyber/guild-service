package com.valka.guild_service.kafka.producer;

import com.valka.guild_service.model.event.JoinRequestEvent;
import com.valka.guild_service.model.event.LeaveRequestEvent;
import com.valka.guild_service.model.event.UpdateRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GuildMemberProducer {

    private static final String TOPIC = "guild-member-topic";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendJoinRequestEvent(JoinRequestEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.getGuildId(),
                event
        );
    }

    public void sendLeaveRequestEvent(LeaveRequestEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.getGuildMemberId(),
                event
        );
    }

    public void sendUpdateRequestEvent(UpdateRequestEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.getId(),
                event
        );
    }
}