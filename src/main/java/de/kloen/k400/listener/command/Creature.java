package de.kloen.k400.listener.command;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.kloen.k400.listener.Karma.RAT_KARMA;
import static de.kloen.k400.listener.MessageUtil.isCommand;
import static de.kloen.k400.listener.MessageUtil.isExpectedCommand;
import static de.kloen.k400.listener.command.Commands.RAT;
import static java.lang.String.format;

@Component
public class Creature extends ListenerAdapter {
    
    private CommandService commandService;

    @Autowired
    public Creature(CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        String eventMessage = event.getMessage().getContentRaw();

        if (isCommand(eventMessage)) {
            if (isExpectedCommand(eventMessage, RAT.value())) {
                String message = format("Plötzlich springt eine Maulwurfsratte in den Channel! " +
                                "Zum Glück hat %s einen Baseballschläger parat und erledigt das Viech! (%s Karma)",
                        author.getName(), RAT_KARMA);
                commandService.executeCommandWithOutTarget(event, author, RAT_KARMA, message);
            }
        }
    }
}
