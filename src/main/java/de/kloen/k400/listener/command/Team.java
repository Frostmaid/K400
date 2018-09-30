package de.kloen.k400.listener.command;

import com.google.common.collect.ImmutableSet;
import de.kloen.k400.db.K400User;
import de.kloen.k400.db.K400UserRepository;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import static de.kloen.k400.listener.MessageUtil.isCommand;
import static de.kloen.k400.listener.MessageUtil.isExpectedCommand;
import static de.kloen.k400.listener.command.Commands.JOIN_TEAM;

@Component
public class Team extends ListenerAdapter {

    private K400UserRepository userRepository;

    public static final Set<String> POSSIBLE_TEAMS = ImmutableSet.<String>builder()
            .add("Minutemen")
            .add("stählerne Bruderschaft")
            .add("Institut")
            .add("RNK")
            .add("Crimson Carawan")
            .build();

    @Autowired
    public Team(K400UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        String eventMessage = event.getMessage().getContentRaw();

        if (isCommand(eventMessage) && !author.isBot()) {
            if (isExpectedCommand(eventMessage, JOIN_TEAM.value())) {
                K400User user = userRepository.getOrInit(author);
                String teamName = eventMessage.substring(JOIN_TEAM.value().length() + 1);
                if (POSSIBLE_TEAMS.contains(teamName)) {
                    user.joinTeam(teamName);
                    userRepository.save(user);
                }
            }
        }
    }
}
