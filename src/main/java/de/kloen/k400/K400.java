package de.kloen.k400;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class K400 {

    private CalculateKarma calculateKarma;

    @Autowired
    public K400(CalculateKarma calculateKarma) {
        this.calculateKarma = calculateKarma;
    }

    public void main(String[] args) throws Exception {
        String token = K400Arguments.readToken(args);

        JDA jda = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .addEventListener(new PingPongMessage())
                .addEventListener(calculateKarma)
                .buildBlocking();

    }
}
