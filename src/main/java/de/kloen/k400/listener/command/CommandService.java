package de.kloen.k400.listener.command;

import de.kloen.k400.db.K400User;
import de.kloen.k400.db.K400UserRepository;
import de.kloen.k400.db.KarmaRepository;
import de.kloen.k400.listener.UserService;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static de.kloen.k400.listener.MessageUtil.isValidTargetUser;

@Component
public class CommandService {

    private final KarmaRepository karmaRepository;

    private final K400UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public CommandService(KarmaRepository karmaRepository, K400UserRepository userRepository, UserService userService) {
        this.karmaRepository = karmaRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public void executeCommandWithTarget(MessageReceivedEvent event, User author, String userName, int karma, String message) {
        Optional<K400User> user = userService.findUserForName(event.getJDA(), userName);
        if (isValidTargetUser(author, user)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), karma);
            event.getChannel()
                    .sendMessage(message)
                    .queue();
        }
    }

    public void executeCommandWithOutTarget(MessageReceivedEvent event, User author, int karma, String message) {
        if (!author.isBot()) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), karma);
            event.getChannel()
                    .sendMessage(message)
                    .queue();
        }
    }
}
