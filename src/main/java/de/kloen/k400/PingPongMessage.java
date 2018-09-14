package de.kloen.k400;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import static de.kloen.k400.MessageUtil.getCorrectedCommand;

public class PingPongMessage extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (getCorrectedCommand(event).equals("!ping")) {
            event.getChannel().sendMessage("Pong").queue();
        }
    }
}
