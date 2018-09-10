package de.kloen.k400;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class K400 {

    public static void main(String[] args) {
        try {
            JDA jda = new JDABuilder(AccountType.BOT)
                    .setToken("NDg4Nzk5OTIwNDM5MDMzODc5.DnheRg.6Jtin0b50R0UKHW6wX6YMC4crW0")
                    .addEventListener(new PingPongMessage())
                    .buildBlocking();

        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
