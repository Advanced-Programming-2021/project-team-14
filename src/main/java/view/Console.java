package view;

import java.util.Scanner;

public abstract class Console {
    private static final Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static String scan(){
        return scanner.nextLine();
    }
    public static void print(String output){
        System.out.println(output);
    }
}
