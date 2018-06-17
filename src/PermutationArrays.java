import java.util.Scanner;

public class PermutationArrays {
    private static Scanner input = new Scanner(System.in);
    private static boolean isRunning = true;
    private static boolean isValid = true;
    private static int choice = Integer.MIN_VALUE;


    public static void main(String[] args) {
        while(isRunning) {

            //Avoids printing menu if choice was invalid
            if(isValid)
                printMenu();

            getIntegerInput();

            //Switches between possible cases
            switch(choice) {
                case 1:
                    checkPermutation();
                    break;
                case 2:
                    computeComposition();
                    break;
                case 3:
                    computeInverse();
                    break;
                case 4:
                    computePower();
                    break;
                default:
                    System.out.println("Invalid input.");
                    isValid = false;
                    continue;
            }

            isValid = true;
            promptContinuation();
        }
    }

    private static void printMenu() {
        System.out.println("=====================================================================");
        System.out.println("                                Welcome!\n");
        System.out.println("1. Check if an array is a permutation");
        System.out.println("2. Find the composition of two arrays");
        System.out.println("3. Find the inverse of an array");
        System.out.println("4. Compute the permutation of an array to a power");
        System.out.println("=====================================================================");
    }

    private static void checkPermutation() {

    }

    private static void computeComposition() {

    }

    private static void computeInverse() {

    }

    private static void computePower() {

    }

    private static void getIntegerInput() {
        try {
            choice = Integer.parseInt(input.nextLine());
        } catch(NumberFormatException e) {
            choice = Integer.MAX_VALUE;
        }
    }

    private static void promptContinuation() {
        System.out.println("Would you like to check another array or perform another computation? (Y/N)");
        char continuation = input.nextLine().charAt(0);
        if(continuation != 'y' && continuation != 'Y')
            isRunning = false;
    }
}