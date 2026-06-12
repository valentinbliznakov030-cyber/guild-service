package com.valka.guild_service.kafka.consumer;

import com.valka.guild_service.model.entity.EGuildRank;
import com.valka.guild_service.model.entity.GuildMember;
import com.valka.guild_service.model.event.GuildCreateEvent;
import com.valka.guild_service.model.event.GuildDeleteEvent;
import com.valka.guild_service.model.event.GuildUpdateEvent;
import com.valka.guild_service.model.event.UpdateRequestEvent;
import com.valka.guild_service.service.GuildMemberService;
import com.valka.guild_service.service.GuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@KafkaListener(topics = "${app.kafka.topics.guild-events}")
@RequiredArgsConstructor
public class GuildConsumer {
    private final GuildMemberService guildMemberService;
    private final GuildService guildService;

    @KafkaHandler
    public void createGuild(GuildCreateEvent event){
        guildService.createGuild(event);
    }

    @KafkaHandler
    public void updateGuild(GuildUpdateEvent event){
        GuildMember member = guildMemberService.findById(UUID.fromString(event.getMemberId()));

        if(member.getRank() != EGuildRank.LEADER){
            throw new IllegalArgumentException("Only member with role LEADER can change");
        }

        guildService.updateGuild(event);
    }

    @KafkaHandler
    public void deleteGuild(GuildDeleteEvent event){
        GuildMember member = guildMemberService.findById(UUID.fromString(event.getMemberId()));

        if(member.getRank() != EGuildRank.LEADER){
            throw new IllegalArgumentException("Only member with tole LEADER can delete");
        }

        guildService.deleteGuild(event);
    }
}
