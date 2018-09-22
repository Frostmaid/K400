package de.kloen.k400.listener.command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import static de.kloen.k400.listener.MessageUtil.isCommand;
import static de.kloen.k400.listener.MessageUtil.isExpectedCommand;
import static de.kloen.k400.listener.command.Commands.HELP;

public class Help extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if(isCommand(message)) {
            if (isExpectedCommand(message, HELP)) {
                String help = "!karamaTitel: Gibt alle Karamastufen aus\n" +
                        "!status: Gibt den aktuellen Karmastatus des Users aus\n" +
                        "!status <Username>: Gibt den aktuellen Karmastatus von <Username> aus";

                event.getChannel().sendMessage(help).queue();
            }
        }
    }
}
