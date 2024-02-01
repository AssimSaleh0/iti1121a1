package lab1_300353938;

public class Q3_ArrayInsertionDemo {

    public static int[] insertIntoArray(int[] beforeArray, int indexToInsert, int valueToInsert){
		int[] afterArray = new int[(beforeArray.length +1)];
        for (int i = 0; i < afterArray.length; i++) {
            if (i < indexToInsert) {
                afterArray[i] = beforeArray[i];
            } else if (i == indexToInsert) {
                afterArray[i] = valueToInsert;
            } else {
                afterArray[i] = beforeArray[i-1];
            }
        }
        return afterArray;
	}

    public static String toString(int[] array) {
        String arrayS = "";
        for (int i = 0; i < array.length; i++) {
           arrayS += array[i] + "\n";
            }
            return arrayS;
    }
    public static void main(String[] args) {
        int[] beforeArray = {1,5,4,7,9,6};
        int indexToInsert = 3;
        int valueToInsert = 15;

        String beforeArrayS = toString(beforeArray);

        int[] afterArray = insertIntoArray(beforeArray, indexToInsert, valueToInsert);

        String afterArrayS = toString(afterArray);

        System.out.println("Array before Insertion: \n" + beforeArrayS + "Array after insertion of " + valueToInsert + " at position of " + indexToInsert + ": \n" + afterArrayS);
    }

}
