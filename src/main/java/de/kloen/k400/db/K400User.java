package de.kloen.k400.db;

import com.google.common.base.Preconditions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Optional;
import java.util.UUID;

@Entity
public class K400User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String discordId;

    private String name;

    private String team;

    public K400User(String discordId, String name) {
        this.discordId = Preconditions.checkNotNull(discordId, "discordId can not be null!");
        this.name = Preconditions.checkNotNull(name, "name can not be null!");
    }

    private K400User() {
    }

    public String name() {
        return name;
    }

    public String discordId() {
        return discordId;
    }

    public Optional<String> team() {
        if (team == null || team.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(team);
    }

    public void joinTeam(String team) {
        this.team = Preconditions.checkNotNull(team);
    }

}
