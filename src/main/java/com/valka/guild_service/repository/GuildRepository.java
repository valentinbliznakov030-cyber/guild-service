package com.valka.guild_service.repository;

import com.valka.guild_service.model.entity.Guild;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GuildRepository  extends JpaRepository<Guild, UUID> {
    boolean existsById(UUID id);
    boolean existsByLeaderCharacterId(UUID id);
}
