package com.valka.guild_service.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "guild_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuildMember {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guild_id", nullable = false)
    private Guild guild;

    @Column(name = "character_id", nullable = false, unique = true)
    private UUID characterId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rank", nullable = false, length = 20)
    private EGuildRank rank;

    @Column(name = "joined_at", nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    @PrePersist
    protected void onJoin() {
        this.joinedAt = LocalDateTime.now();
    }
}