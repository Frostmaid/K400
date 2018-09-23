package de.kloen.k400.listener.command;

import de.kloen.k400.db.K400User;
import de.kloen.k400.db.K400UserRepository;
import de.kloen.k400.db.KarmaRepository;
import de.kloen.k400.listener.UserService;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static de.kloen.k400.listener.Karma.INSTITUT_KARMA;
import static de.kloen.k400.listener.Karma.RECALL_KARMA;
import static de.kloen.k400.listener.MessageUtil.*;
import static de.kloen.k400.listener.command.Commands.INSTITUT;
import static de.kloen.k400.listener.command.Commands.RECALL;
import static java.lang.String.format;

@Component
public class Synth extends ListenerAdapter {

    private final K400UserRepository userRepository;

    private final KarmaRepository karmaRepository;

    private final UserService userService;

    @Autowired
    public Synth(K400UserRepository userRepository, KarmaRepository karmaRepository, UserService userService) {
        this.userRepository = userRepository;
        this.karmaRepository = karmaRepository;
        this.userService = userService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        String message = event.getMessage().getContentRaw();

        if (isCommand(message)) {
            if (isExpectedCommand(message, INSTITUT)) {
                institut(event, author, message.substring(INSTITUT.length() + 1));
            } else if (isExpectedCommand(message, RECALL)) {
                recall(event, author, message.substring(RECALL.length() + 1));
            }
        }
    }

    private void institut(MessageReceivedEvent event, User author, String userName) {
        Optional<K400User> user = userService.findUserForName(event.getJDA(), userName);
        if (isValidTargetUser(author, user)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), INSTITUT_KARMA);
            event.getChannel()
                    .sendMessage(format("Dank eines Tipps von %s konnte das Institut %s finden und gegen einen Synth austauschen (%s Karma)",
                            author.getName(), userName, INSTITUT_KARMA))
                    .queue();
        }
    }

    private void recall(MessageReceivedEvent event, User author, String userName) {
        Optional<K400User> user = userService.findUserForName(event.getJDA(), userName);
        if (isValidTargetUser(author, user)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), RECALL_KARMA);
            event.getChannel()
                    .sendMessage(format("%s liest einen Recall-Code vor. Plötzlich erinnert sich %s an die Flucht vorm Institut. %s ist ein geflüchteter Synth! (%s Karma)",
                            author.getName(), userName, userName, RECALL_KARMA))
                    .queue();
        }
    }
}
