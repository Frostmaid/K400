package de.kloen.k400.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface KarmaRepository extends JpaRepository<Karma, UUID> {

    default Karma increaseKarmaForUser(K400User k400User, int karmaValue) {
        Optional<Karma> karmaOpt = findByK400User(k400User);

        if (karmaOpt.isPresent()) {
            Karma karma = karmaOpt.get();
            return updateKarma(karmaValue, karma);
        } else {
            return save(new Karma(k400User, karmaValue));
        }
    }

    default Karma updateKarma(int karmaValue, Karma karma) {
        karma.addKarma(karmaValue);
        return save(karma);
    }

    Optional<Karma> findByK400User(K400User user);

    default Karma getOrInitForK400User(K400User user){
        Optional<Karma> karma = findByK400User(user);

        return karma.orElseGet(() -> save(new Karma(user)));
    }

}
