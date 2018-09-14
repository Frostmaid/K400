package de.kloen.k400;

import com.google.common.collect.ImmutableList;
import de.kloen.k400.db.K400UserRepository;
import de.kloen.k400.db.KarmaRepository;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vdurmont.emoji.EmojiParser.parseToAliases;

@Service
public class CalculateKarma extends ListenerAdapter {

    public static final int POSITIVE_KARMA_VALUE = 1;

    public static final int NEGATIVE_KARMA_VALUE = -1;

    public static final List<String> positiveReactions = ImmutableList.of(":smile:", "+1", ":v:", "blove");

    private KarmaRepository karmaRepository;

    private K400UserRepository k400UserRepository;

    @Autowired
    public CalculateKarma(KarmaRepository karmaRepository, K400UserRepository k400UserRepository) {
        this.karmaRepository = karmaRepository;
        this.k400UserRepository = k400UserRepository;
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        User author = event.getUser();

        if (!author.isBot() && isPositiveReaction(parseToAliases(event.getReactionEmote().getName()))) {
            karmaRepository.increaseKarmaForUser(k400UserRepository.getOrInit(author), POSITIVE_KARMA_VALUE);

            event.getChannel().sendMessage(author.getName() + " erhält " + POSITIVE_KARMA_VALUE + " Karma").queue();
        }
    }

    private static boolean isPositiveReaction(String reaction) {
        return positiveReactions.contains(reaction);
    }

}
