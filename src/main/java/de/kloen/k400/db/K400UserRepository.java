package de.kloen.k400.db;

import net.dv8tion.jda.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface K400UserRepository extends JpaRepository<K400User, UUID> {

    Optional<K400User> findByDiscordId(String discordId);

    default K400User getOrInit(User user) {
        Optional<K400User> userOpt = findByDiscordId(user.getId());

        return userOpt.orElseGet(() -> save(new K400User(user.getId(), user.getName())));

    }
}
