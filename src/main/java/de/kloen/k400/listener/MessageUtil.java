package de.kloen.k400.listener;

public class MessageUtil {

    private MessageUtil() {
    }

    public static String getCorrectedCommand(String message) {
        String correctedMessage = message.substring(1).toLowerCase().trim();
        return message.charAt(0) + correctedMessage;
    }

    public static boolean isExpectedCommand(String message, String expectedCommand) {
        return getCorrectedCommand(message).substring(0, expectedCommand.length()).equals(expectedCommand);
    }

    public static boolean isCommand(String message) {
        return message.substring(0, 1).equals("!");
    }
}
