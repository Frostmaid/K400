package de.kloen.k400.listener.command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Set;
import java.util.stream.Collectors;

import static de.kloen.k400.listener.MessageUtil.isExpectedCommand;
import static de.kloen.k400.listener.command.Commands.*;

public class Help extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (message.substring(0, 1).equals("?")) {
            if (isExpectedCommand(message, HELP.value())) {
                Set<Command> commands = allCommands();
                String help = commands
                        .stream()
                        .filter(c -> !c.equals(HELP))
                        .filter(c -> !c.equals(PING))
                        .map(Command::description)
                        .collect(Collectors.joining("\n"));

                event.getChannel().sendMessage(help).queue();
            }
        }
    }

}
