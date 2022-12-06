package lab01.student;

// import java.util.Arrays;
import java.util.Scanner;

/**
 * The class SieveOfEratosthenes is an implementation of the Sieve of Eratosthenes algorithm.
 *
 * $ java SieveOfEratosthenes
 * Please enter an upper bound (0 to quit): 2323
 * Please enter a positive number (0 to quit): 2000
 * 2000 is not prime.
 *
 * @author William J. Reid (wjr3714)
 */
public class SieveOfEratosthenes {

    /**
     * The main() method prompts the user to enter an integer for the upper bound, then asks the user to
     * enter a number as an integer greater than 0 but less than or equal to the upper bound, and then prints a
     * message indicating whether or not the number is prime.
     *
     * @param args Command line arguments (an integer entered by the user).
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter an upper bound (0 to quit): ");
        int input_upperBound = sc.nextInt();

        boolean start = true; // Used to enter while loop.

        // If user enters "0" for the upper bound, end the program.
        if (input_upperBound == 0){
            start = false; // This will prevent the while loop from executing.
            System.out.println("Goodbye!");
        }

            while (start) {

                System.out.println("Please enter a positive number (0 to quit): ");
                int positive_number = sc.nextInt();

                if (input_upperBound == 0 || positive_number == 0) {
                    System.out.println("Goodbye!");
                    start = false; // Terminates while loop.
                }

                // If user enters number outside of upper bound range, allow  user to correct input.
                else if (positive_number > input_upperBound) {
                    System.out.println(positive_number + " is out of range ( " + positive_number + " > " + input_upperBound + " ).");
                    System.out.println("Want to change the initial upper bound ( Y / N ) or [0 to quit]: ");
                    String response = sc.next();

                    // If user wants to change the upper bound.
                    if ("Y".equals(response.toUpperCase())) {
                        System.out.println("Please enter an upper bound (0 to quit): ");
                        input_upperBound = sc.nextInt();
                    }

                    // If user does NOT want to change the upper bound.
                    else if ("N".equals(response.toUpperCase())) {
                        System.out.println("Please enter a positive number less than or equal to " + input_upperBound + " (0 to quit): ");
                        positive_number = sc.nextInt();
                        int[] result = makeSieve(input_upperBound);

                        if (result[positive_number] == 1) { //
                            System.out.println(positive_number + " is not prime.");
                            // System.out.println(Arrays.toString(result)); Used for troubleshooting.
                        }
                        else {
                            System.out.println(positive_number + " is prime.");
                            // System.out.println(Arrays.toString(result)); Used for troubleshooting.
                        }
                    }

                    else { // Break if the user enters anything other than these strings: "Y, y, N, n".
                        System.out.println("Goodbye!");
                        start = false; // This will terminate the while loop.
                    }

                }

                // If user enters a number within the range of the upper bound, use the Sieve of Eratosthenes to
                // determine the number's primality.
                else {
                    int[] result = makeSieve(input_upperBound);

                    if (result[positive_number] == 1) { //
                        System.out.println(positive_number + " is not prime.");
                        // System.out.println(Arrays.toString(result)); Used for troubleshooting.
                    } else {
                        System.out.println(positive_number + " is prime.");
                        // System.out.println(Arrays.toString(result)); Used for troubleshooting.
                    }
                }

            }
            sc.close(); // Close scanner.

    } // end of method: main()


    /**
     * Creates a new Sieve of Eratosthenes for every number between 0 and the specified upper bound (inclusive).
     *
     * @param upperBound The largest number for which the sieve can be used to determine primality.
     * @return The Sieve of Eratosthenes array with range 0 and the specified upper bound (inclusive). The value at each index i of the array is 0 if the number is prime and 1 otherwise.
     */
    public static int[] makeSieve(int upperBound) { //returns and integer array.
        int[] sieveArray = new int[upperBound + 1]; // The +1 allows us to include the number "0" (lower bound) as
        // part of the array

        // Hardcode the 1's (not prime) for the numbers zero and one.
        sieveArray[0] = 1; // Zero is not considered prime.
        sieveArray[1] = 1; // One is not considered prime.
         // Since all values in a newly create integer array, such as in "sieveArray[]", are assigned a value of zero,
        // and we know that the number 2 & 3 are both primes, we should start updating the sieveArray[] starting at
        // index 4 "sieveArray[4]", which represents the number 4.

        for (int i = 2; i <= (upperBound ); i++) {
            for (int j = 2; j <= (upperBound); j++) {
                if ((i*j) <= (upperBound)){
                    sieveArray[(i*j)] = 1; // The nested for loop begins updating sieveArray at index sieveArray[4],
                    // which represents the number 4.
                } // Since the numbers 2 and 3 are both prime, there is no need to update any of these values in the
                // sieveArray[] as these are already 0's.

            }
        }
        return sieveArray;
    } // end of method: makeSieve()
} // end of class: SieveOfEratosthenes
