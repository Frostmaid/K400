package de.kloen.k400.listener;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class MessageUtil {

    private MessageUtil() {
    }

    public static String getCorrectedCommand(MessageReceivedEvent event) {
        String contentRaw = event.getMessage().getContentRaw();
        String correctedMessage = contentRaw.substring(1).toLowerCase().trim();
        return contentRaw.charAt(0) + correctedMessage;
    }
}
