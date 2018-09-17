package client;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        while (true){
            operationPrompt();
            System.out.println();
        }

    }
    private static void operationPrompt(){
        Scanner in = new Scanner(System.in);
        System.out.println("Choose an operation:");
        System.out.println("1\ttoLowerCase()");
        System.out.println("2\ttrim()");
        System.out.println("3\tparseDouble()");
        System.out.println();
        int operation = in.nextInt();
        in.nextLine();
        System.out.println("Type your input string:");
        String input = in.nextLine();
        System.out.println("Answer:");
        switch(operation){
            case 1:
                System.out.println(StringProcessorProxy.getInstance().toLowerCase(input));
                break;
            case 2:
                System.out.println(StringProcessorProxy.getInstance().trim(input));
                break;
            case 3:
                System.out.println(StringProcessorProxy.getInstance().parseDouble(input));
                break;
            default:
                break;
        }

    }
}
