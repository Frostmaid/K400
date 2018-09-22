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

import static de.kloen.k400.listener.Karma.*;
import static de.kloen.k400.listener.MessageUtil.*;
import static de.kloen.k400.listener.command.Commands.*;
import static java.lang.String.format;

@Component
public class FoodDrugsMedicine extends ListenerAdapter {

    private K400UserRepository userRepository;

    private KarmaRepository karmaRepository;

    private UserService userService;

    @Autowired
    public FoodDrugsMedicine(K400UserRepository userRepository, KarmaRepository karmaRepository, UserService userService) {
        this.userRepository = userRepository;
        this.karmaRepository = karmaRepository;
        this.userService = userService;
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
            } else if (isExpectedCommand(message, SERUM)) {
                serum(event, author, message.substring(SERUM.length() + 1));
            } else if (isExpectedCommand(message, MENTATS)) {
                mentats(event, author, message.substring(MENTATS.length() + 1));
            } else if (isExpectedCommand(message, BUFFOUT)) {
                buffout(event, author, message.substring(BUFFOUT.length() + 1));
            } else if (isExpectedCommand(message, RADAWAY)) {
                radaway(event, author, message.substring(RADAWAY.length() + 1));
            } else if (isExpectedCommand(message, SUGARBOMBS)) {
                sugarbombs(event, author, message.substring(SUGARBOMBS.length() + 1));
            } else if (isExpectedCommand(message, NUKA)) {
                nuka(event, author, message.substring(NUKA.length() + 1));
            }

        }
    }

    private void nuka(MessageReceivedEvent event, User author, String userName) {
        Optional<K400User> user = userService.findUserForName(event.getJDA(), userName);
        if (isValidTargetUser(author, user)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), NUKA_KARMA);
            event.getChannel()
                    .sendMessage(format("%s reicht %s eine eisgekühlte Nuka-Cola. Wohl bekomms! (%s Karma)",
                            author.getName(), userName, NUKA_KARMA))
                    .queue();
        }
    }

    private void sugarbombs(MessageReceivedEvent event, User author, String userName) {
        Optional<K400User> user = userService.findUserForName(event.getJDA(), userName);
        if (isValidTargetUser(author, user)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), SUGARBOMBS_KARMA);
            event.getChannel()
                    .sendMessage(format("%s überhäuft %s mit knusprig-süßen Zuckerbomben! Yummy! (%s Karma)",
                            author.getName(), userName, SUGARBOMBS_KARMA))
                    .queue();
        }
    }

    private void radaway(MessageReceivedEvent event, User author, String userName) {
        Optional<K400User> user = userService.findUserForName(event.getJDA(), userName);
        if (isValidTargetUser(author, user)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), RADAWAY_KARMA);
            event.getChannel()
                    .sendMessage(format("%s verabreicht %s eine Dosis Radaway. Eine wohlige Wärme breitet sich in %s aus. (%s Karma)",
                            author.getName(), userName, userName, RADAWAY_KARMA))
                    .queue();
        }
    }

    private void buffout(MessageReceivedEvent event, User author, String userName) {
        Optional<K400User> user = userService.findUserForName(event.getJDA(), userName);
        if (isValidTargetUser(author, user)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), BUFFOUT_KARMA);
            event.getChannel()
                    .sendMessage(format("%s versteckt Buffout zwischen %ss Vitaminpillen. %s möchte plötzlich schwere Dinge tragen. (%s Karma)",
                            author.getName(), userName, userName, BUFFOUT_KARMA))
                    .queue();
        }
    }

    private void mentats(MessageReceivedEvent event, User author, String userName) {
        Optional<K400User> user = userService.findUserForName(event.getJDA(), userName);
        if (isValidTargetUser(author, user)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), MENTATS_KARMA);
            event.getChannel()
                    .sendMessage(format("%s mischt %s heimlich Mentats ins Wasser. %s verspürt das unbändige Bedürfnis SUDOKU Rätsel zu lösen. (%s Karma)",
                            author.getName(), userName, userName, MENTATS_KARMA))
                    .queue();
        }
    }

    private void serum(MessageReceivedEvent event, User author, String userName) {
        Optional<K400User> user = userService.findUserForName(event.getJDA(), userName);
        if (isValidTargetUser(author, user)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), SERUM_KARMA);
            event.getChannel()
                    .sendMessage(format("%s verabreicht %s ein heilendes Serum. Es wirkt und %s wird wieder zum Menschen! (%s Karma)",
                            author.getName(), userName, userName, SERUM_KARMA))
                    .queue();
        }
    }

    private void fev(MessageReceivedEvent event, User author, String userName) {
        Optional<K400User> user = userService.findUserForName(event.getJDA(), userName);
        if (isValidTargetUser(author, user)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), FEV_KARMA);
            event.getChannel()
                    .sendMessage(format("%s injiziert %s mit einem hochaggressiven FEV-Stamm. %s mutiert und wird zum Supermutanten! (%s Karma)",
                            author.getName(), userName, userName, FEV_KARMA))
                    .queue();
        }
    }

    private void jet(MessageReceivedEvent event, User author, String userName) {
        Optional<K400User> user = userService.findUserForName(event.getJDA(), userName);
        if (isValidTargetUser(author, user)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), JET_KARMA);
            event.getChannel()
                    .sendMessage(format("%s verführt %s dazu, einen tiefen Zug vom Jet zu nehmen (%s Karma)",
                            author.getName(), userName, JET_KARMA))
                    .queue();
        }
    }

    private void stimPack(MessageReceivedEvent event, User author, String userName) {
        Optional<K400User> k400UserToHeal = userService.findUserForName(event.getJDA(), userName);
        if (isValidTargetUser(author, k400UserToHeal)) {
            karmaRepository.increaseKarmaForUser(userRepository.getOrInit(author), STIM_PACK_KARMA);
            event.getChannel()
                    .sendMessage(format("%s rammt %s ein Stimpak in den Arm. Puh! Das war knapp (%s Karma)",
                            author.getName(), userName, STIM_PACK_KARMA))
                    .queue();
        }
    }

}
