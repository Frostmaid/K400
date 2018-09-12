package de.kloen.k400;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class K400 {

    public static void main(String[] args) throws Exception {
        try {
            String token = K400Arguments.readToken(args);
            JDA jda = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .addEventListener(new PingPongMessage())
                    .addEventListener(new CalculateKarma())
                    .buildBlocking();

        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
