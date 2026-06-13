package com.valka.guild_service.repository;

import com.valka.guild_service.model.entity.GuildMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GuildMemberRepository extends JpaRepository<GuildMember, UUID> {
    boolean existsByCharacterId(UUID id);
    Page<GuildMember> findByGuildId(UUID guildId, Pageable pageable);
}
