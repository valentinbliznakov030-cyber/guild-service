package com.valka.guild_service.kafka.producer;

import com.valka.guild_service.model.event.GuildCreateEvent;
import com.valka.guild_service.model.event.GuildDeleteEvent;
import com.valka.guild_service.model.event.GuildUpdateEvent;
import com.valka.guild_service.service.GuildService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class GuildProducer {

    private final String topic;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public GuildProducer(
            @Value("${app.kafka.topics.guild-events}") String topic,
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendGuildCreatedEvent(GuildCreateEvent event) {
        kafkaTemplate.send(topic, event.getName(), event);
    }

    public void sendGuildUpdatedEvent(GuildUpdateEvent event) {
        kafkaTemplate.send(topic, event.getGuildId(), event);
    }

    public void sendGuildDeletedEvent(GuildDeleteEvent event) {
        kafkaTemplate.send(topic, event.getGuildId(), event);
    }
}

