package de.kloen.k400;

import de.kloen.k400.db.K400User;
import de.kloen.k400.db.K400UserRepository;
import de.kloen.k400.db.Karma;
import de.kloen.k400.db.KarmaRepository;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static de.kloen.k400.MessageUtil.getCorrectedCommand;

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
        if (getCorrectedCommand(event).equals("!status")) {
            User author = event.getAuthor();
            K400User k400User = k400UserRepository.getOrInit(author.getId());
            Karma karma = karmaRepository.getOrInitForK400User(k400User);
            event.getChannel().sendMessage(author.getName() + " hat " + karma.value() + " Karma und den Status " + karma.title()).queue();
        }

    }
}
