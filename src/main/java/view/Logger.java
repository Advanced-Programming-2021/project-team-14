package view;

public class Logger {

    public static final String RESET = "\033[0m";  // Text Reset
    public static final String WHITE = "\033[0;37m";   // WHITE

    public static void log(String from, String output) {
        System.out.println(Logger.WHITE + from + " : " + output + Logger.RESET);
    }
}
