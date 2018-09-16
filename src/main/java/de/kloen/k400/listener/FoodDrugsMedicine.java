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

import static de.kloen.k400.listener.Commands.*;
import static de.kloen.k400.listener.Karma.*;
import static de.kloen.k400.listener.MessageUtil.isExpectedCommand;
import static java.lang.String.format;

@Service
public class FoodDrugsMedicine extends ListenerAdapter {

    private K400UserRepository userRepository;

    private KarmaRepository karmaRepository;

    private UsersService usersService;

    @Autowired
    public FoodDrugsMedicine(K400UserRepository userRepository, KarmaRepository karmaRepository, UsersService usersService) {
        this.userRepository = userRepository;
        this.karmaRepository = karmaRepository;
        this.usersService = usersService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        String message = event.getMessage().getContentRaw();

        if (isCommand(message)) {
            if (isExpectedCommand(message, STIM_PACK)) {
                stimPack(event, author, message.substring(STIM_PACK.length() + 1));
            } else if (isExpectedCommand(message, JET)) {
                jet(event, author, message.substring(JET.length() + 1));
            } else if (isExpectedCommand(message, FEV)) {
                fev(event, author, message.substring(FEV.length() + 1));
            }

        }
    }

    private void fev(MessageReceivedEvent event, User author, String fevUserName) {
        Optional<K400User> jetUser = usersService.findUserForName(event.getJDA(), fevUserName);
        if (isValidTargetUser(author, jetUser)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), FEV_KARMA);
            event.getChannel()
                    .sendMessage(format("%s injiziert %s mit einem hochaggressiven FEV-Stamm. %s mutiert und wird zum Supermutanten! (-%s Karma)",
                            author.getName(), fevUserName, fevUserName, FEV_KARMA))
                    .queue();
        }
    }

    private void jet(MessageReceivedEvent event, User author, String jetUserName) {
        Optional<K400User> jetUser = usersService.findUserForName(event.getJDA(), jetUserName);
        if (isValidTargetUser(author, jetUser)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), JET_KARMA);
            event.getChannel()
                    .sendMessage(format("%s verführt %s dazu, einen tiefen Zug vom Jet zu nehmen (-%s Karma)",
                            author.getName(), jetUserName, JET_KARMA))
                    .queue();
        }
    }

    private void stimPack(MessageReceivedEvent event, User author, String userToHeal) {
        Optional<K400User> k400UserToHeal = usersService.findUserForName(event.getJDA(), userToHeal);

        if (isValidTargetUser(author, k400UserToHeal)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), STIM_PACK_KARMA);

            event.getChannel()
                    .sendMessage(format("%s rammt %s ein Stimpak in den Arm. Puh! Das war knapp (+%s Karma)",
                            author.getName(), userToHeal, STIM_PACK_KARMA))
                    .queue();
        }
    }

    private static boolean isCommand(String message) {
        return message.substring(0, 1).equals("!");
    }

    private boolean isValidTargetUser(User author, Optional<K400User> targetUser) {
        return !author.isBot() && targetUser.isPresent() && !targetUser.get().discordId().equals(author.getId());
    }
}
