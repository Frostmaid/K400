package de.kloen.k400.listener.command;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.kloen.k400.listener.Karma.*;
import static de.kloen.k400.listener.MessageUtil.isCommand;
import static de.kloen.k400.listener.MessageUtil.isExpectedCommand;
import static de.kloen.k400.listener.command.Commands.*;
import static java.lang.String.format;

@Component
public class FoodDrugsMedicine extends ListenerAdapter {

    private CommandService commandService;

    @Autowired
    public FoodDrugsMedicine(CommandService commandService) {
        this.commandService = commandService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        String eventMessage = event.getMessage().getContentRaw();

        if (isCommand(eventMessage)) {
            if (isExpectedCommand(eventMessage, STIM_PACK)) {
                String userName = eventMessage.substring(STIM_PACK.length() + 1);
                String message = format("%s rammt %s ein Stimpak in den Arm. Puh! Das war knapp (%s Karma)",
                        author.getName(), userName, STIM_PACK_KARMA);

                commandService.executeCommandWithTarget(event, author, userName, STIM_PACK_KARMA, message);
            } else if (isExpectedCommand(eventMessage, JET)) {
                String userName = eventMessage.substring(JET.length() + 1);
                String message = format("%s verführt %s dazu, einen tiefen Zug vom Jet zu nehmen (%s Karma)",
                        author.getName(), userName, JET_KARMA);

                commandService.executeCommandWithTarget(event, author, userName, JET_KARMA, message);
            } else if (isExpectedCommand(eventMessage, FEV)) {
                String userName = eventMessage.substring(FEV.length() + 1);
                String message = format("%s injiziert %s mit einem hochaggressiven FEV-Stamm. %s mutiert und wird zum Supermutanten! (%s Karma)",
                        author.getName(), userName, userName, FEV_KARMA);

                commandService.executeCommandWithTarget(event, author, userName, FEV_KARMA, message);
            } else if (isExpectedCommand(eventMessage, SERUM)) {
                String userName = eventMessage.substring(SERUM.length() + 1);
                String message = format("%s verabreicht %s ein heilendes Serum. Es wirkt und %s wird wieder zum Menschen! (%s Karma)",
                        author.getName(), userName, userName, SERUM_KARMA);

                commandService.executeCommandWithTarget(event, author, userName, SERUM_KARMA, message);
            } else if (isExpectedCommand(eventMessage, MENTATS)) {
                String userName = eventMessage.substring(MENTATS.length() + 1);
                String message = format("%s mischt %s heimlich Mentats ins Wasser. %s verspürt das unbändige Bedürfnis SUDOKU Rätsel zu lösen. (%s Karma)",
                        author.getName(), userName, userName, MENTATS_KARMA);

                commandService.executeCommandWithTarget(event, author, userName, MENTATS_KARMA, message);
            } else if (isExpectedCommand(eventMessage, BUFFOUT)) {
                String userName = eventMessage.substring(BUFFOUT.length() + 1);
                String message = format("%s versteckt Buffout zwischen %ss Vitaminpillen. %s möchte plötzlich schwere Dinge tragen. (%s Karma)",
                        author.getName(), userName, userName, BUFFOUT_KARMA);

                commandService.executeCommandWithTarget(event, author, userName, BUFFOUT_KARMA, message);
            } else if (isExpectedCommand(eventMessage, RADAWAY)) {
                String userName = eventMessage.substring(RADAWAY.length() + 1);
                String message = format("%s verabreicht %s eine Dosis Radaway. Eine wohlige Wärme breitet sich in %s aus. (%s Karma)",
                        author.getName(), userName, userName, RADAWAY_KARMA);

                commandService.executeCommandWithTarget(event, author, userName, RADAWAY_KARMA, message);
            } else if (isExpectedCommand(eventMessage, SUGARBOMBS)) {
                String userName = eventMessage.substring(SUGARBOMBS.length() + 1);
                String message = format("%s überhäuft %s mit knusprig-süßen Zuckerbomben! Yummy! (%s Karma)",
                        author.getName(), userName, SUGARBOMBS_KARMA);

                commandService.executeCommandWithTarget(event, author, userName, SUGARBOMBS_KARMA, message);
            } else if (isExpectedCommand(eventMessage, NUKA)) {
                String userName = eventMessage.substring(NUKA.length() + 1);
                String message = format("%s reicht %s eine eisgekühlte Nuka-Cola. Wohl bekomms! (%s Karma)",
                        author.getName(), userName, NUKA_KARMA);

                commandService.executeCommandWithTarget(event, author, userName, NUKA_KARMA, message);
            }

        }
    }

}
