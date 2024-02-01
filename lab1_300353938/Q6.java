package lab1_300353938;

import java.util.Arrays;
import java.util.Scanner;

public class Q6 {    
        public static void main(String[] args){
            Scanner scanner = new Scanner(System.in);
            double[] grades = new double[10];
    
            for (int i = 0; i < 10; i++) {
                System.out.println("Enter grade for student " + (i + 1) + ":");
                grades[i] = scanner.nextDouble();
            }
    
            System.out.println("Average grade: " + calculateAverage(grades));
            System.out.println("Median grade: " + calculateMedian(grades));
            System.out.println("Number of students failed: " + calculateNumberFailed(grades));
            System.out.println("Number of students passed: " + calculateNumberPassed(grades));
            scanner.close();
        }
    
        public static double calculateAverage(double[] notes){
            double sum = 0;
            for (double note : notes) {
                sum += note;
            }
            return sum / notes.length;
        }
    
        public static double calculateMedian(double[] notes){
            Arrays.sort(notes);
            int middle = notes.length / 2;
            if (notes.length % 2 == 1) {
                return notes[middle];
            } else {
                return (notes[middle - 1] + notes[middle]) / 2.0;
            }
        }
    
        public static int calculateNumberFailed(double[] notes){
            int count = 0;
            for (double note : notes) {
                if (note < 50.0) {
                    count++;
                }
            }
            return count;
        }
    
        public static int calculateNumberPassed(double[] notes){
            int count = 0;
            for (double note : notes) {
                if (note >= 50.0) {
                    count++;
                }
            }
            return count;
        }
}
    
