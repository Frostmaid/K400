package de.kloen.k400.listener.command;

import de.kloen.k400.db.K400UserRepository;
import de.kloen.k400.db.KarmaRepository;
import de.kloen.k400.listener.UserService;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.kloen.k400.listener.Karma.RAT_KARMA;
import static de.kloen.k400.listener.MessageUtil.isCommand;
import static de.kloen.k400.listener.MessageUtil.isExpectedCommand;
import static de.kloen.k400.listener.command.Commands.RAT;
import static java.lang.String.format;

@Component
public class Creature extends ListenerAdapter {

    private final K400UserRepository userRepository;

    private final KarmaRepository karmaRepository;

    private final UserService userService;

    @Autowired
    public Creature(K400UserRepository userRepository, KarmaRepository karmaRepository, UserService userService) {
        this.userRepository = userRepository;
        this.karmaRepository = karmaRepository;
        this.userService = userService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        String message = event.getMessage().getContentRaw();

        if (isCommand(message)) {
            if (isExpectedCommand(message, RAT)) {
                rat(event, author);
            }
        }
    }

    public void rat(MessageReceivedEvent event, User author) {
        if (!author.isBot()) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), RAT_KARMA);
            event.getChannel()
                    .sendMessage(format("Plötzlich springt eine Maulwurfsratte in den Channel! " +
                                    "Zum Glück hat %s einen Baseballschläger parat und erledigt das Viech! (%s Karma)",
                            author.getName(), RAT_KARMA))
                    .queue();
        }
    }
}
