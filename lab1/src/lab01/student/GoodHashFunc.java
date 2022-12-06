package lab01.student;

import java.util.Scanner;

/**
 * Compute the hash value of a string provided by the user.
 *
 * $ java GoodHashFunc
 * Please enter a string: Hello
 * The hashcode of the string "Hello" is: 69609650
 *
 * @author William J. Reid (wjr3714)
 */
public class GoodHashFunc {

    /**
     * The main method prompts the user for a string and implements the computeHash() method to compute the
     * hash value of the string provided. It then prints the result to standard output.
     *
     * @param args Command line arguments (a string entered by the user).
     */
    public static void main (String[] args){

        Scanner sc = new Scanner(System.in);

        // Prompt the user to enter a string.
        System.out.println("Please enter a string: ");
        String input_str = sc.next(); //Read only the first word provided by the user and store it as a string.

        sc.close(); // Close scanner.

        // Compute hash value for String
        int result = computeHash(input_str);

        // Print hashcode of the string provided by the user.
        System.out.println("The hashcode of the string \"" + input_str + "\" is: " + result);
    } //end of main

    /**
     * The computeHash method computes the hash value of the string provided by the user.
     *
     * @param userInput Command line arguments (a string entered by the user).
     * @return The hash value of the string entered by the user.
     */
    public static int computeHash(String userInput) {

        int[] hashed_value = new int[userInput.length()]; // Array that will contain computed hash value of each character in the string provided by the user.
        double userInput_length = userInput.length(); // Must convert to type double because "Math.pow" takes in two type double arguments and the String method ",length()" return type is "int".

        for (int i = 0; i < userInput.length(); i++){
            int hash_val = userInput.charAt(i);  // Iterate through characters in the string provided by user.
            int hash_exponent = (int) Math.pow(31, userInput_length - (i+1));
            hashed_value[i] = hash_val*hash_exponent; // Compute hash value of each character in the string and store under a seperate index in array.
        }

        int hashcode_sum = 0; // Hash value of the entire string provided by the user.
        int k = 0; // Counter of while loop.
        while (k < userInput.length()){
            hashcode_sum = hashed_value[k] + hashcode_sum; // Sum the hashcodes of the individual characters of the string.
            k++; // Increment counter by 1.
        }
        return hashcode_sum;
    } //end of computeHash
} //end of GoodHashFunc
