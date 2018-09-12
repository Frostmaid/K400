package de.kloen.k400;

import com.google.common.collect.ImmutableList;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

import static com.vdurmont.emoji.EmojiParser.parseToAliases;

public class CalculateKarma extends ListenerAdapter {

    public static final List<String> positiveReactions = ImmutableList.of(":smile:", "+1", ":v:", "blove");

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        User author = event.getUser();

        if (!author.isBot() && isPositiveReaction(parseToAliases(event.getReactionEmote().getName()))) {
            event.getChannel().sendMessage(author.getName() + " erhält 1 Karma").queue();
        }
    }

    private static boolean isPositiveReaction(String reaction) {
        return positiveReactions.contains(reaction);
    }
}
