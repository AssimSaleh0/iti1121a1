package lab1_300353938;


public class Q3_SquareArray {

    public static int[] createArray(int size) {
        int[] ar = new int[size + 1];
        for (int i = 0; i< (size+ 1); i++) {
            ar[i] = i*i;
        }
        return ar;
    }
    public static void main(String[] args) {
       int[] ar = createArray(12);
       for (int i = 0; i < ar.length; i++) {
        System.out.println("The square of " + i + " is: " + ar[i]);
       }
    }

}