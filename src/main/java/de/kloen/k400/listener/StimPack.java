package de.kloen.k400.listener;

import de.kloen.k400.db.K400User;
import de.kloen.k400.db.K400UserRepository;
import de.kloen.k400.db.KarmaRepository;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static de.kloen.k400.listener.CalculateKarma.POSITIVE_KARMA_VALUE;
import static de.kloen.k400.listener.MessageUtil.getCorrectedCommand;

@Service
public class StimPack extends ListenerAdapter {


    private K400UserRepository userRepository;
    private KarmaRepository karmaRepository;

    @Autowired
    public StimPack(K400UserRepository userRepository, KarmaRepository karmaRepository) {
        this.userRepository = userRepository;
        this.karmaRepository = karmaRepository;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (getCorrectedCommand(event).substring(0, 5).equals("!stim")) {
            User author = event.getAuthor();
            String userToHeal = event.getMessage().getContentRaw().substring(6);
            Optional<K400User> k400User = event.getJDA().getUsers()
                    .stream()
                    .filter(user -> user.getName().equals(userToHeal))
                    .filter(user -> !user.isBot())
                    .map(user -> userRepository.getOrInit(user))
                    .findFirst();

            if (!author.isBot() && k400User.isPresent()) {
                karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), POSITIVE_KARMA_VALUE);

                String message = author.getName() + " rammt " + userToHeal + " ein Stimpak in den Arm!\n"
                        + author.getName() + " erhält " + POSITIVE_KARMA_VALUE + " Karma";
                event.getChannel().sendMessage(message).queue();
            }
        }
    }
}
