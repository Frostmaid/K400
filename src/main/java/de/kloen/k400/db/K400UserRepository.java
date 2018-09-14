package de.kloen.k400.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface K400UserRepository extends JpaRepository<K400User, UUID> {

    Optional<K400User> findByDiscordId(String discordId);

    default K400User getOrInit(String discordId) {
        Optional<K400User> userOpt = findByDiscordId(discordId);

        return userOpt.orElseGet(() -> save(new K400User()));

    }
}
