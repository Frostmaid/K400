package de.kloen.k400;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PingPongMessage extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.print("Message from:" + event.getAuthor() + ", with text: " + event.getMessage().getContentRaw());

        if (event.getMessage().getContentRaw().equals("!ping")) {
            System.out.print("send Message");
            event.getChannel().sendMessage("Pong").queue();
        }

    }
}
