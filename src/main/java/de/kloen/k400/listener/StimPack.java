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

import static de.kloen.k400.listener.Commands.STIM_PACK;
import static de.kloen.k400.listener.Karma.STIM_PACK_KARMA_GAIN;
import static de.kloen.k400.listener.MessageUtil.isExpectedCommand;
import static java.lang.String.format;

@Service
public class StimPack extends ListenerAdapter {

    private K400UserRepository userRepository;
    private KarmaRepository karmaRepository;
    private UsersService usersService;

    @Autowired
    public StimPack(K400UserRepository userRepository, KarmaRepository karmaRepository, UsersService usersService) {
        this.userRepository = userRepository;
        this.karmaRepository = karmaRepository;
        this.usersService = usersService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (isExpectedCommand(event, STIM_PACK)) {
            User author = event.getAuthor();
            String userToHeal = event.getMessage().getContentRaw().substring(6);

            Optional<K400User> k400UserToHeal = usersService.findUserForName(event.getJDA(), userToHeal);

            if (!author.isBot() && k400UserToHeal.isPresent() && !k400UserToHeal.get().discordId().equals(author.getId())) {
                karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), STIM_PACK_KARMA_GAIN);

                String message = format("%s rammt %s ein Stimpak in den Arm. Puh! Das war knapp (+%s Karma)",
                        author.getName(), userToHeal, STIM_PACK_KARMA_GAIN);

                event.getChannel().sendMessage(message).queue();
            }
        }
    }
}
