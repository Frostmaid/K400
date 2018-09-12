package de.kloen.k400;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PingPongMessage extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String contentRaw = event.getMessage().getContentRaw();
        String correctedPing = contentRaw.substring(1).toLowerCase().trim();

        if ((contentRaw.charAt(0) + correctedPing).equals("!ping")) {
            event.getChannel().sendMessage("Pong").queue();
        }
    }
}
