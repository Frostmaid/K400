package de.kloen.k400.listener.command;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.kloen.k400.listener.Karma.INSTITUT_KARMA;
import static de.kloen.k400.listener.Karma.RECALL_KARMA;
import static de.kloen.k400.listener.MessageUtil.isCommand;
import static de.kloen.k400.listener.MessageUtil.isExpectedCommand;
import static de.kloen.k400.listener.command.Commands.INSTITUT;
import static de.kloen.k400.listener.command.Commands.RECALL;
import static java.lang.String.format;

@Component
public class Synth extends ListenerAdapter {

    private CommandService commandService;

    @Autowired
    public Synth(CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        String eventMessage = event.getMessage().getContentRaw();

        if (isCommand(eventMessage)) {
            if (isExpectedCommand(eventMessage, INSTITUT)) {
                String userName = eventMessage.substring(INSTITUT.length() + 1);
                String message = format("Dank eines Tipps von %s konnte das Institut %s finden und gegen einen Synth austauschen (%s Karma)",
                        author.getName(), userName, INSTITUT_KARMA);

                commandService.executeCommandWithTarget(event, author, userName, INSTITUT_KARMA, message);
            } else if (isExpectedCommand(eventMessage, RECALL)) {
                String userName = eventMessage.substring(RECALL.length() + 1);
                String message = format("%s liest einen Recall-Code vor. Plötzlich erinnert sich %s an die Flucht vorm Institut. %s ist ein geflüchteter Synth! (%s Karma)",
                        author.getName(), userName, userName, RECALL_KARMA);

                commandService.executeCommandWithTarget(event, author, userName, RECALL_KARMA, message);
            }
        }
    }

}
