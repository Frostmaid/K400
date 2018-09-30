package de.kloen.k400.listener.command;

import com.google.common.collect.ImmutableSet;

import java.util.Set;
import java.util.stream.Collectors;

import static de.kloen.k400.listener.command.Team.POSSIBLE_TEAMS;

public abstract class Commands {

    public static final Command HELP = new Command("?hilfe", "Gibt die Hilfe aus");

    public static final Command KARMA_TITLE = new Command("!karmatitel", "!karamaTitel: Gibt alle Karamaränge aus.");

    public static final Command STATUS = new Command("!status", "!status: Gibt den aktuellen Karmastatus des Users aus\n" +
            "!status <Username>: Gibt den aktuellen Karmastatus von <Username> aus");

    public static final Command PING = new Command("!ping", "!ping: pong");

    public static final Command STIM_PACK = new Command("!stim", "!stim <Username>: Verabreicht <Username> ein Stimpack.");

    public static final Command JET = new Command("!jet", "!jet <Usernaem>: <Username> nimmt ein Jet.");

    public static final Command FEV = new Command("!fev", "!fev <Username>: <Username> wird mit einem FEV-Stamm infiziert.");

    public static final Command SERUM = new Command("!serum", "!serum <Username>: <Username> bekommt ein heilendes Serum.");

    public static final Command MENTATS = new Command("!mentats", "!mentats <Username>: <Username> nimmt Mentats zu sich.");

    public static final Command BUFFOUT = new Command("!buffout", "!buffout <Username>: <Username> nimmt Buffout zu sich.");

    public static final Command SUGARBOMBS = new Command("!sugarbombs", "!sugarbombs <Username>: <Username> nimmt Sugarbombs zu sich");

    public static final Command NUKA = new Command("!nuka", "!nuka <Username>: <Username> trinkt eine Nuka Cola.");

    public static final Command RADAWAY = new Command("!radaway", "!radaway <Username>: <Username> bekommt Radaway.");

    public static final Command INSTITUT = new Command("!institut", "!institut <Username>");

    public static final Command RECALL = new Command("!recall", "!recall <Username>");

    public static final Command RAT = new Command("!ratte", "!ratte: Eine Ratte erscheint.");

    public static final Command JOIN_TEAM = new Command("!join", "!join <Teamname>: Nutzer tritt dem <Teamname> bei. Liste aller Teams: " + POSSIBLE_TEAMS.stream().collect(Collectors.joining(", ")));

    public static Set<Command> allCommands() {
        return ImmutableSet.<Command>builder()
                .add(HELP)
                .add(KARMA_TITLE)
                .add(STATUS)
                .add(PING)
                .add(STIM_PACK)
                .add(JET)
                .add(FEV)
                .add(SERUM)
                .add(MENTATS)
                .add(BUFFOUT)
                .add(SUGARBOMBS)
                .add(NUKA)
                .add(RADAWAY)
                .add(INSTITUT)
                .add(RECALL)
                .add(RAT)
                .add(JOIN_TEAM)
                .build();
    }
}
