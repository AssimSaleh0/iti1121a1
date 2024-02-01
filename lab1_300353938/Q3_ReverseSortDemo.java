package lab1_300353938;

public class Q3_ReverseSortDemo {
    public static void main(String[] args) {
        char[] unorderedLetters;
		unorderedLetters = new char[]{'b', 'm', 'z', 'a', 'u'};
		unorderedLetters = reverseSort(unorderedLetters);
		for (int i = 0 ; i < unorderedLetters.length; i++ )
			System.out.print(unorderedLetters[i]);
	}

	//method that sorts a char array into its reverse alphabetical order
	public static char[] reverseSort(char[] values){
        char[] valuesS = new char[values.length];

        for (int i = 0; i<values.length; i++) {
            valuesS[i] = values[values.length - 1 - i];
        }

		return valuesS;
	}
   
}

