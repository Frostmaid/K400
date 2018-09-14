package de.kloen.k400;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class K400 implements CommandLineRunner {

    private CalculateKarma calculateKarma;
    private KarmaUserStatus karmaUserStatus;

    @Autowired
    public K400(CalculateKarma calculateKarma, KarmaUserStatus karmaUserStatus) {
        this.calculateKarma = calculateKarma;
        this.karmaUserStatus = karmaUserStatus;
    }

    public static void main(String[] args) {
        SpringApplication.run(K400.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String token = K400Arguments.readToken(args);
        new JDABuilder(AccountType.BOT)
                .setToken(token)
                .addEventListener(new KarmaTitleInformation())
                .addEventListener(new PingPongMessage())
                .addEventListener(calculateKarma)
                .addEventListener(karmaUserStatus)
                .buildBlocking();
    }
}
