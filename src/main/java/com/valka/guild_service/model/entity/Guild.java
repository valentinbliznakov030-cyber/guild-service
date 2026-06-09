package com.valka.guild_service.model.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "guilds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guild {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true, length = 45)
    private String name;

    // Връзка към външния Character Service - кой притежава гилдията
    @Column(name = "leader_character_id", nullable = false)
    private UUID leaderCharacterId;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "level", nullable = false)
    private int level = 1;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "guild", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<GuildMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "guild", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Quest> quests = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.level == 0) {
            this.level = 1;
        }
    }
}