package org.example.utility;

import java.util.Scanner;

public class InputUtils { //scanner, read string, read int
    private final static Scanner scanner = new Scanner(System.in);

    public static String readString(){
        return scanner.nextLine();
    }

    public static int readInt(){
        while (true){
            try{
                int input = scanner.nextInt();
                scanner.nextLine();
                return input;

            } catch (RuntimeException e) {
                System.out.println("Invalid input, try again");
                scanner.nextLine();
            }
        }

    }

}
