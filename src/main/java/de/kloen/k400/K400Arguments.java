package de.kloen.k400;

import org.apache.commons.cli.*;

public class K400Arguments {

    public static final String TOKEN_OPTION = "t";

    public static String readToken(String[] args) throws ParseException {
        Options options = new Options().addOption(TOKEN_OPTION, true, "set token");

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine line = parser.parse(options, args);
            return line.getOptionValue(TOKEN_OPTION);
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("K400", options);
            throw e;
        }
    }
}
