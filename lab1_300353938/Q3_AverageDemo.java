package lab1_300353938;

public class Q3_AverageDemo {
    public static void main(String[] args) {
        double[] valuesArray;
		valuesArray = new double[]{100.0,34.0,72.0,56.0,82.0,67.0,94.0};
		System.out.println("The average is: " + calculateAverage(valuesArray));
    }

    public static double calculateAverage(double[] values){
		double result = 0;
        for (int i = 0; i < values.length; i++) {
            result += values[i];
        }
        result /= values.length;
        return result;
	}
}
