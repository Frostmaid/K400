package de.kloen.k400.listener.command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import static de.kloen.k400.listener.MessageUtil.isCommand;
import static de.kloen.k400.listener.MessageUtil.isExpectedCommand;
import static de.kloen.k400.listener.command.Commands.PING;

public class PingPongMessage extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (isCommand(message)) {
            if (isExpectedCommand(message, PING.value())) {
                event.getChannel().sendMessage("Pong").queue();
            }
        }
    }
}
