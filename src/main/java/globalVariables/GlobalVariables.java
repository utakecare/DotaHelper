package globalVariables;

import java.util.HashMap;

public class GlobalVariables {
    public static HashMap<String, String> dotaBuffLinkMap = new HashMap<>();
    public static HashMap<String, String> playersNameMap = new HashMap<>();
    public static boolean accepterStatus = true;
    public static boolean flagAccepterButtons = true;
    public static boolean flagDotaBuffButtons = true;
    public static int countCores;
    public static int totalPlayers = 10;
    public static int red = 0;
    public static int green = 1;
    public static int blue = 2;
    public static int range = 10;
    public static String pathToUserSettings = "SOFTWARE\\AccepterDota";
    public static String pathToSteamVariable = "steamLogPath";
    public static String pathToTelegramChatVariable = "chatId";
}
