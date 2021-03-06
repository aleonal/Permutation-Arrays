import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
                    System.out.println("\n== Check if an array is a permutation ==");
                    if(checkPermutation(arrayFromFile()))
                        System.out.println("\n== The array is a permutation ==");
                    else System.out.println("\n== The array is not a permutation ==");
                    break;
                case 2:
                    System.out.println("\n== Compute the composition of two arrays ==");
                    computeCompositionHelper();
                    break;
                case 3:
                    System.out.println("\n== Compute the inverse of an array ==");

                    //Array is retrieved here in order to generalize inverse algorithm
                    int[] array;
                    while(true) {
                        array = arrayFromFile();
                        if (!checkPermutation(array)) {
                            System.out.println("Specified array is not a permutation. Try another array.");
                            continue;
                        }
                        break;
                    }
                    array = computeInverse(array);

                    System.out.print("== The inverse array is: [");
                    for(int i = 0; i < array.length; i++) {
                        System.out.print(array[i]);
                        if (i != array.length - 1)
                            System.out.print(" ");
                    }
                    System.out.println("] ==");

                    break;
                case 4:
                    System.out.println("\n== Compute the composition of an array to itself, n times ==");
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
        System.out.println("4. Compute the permutation of an array to itself, n times");
        System.out.println("=====================================================================");
    }

    private static boolean checkPermutation(int[] array) {
        Set<Integer> set = new HashSet<>();

        //If the array contains duplicate integers, it is not a permutation
        for(int element: array) {
            if(set.contains(element))
                return false;

            set.add(element);
        }

        return true;
    }

    private static void computeCompositionHelper() {
        int[] A;
        int[] B;
        int[] composition;

        System.out.println("-- Array A --");
        while(true) {
            A = arrayFromFile();
            if (!checkPermutation(A)) {
                System.out.println("Specified array is not a permutation. Try another array.");
                continue;
            }
            break;
        }

        System.out.println("-- Array B --");
        while(true) {
            B = arrayFromFile();
            if (!checkPermutation(B)) {
                System.out.println("Specified array is not a permutation. Try another array.");
                continue;
            }
            break;
        }

        if(A.length != B.length) {
            System.out.println("Array lengths do not match. The composition cannot be computed.");
        } else {
            composition = computeComposition(A, B);

            System.out.print("== The composition array is: [");
            for(int i = 0; i < composition.length; i++) {
                System.out.print(composition[i]);
                if(i != composition.length - 1)
                    System.out.print(" ");
            }
        }
    }

    private static int[] computeComposition(int[] A, int[] B) {
        int[] composition = new int[A.length];

        for(int i = 0; i < composition.length; i++)
            composition[i] = A[B[i]];

        return composition;
    }

    private static int[] computeInverse(int[] array) {
        int[] inverse = new int[array.length];

        for(int i = 0; i < array.length; i++) {
            inverse[i] = array[array[i]];
        }

        return inverse;
    }

    private static void computePower() {
        int[] A;
        int[] B;

        while(true) {
            A = arrayFromFile();
            if (!checkPermutation(A)) {
                System.out.println("Specified array is not a permutation. Try another array.");
                continue;
            }
            break;
        }

        while(true) {
            System.out.println("To what power would you like to compute the composition? (integer): ");
            if(choice == Integer.MAX_VALUE) {
                System.out.println("Input is not an integer.");
                continue;
            }
            break;
        }

        //Determines whether power is negative or positive; computes respective compositions
        if(choice < 0) {
            A = computeInverse(A);
            B = new int[A.length];

            for(int i = 0; i < A.length; i++)
                B[i] = A[i];

            for(int i = -1; i > choice; i--)
                B = computeComposition(A, B);
        } else {
            B = new int[A.length];

            for(int i = 0; i < A.length; i++)
                B[i] = A[i];

            for(int i = 1; i < choice; i++)
                B = computeComposition(A, B);
        }

        System.out.print("== The composition of the array to itself " + choice + "times is: [");
        for(int i = 0; i < B.length; i++) {
            System.out.print(B[i]);
            if (i != B.length - 1)
                System.out.print(" ");
        }
        System.out.println("] ==");
    }

    private static void getIntegerInput() {
        try {
            choice = Integer.parseInt(input.nextLine());
        } catch(NumberFormatException e) {
            choice = Integer.MAX_VALUE;
        }
    }

    private static void promptContinuation() {
        System.out.println("\nWould you like to check another array or perform another computation? (Y/N)");
        char continuation = input.nextLine().charAt(0);
        if(continuation != 'y' && continuation != 'Y')
            isRunning = false;
        else System.out.println();
    }

    private static int[] arrayFromFile() {
        int[] array;

        while(true) {
            System.out.print("Enter filename (root is project folder): ");
            try {
                File file = new File(input.nextLine());
                Scanner data = new Scanner(file);
                int counter = 0;

                //Reads first line as array length
                array = new int[Integer.parseInt(data.nextLine())];

                //Reads integers from second line into array
                while(data.hasNextInt()) {
                    array[counter] = data.nextInt();
                    counter++;
                }

                //Throws exception if integers < array length
                if(counter != array.length)
                    throw new ArrayIndexOutOfBoundsException();

                //Throws exception if file contains extra data
                if(data.hasNextLine())
                    throw new NumberFormatException();

                break;
            } catch(FileNotFoundException f) {
                System.out.println("File not found...");
            } catch(NumberFormatException a) {
                System.out.println("File is corrupt... please try another file.");
            } catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Array length does not equal integer amount... please try another file.");
            }
        }

        return array;
    }
}