package de.kloen.k400.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class K400User {

    @Id
    @GeneratedValue
    private UUID id;

    private String discordId;

    private String name;

    public String name() {
        return name;
    }

    public String discordId() {
        return discordId;
    }
}
