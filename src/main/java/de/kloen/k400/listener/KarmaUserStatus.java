package de.kloen.k400.listener;

import de.kloen.k400.db.K400User;
import de.kloen.k400.db.K400UserRepository;
import de.kloen.k400.db.Karma;
import de.kloen.k400.db.KarmaRepository;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static de.kloen.k400.listener.MessageUtil.getCorrectedCommand;
import static java.util.stream.Collectors.joining;

@Service
public class KarmaUserStatus extends ListenerAdapter {

    private KarmaRepository karmaRepository;
    private K400UserRepository k400UserRepository;

    @Autowired
    public KarmaUserStatus(KarmaRepository karmaRepository, K400UserRepository k400UserRepository) {
        this.karmaRepository = karmaRepository;
        this.k400UserRepository = k400UserRepository;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String correctedCommand = getCorrectedCommand(event);

        if (correctedCommand.length() == 7 && correctedCommand.equals("!status")) {
            ownStatus(event);
        } else if (correctedCommand.length() > 7 && correctedCommand.substring(0, 7).equals("!status")) {
            otherUsersStatus(event);
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
        String userName = event.getMessage().getContentRaw().substring(8);
        String message = jda.getUsers()
                .stream()
                .filter(user -> user.getName().equals(userName))
                .map(user -> k400UserRepository.getOrInit(user))
                .map(k400User -> statusMessage(k400User, karmaRepository.getOrInitForK400User(k400User)))
                .collect(joining("\n"));

        event.getChannel().sendMessage(message).queue();
    }

    private static String statusMessage(K400User author, Karma karma) {
        return author.name() + " hat " + karma.value() + " Karma und den Titel " + karma.title();
    }
}
