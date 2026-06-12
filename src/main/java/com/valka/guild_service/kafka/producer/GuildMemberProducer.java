package com.valka.guild_service.kafka.producer;

import com.valka.guild_service.model.event.JoinRequestEvent;
import com.valka.guild_service.model.event.LeaveRequestEvent;
import com.valka.guild_service.model.event.UpdateRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class GuildMemberProducer {

    private final String topic;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public GuildMemberProducer(
            @Value("${app.kafka.topics.guild-member-events}") String topic,
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendJoinRequestEvent(JoinRequestEvent event) {
        kafkaTemplate.send(
                topic,
                event.getGuildId(),
                event
        );
    }

    public void sendLeaveRequestEvent(LeaveRequestEvent event) {
        kafkaTemplate.send(
                topic,
                event.getGuildMemberId(),
                event
        );
    }

    public void sendUpdateRequestEvent(UpdateRequestEvent event) {
        kafkaTemplate.send(
                topic,
                event.getId(),
                event
        );
    }
}