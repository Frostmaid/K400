package de.kloen.k400;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import static de.kloen.k400.MessageUtil.getCorrectedCommand;
import static de.kloen.k400.db.Karma.titles;
import static java.util.stream.Collectors.joining;

public class KarmaTitleInformation extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (getCorrectedCommand(event).equals("!karmatitel")) {
            String titlesMessage = titles
                    .entrySet()
                    .stream()
                    .map(title -> title.getKey() + " (" + title.getValue() + " Karma)")
                    .collect(joining("\n"));

            event.getChannel().sendMessage(titlesMessage).queue();
        }
    }

}
