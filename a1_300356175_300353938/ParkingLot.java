import java.io.*;
import java.util.Scanner;

/**
 * @author Mehrdad Sabetzadeh, University of Ottawa
 */
public class ParkingLot {
	/**
	 * The delimiter that separates values
	 */
	private static final String SEPARATOR = ",";

	/**
	 * The delimiter that separates the parking lot design section from the parked
	 * car data section
	 */
	private static final String SECTIONER = "###";

	/**
	 * Instance variable for storing the number of rows in a parking lot
	 */
	private int numRows;

	/**
	 * Instance variable for storing the number of spaces per row in a parking lot
	 */
	private int numSpotsPerRow;

	/**
	 * Instance variable (two-dimensional array) for storing the lot design
	 */
	private CarType[][] lotDesign;

	/**
	 * Instance variable (two-dimensional array) for storing occupancy information
	 * for the spots in the lot
	 */
	private Car[][] occupancy;

	/**
	 * Constructs a parking lot by loading a file
	 * 
	 * @param strFilename is the name of the file
	 */
	public ParkingLot(String strFilename) throws Exception {

		if (strFilename == null) {
			System.out.println("File name cannot be null.");
			return;
		}

		// determine numRows and numSpotsPerRow; you can do so by
		// writing your own code or alternatively completing the 
		// private calculateLotDimensions(...) that I have provided
		calculateLotDimensions(strFilename);

		// instantiate the lotDesign and occupancy variables!
       
		lotDesign = new CarType[numRows][numSpotsPerRow];
        occupancy = new Car[numRows][numSpotsPerRow];

		// populate lotDesign and occupancy; you can do so by
		// writing your own code or alternatively completing the 
		// private populateFromFile(...) that I have provided
		populateFromFile(strFilename);
	}

	/**
	 * Parks a car (c) at a give location (i, j) within the parking lot.
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @param c is the car to be parked
	 */
	public void park(int i, int j, Car c) {
        if (isValidSpot(i, j) && canParkAt(i, j, c)) {
            occupancy[i][j] = c;
        }
        return null;
    }
	
	public boolean isValidSpot(int i, int j) {
		// If i is less than numberof rows, as well as j i s less than equal to number of spots per row, then return true. else false
		if ((0<=i<=this.numRows) &&  (0<=j<=this.numSpotsPerRow)) {
		   return true;
		} else {
		   return false;
		}
	}
	/**
	 * Removes the car parked at a given location (i, j) in the parking lot
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the car removed; the method returns null when either i or j are out
	 *         of range, or when there is no car parked at (i, j)
	 */
	public Car remove(int i, int j) {
	       if (isValidSpot(i, j) && occupancy[i][j] != null) {
	            Car removedCar = occupancy[i][j];
	            occupancy[i][j] = null;
	            return removedCar;
	        }
		return null; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD

	}

	/**
	 * Checks whether a car (which has a certain type) is allowed to park at
	 * location (i, j)
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return true if car c can park at (i, j) and false otherwise
	 */
	public boolean canParkAt(int i, int j, Car c) {
		if (isValidSpot(i, j)) {
            return lotDesign[i][j] == c.getType();
            // Check car type, if L then accept (E/S/R,L), if R then accept (E/S/R), if S then accept (E/S), if E then only accept (E), if N then accept none.
		}
		return false; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD
	}

	/**
	 * @return the total capacity of the parking lot excluding spots that cannot be
	 *         used for parking (i.e., excluding spots that point to CarType.NA)
	 */
	public int getTotalCapacity() {
		int capacity = 0;
		// as long as i (row index) is less than number of rows, increase the row index by one, do the same for number of colums to calculate the total capacity of cars
        for (int i = 0; i < numRows; i++) { 
            for (int j = 0; j < numSpotsPerRow; j++) {
                if (lotDesign[i][j] != "N") {
                    capacity++;
                }                
            }
        }
		return capacity; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD

	}

	/**
	 * @return the total occupancy of the parking lot (i.e., the total number of
	 *         cars parked in the lot)
	 */
	public int getTotalOccupancy() {
		// WRITE YOUR CODE HERE!
		// find each spot taken by the car and is occupired by searching each row and column
		int localOccupancy = 0;
		for (int i = 0; i < this.numRows; i++) {
			for (int j = 0; j < this.numSpotsPerRow; j++) {
				if (occupancy[i][j] != "null") {
					localOccupancy++;
				}
			}
		}
		return localOccupancy; // REMOVE THIS STATEMENT AFTER IMPLEMENTING THIS METHOD		
	}

	private void calculateLotDimensions(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));

	    // Initialize numRows and numSpotsPerRow to 0
	    this.numRows = 0;
	    this.numSpotsPerRow = 0;
		
	    //loops through the file to count rows and find the maximum spots per row
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			if (!str.equals(SECTIONER)== false) {
				this.numRows++ ;
			}
			else {
			   break;
			}
			if (numRows == 1) {
			   this.numSpotsPerRow = str.split(SEPARATOR).length;
				
			}
			//System.out.print(str);
			// WRITE YOUR CODE HERE!
			
		}

		scanner.close();
	}

	private void populateFromFile(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));

		// YOU MAY NEED TO DEFINE SOME LOCAL VARIABLES HERE!
		int occupancyRows = 0;
		boolean startCount = false;
		// while loop for reading the lot design
		 
			String str = scanner.nextLine();
			if (!str.equals(SECTIONER)) {
				String[] tokens = str.split(SEPARATOR);
				for (int j = 0; j < numSpotsPerRow; j++) {
                    lotDesign[occupancyRows][j] = CarType.valueOf(tokens[j]);
				}
				occupancyRows++;
			} else {
                startCount = true;
			}
				   
			}
			if (startCount == true && str.length > 0) {
				occupancyRows++;
			}
		}

		scanner.close();
	}

	/**
	 * Produce string representation of the parking lot
	 * 
	 * @return String containing the parking lot information
	 */
	public String toString() {
		// NOTE: The implementation of this method is complete. You do NOT need to
		// change it for the assignment.
		StringBuffer buffer = new StringBuffer();
		buffer.append("==== Lot Design ====").append(System.lineSeparator());

		for (int i = 0; i < lotDesign.length; i++) {
			for (int j = 0; j < lotDesign[0].length; j++) {
				buffer.append((lotDesign[i][j] != null) ? Util.getLabelByCarType(lotDesign[i][j])
						: Util.getLabelByCarType(CarType.NA));
				if (j < numSpotsPerRow - 1) {
					buffer.append(", ");
				}
			}
			buffer.append(System.lineSeparator());
		}

		buffer.append(System.lineSeparator()).append("==== Parking Occupancy ====").append(System.lineSeparator());

		for (int i = 0; i < occupancy.length; i++) {
			for (int j = 0; j < occupancy[0].length; j++) {
				buffer.append(
						"(" + i + ", " + j + "): " + ((occupancy[i][j] != null) ? occupancy[i][j] : "Unoccupied"));
				buffer.append(System.lineSeparator());
			}

		}
		return buffer.toString();
	}

	/**
	 * <b>main</b> of the application. The method first reads from the standard
	 * input the name of the file to process. Next, it creates an instance of
	 * ParkingLot. Finally, it prints to the standard output information about the
	 * instance of the ParkingLot just created.
	 * 
	 * @param args command lines parameters (not used in the body of the method)
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {

		StudentInfo.display();

		System.out.print("Please enter the name of the file to process: ");

		Scanner scanner = new Scanner(System.in);

		String strFilename = scanner.nextLine();

		ParkingLot lot = new ParkingLot(strFilename);

		System.out.println("Total number of parkable spots (capacity): " + lot.getTotalCapacity());

		System.out.println("Number of cars currently parked in the lot: " + lot.getTotalOccupancy());

		System.out.print(lot);

	}
}