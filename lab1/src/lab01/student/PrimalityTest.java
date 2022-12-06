package lab01.student;

import java.util.Scanner;

/**
 * Determines whether an integer provided by the user is prime or not. A prime number is as an integer greater than or
 * equal to 2 and is divisible only by 1 and itself.
 *
 * $ java PrimalityTest
 * Enter an number (0 to quit): 5
 * 5 is prime.
 *
 * @author William J. Reid (wjr3714)
 */
public class PrimalityTest {


    /**
     * The main() method prompts the user to enter an integer and prints a message indicating whether or not the
     * number is prime.
     *
     * @param args Command line arguments (an integer entered by the user).
     */
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        boolean start = true; // Used to enter while loop.

        while (start) {
            System.out.println("Enter a number (0 to quit): ");
            int input_integer = sc.nextInt();
            if (input_integer < 1){
                System.out.println("Goodbye!");
                start = false; // Terminates while loop.
            }
            else{
                boolean result = isPrime(input_integer);
                if (result){ // Same as "result == true"
                    System.out.println(input_integer + " is prime!");
                }
                else{
                    System.out.println(input_integer + " is not prime.");
                }
            }

        }
        sc.close(); // Close scanner.

    }

    /**
     * The isPrime method computes whether the integer provided by the user is prime. This method returns true if the
     * integer is prime and false otherwise.
     *
     * @param userInput The number provided by the user as a command line argument to be tested for primality.
     * @return This method returns true if the integer entered by the user is prime and false otherwise.
     */
    public static boolean isPrime(int userInput){
        boolean prime = false;
        if (userInput == 1 ){
            prime = false;
        }
        else if (userInput == 2 || userInput == 3){
            prime = true;
        }
        else{
            int half_number = userInput /2;
            int i = 1;
            while (i < (half_number+1)){
                i++;
                if (userInput % i == 0){
                    prime = false;
                    i = half_number+1; // Break out of while loop.
                }
                else if (i < half_number){
                    continue; // Skips the else statement unless i > half_number if (userInput % i != 0)
                }
                else{
                    prime = true;
                }
            }
        }
        return prime;
    }

}
