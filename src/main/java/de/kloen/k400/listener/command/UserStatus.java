package de.kloen.k400.listener.command;

import de.kloen.k400.db.K400User;
import de.kloen.k400.db.K400UserRepository;
import de.kloen.k400.db.Karma;
import de.kloen.k400.db.KarmaRepository;
import de.kloen.k400.listener.UserService;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static de.kloen.k400.db.Karma.titles;
import static de.kloen.k400.listener.MessageUtil.*;
import static de.kloen.k400.listener.command.Commands.KARMA_TITLE;
import static de.kloen.k400.listener.command.Commands.STATUS;
import static java.util.stream.Collectors.joining;

@Service
public class UserStatus extends ListenerAdapter {

    private KarmaRepository karmaRepository;

    private K400UserRepository k400UserRepository;

    private UserService userService;

    @Autowired
    public UserStatus(KarmaRepository karmaRepository, K400UserRepository k400UserRepository, UserService userService) {
        this.karmaRepository = karmaRepository;
        this.k400UserRepository = k400UserRepository;
        this.userService = userService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (isCommand(message)) {
            String correctedCommand = getCorrectedCommand(message);

            if (correctedCommand.length() == STATUS.length() && correctedCommand.equals(STATUS)) {
                ownStatus(event);
            } else if (correctedCommand.length() > STATUS.length() && correctedCommand.substring(0, STATUS.length()).equals(STATUS)) {
                otherUsersStatus(event);
            } else if (isExpectedCommand(event.getMessage().getContentRaw(), KARMA_TITLE)) {
                String titlesMessage = titles
                        .entrySet()
                        .stream()
                        .map(title -> title.getKey() + " (" + title.getValue() + " Karma)")
                        .collect(joining("\n"));

                event.getChannel().sendMessage(titlesMessage).queue();
            }
        }
    }

    private void ownStatus(MessageReceivedEvent event) {
        User author = event.getAuthor();
        K400User k400User = k400UserRepository.getOrInit(author);
        Karma karma = karmaRepository.getOrInitForK400User(k400User);
        event.getChannel().sendMessage(statusMessage(k400User, karma)).queue();
    }

    private void otherUsersStatus(MessageReceivedEvent event) {
        JDA jda = event.getJDA();
        String userName = event.getMessage().getContentRaw().substring(STATUS.length() + 1);

        Optional<K400User> user = userService.findUserForName(jda, userName);
        if (user.isPresent()) {
            String message = statusMessage(user.get(), karmaRepository.getOrInitForK400User(user.get()));
            event.getChannel().sendMessage(message).queue();
        }
    }

    private static String statusMessage(K400User author, Karma karma) {
        return author.name() + " hat " + karma.value() + " Karma und den Titel " + karma.title();
    }
}
