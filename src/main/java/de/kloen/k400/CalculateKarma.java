package de.kloen.k400;

import com.google.common.collect.ImmutableList;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class CalculateKarma extends ListenerAdapter {

    public static final List<String> positiveReactions = ImmutableList.of("\uD83D\uDE04", "\uD83D\uDE03", "✌", "\uD83D\uDC4D", "\uD83D\uDC4F");

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        User author = event.getUser();
        System.out.println("User: " + author + "reacted with: " + event.getReactionEmote().getName());

        if (!author.isBot() && isPositiveReaction(event.getReactionEmote().getName())) {
            event.getChannel().sendMessage(author.getName() + " erhält 1 Karma").queue();
        }
    }

    private static boolean isPositiveReaction(String reaction) {
        return positiveReactions.contains(reaction);
    }
}
