package de.kloen.k400;

import de.kloen.k400.listener.Reactions;
import de.kloen.k400.listener.command.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class K400 implements CommandLineRunner {

    private Reactions reactions;

    private UserStatus userStatus;

    private FoodDrugsMedicine foodDrugsMedicine;

    private Synth synth;

    private Creature creature;

    private Team team;

    @Autowired
    public K400(Reactions reactions, UserStatus userStatus, FoodDrugsMedicine foodDrugsMedicine, Synth synth, Creature creature, Team team) {
        this.reactions = reactions;
        this.userStatus = userStatus;
        this.foodDrugsMedicine = foodDrugsMedicine;
        this.synth = synth;
        this.creature = creature;
        this.team = team;
    }

    public static void main(String[] args) {
        SpringApplication.run(K400.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String token = K400Arguments.readToken(args);
        new JDABuilder(AccountType.BOT)
                .setToken(token)
                .addEventListener(new PingPongMessage())
                .addEventListener(new Help())
                .addEventListener(reactions)
                .addEventListener(userStatus)
                .addEventListener(foodDrugsMedicine)
                .addEventListener(synth)
                .addEventListener(creature)
                .addEventListener(team)
                .buildBlocking();
    }
}
