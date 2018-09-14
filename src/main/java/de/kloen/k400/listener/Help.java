package de.kloen.k400.listener;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import static de.kloen.k400.listener.MessageUtil.getCorrectedCommand;

public class Help extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (getCorrectedCommand(event).equals("!hilfe")) {
            String message = "!karamaTitel: Gibt alle Karamastufen aus\n" +
                    "!status: Gibt den aktuellen Karmastatus des Users aus\n" +
                    "!status <Username>: Gibt den aktuellen Karmastatus von <Username> aus";

            event.getChannel().sendMessage(message).queue();
        }
    }
}
