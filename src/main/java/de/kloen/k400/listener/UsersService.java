package de.kloen.k400.listener;

import de.kloen.k400.db.K400User;
import de.kloen.k400.db.K400UserRepository;
import net.dv8tion.jda.core.JDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    private K400UserRepository userRepository;

    @Autowired
    public UsersService(K400UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<K400User> findUserForName(JDA jda, String userName) {
        return jda.getUsers()
                .stream()
                .filter(user -> user.getName().equals(userName))
                .filter(user -> !user.isBot())
                .map(user -> userRepository.getOrInit(user))
                .findFirst();
    }

}
