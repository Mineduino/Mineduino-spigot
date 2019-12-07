package eu.razniewski.mineduino.utils;

public class StandarizedUtils {

    public static String makeEntryKeyFromData(String identifier, String type) {
        return identifier + ";" + type;
    }
}
