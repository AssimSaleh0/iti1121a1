import java.io.File;
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
	private Spot[][] occupancy;

	/**
	 * Constructs a parking lot by loading a file
	 * 
	 * @param strFilename is the name of the file
	 */
	public ParkingLot(String strFilename) throws Exception {
		calculateLotDimensions(strFilename); // Calculate dimensions based on the file
        lotDesign = new CarType[numRows][numSpotsPerRow];
        occupancy = new Spot[numRows][numSpotsPerRow]; // Initialize occupancy with Spot objects
        populateDesignFromFile(strFilename); // Populate the design from the file
		// WRITE YOUR CODE HERE!
		
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumSpotsPerRow() {
		return numSpotsPerRow;
	}

	/**
	 * Parks a car (c) at a give location (i, j) within the parking lot.
	 * 
	 * @param i         is the parking row index
	 * @param j         is the index of the spot within row i
	 * @param c         is the car to be parked
	 * @param timestamp is the (simulated) time when the car gets parked in the lot
	 */
	public void park(int i, int j, Car c, int timestamp) {
		if (canParkAt(i, j, c)) {
            occupancy[i][j] = new Spot(c, timestamp);
        }
		// WRITE YOUR CODE HERE!		

	}

	/**
	 * Removes the car parked at a given location (i, j) in the parking lot
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the spot removed; the method returns null when either i or j are out
	 *         of range, or when there is no car parked at (i, j)
	 */
	public Spot remove(int i, int j) {
		if(i >= 0 && i < numRows && j >= 0 && j < numSpotsPerRow && occupancy[i][j] != null){
			// Retrieve the Spot object at the given indices.
			Spot spot = occupancy[i][j];
			// Clear the occupancy 
			occupancy[i][j] = null;
			// return object called spot
			return spot;
		}	
		// we return null if paramaters didnt fit
		return null; // Remove this statement when your implementation is complete.
	}

	/**
	 * Returns the spot instance at a given position (i, j)
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the spot instance at position (i, j)
	 */
	public Spot getSpotAt(int i, int j) {
		if (i >= 0 && i < numRows && j>= 0 && j < numSpotsPerRow){
			return occupancy[i][j];
		}
		// WRITE YOUR CODE HERE!	
		return null; // Remove this statement when your implementation is complete.
	}

	/**
	 * Checks whether a car (which has a certain type) is allowed to park at
	 * location (i, j)
	 *
	 * NOTE: This method is complete; you do not need to change it.
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return true if car c can park at (i, j) and false otherwise
	 */

	 // Can take from reference solution
	public boolean canParkAt(int i, int j, Car c) {

		if (i >= numRows || j >= numSpotsPerRow) {
			return false;
		}

		if (occupancy[i][j] != null) {
			return false;
		}

		CarType carType = c.getType();
		CarType spotType = lotDesign[i][j];

		if (carType == CarType.ELECTRIC) {
			return (spotType == CarType.ELECTRIC) || (spotType == CarType.SMALL) || (spotType == CarType.REGULAR)
					|| (spotType == CarType.LARGE);

		} else if (carType == CarType.SMALL) {
			return (spotType == CarType.SMALL) || (spotType == CarType.REGULAR) || (spotType == CarType.LARGE);

		} else if (carType == CarType.REGULAR) {
			return (spotType == CarType.REGULAR) || (spotType == CarType.LARGE);
		} else if (carType == CarType.LARGE) {
			return (spotType == CarType.LARGE);
		}

		return false;
	}

	/**
	 * Attempts to park a car in the lot. Parking is successful if a suitable parking spot
	 * is available in the lot. If some suitable spot is found (anywhere in the lot), the car
	 * is parked at that spot with the indicated timestamp and the method returns "true".
	 * If no suitable spot is found, no parking action is taken and the method simply returns
	 * "false"
	 * 
	 * @param c is the car to be parked
	 * @param timestamp is the simulation time at which parking is attempted for car c 
	 * @return true if c is successfully parked somwhere in the lot, and false otherwise
	 */
	public boolean attemptParking(Car c, int timestamp) {
		
		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numSpotsPerRow; j++){
				if (canParkAt(i, j, c) && occupancy[i][j] == null){
					park(i, j, c, timestamp);
					return true;
				}
			}
		}
		// WRITE YOUR CODE HERE!
		return false; // Remove this statement when your implementation is complete.
	}

	/**
	 * @return the total capacity of the parking lot excluding spots that cannot be
	 *         used for parking (i.e., excluding spots that point to CarType.NA)
	 */
	// taken from reference sol
	public int getTotalCapacity() {
		int count = 0;

		for (int i = 0; i < numRows; i++)
			for (int j = 0; j < numSpotsPerRow; j++)
				if (lotDesign[i][j] != CarType.NA)
					count++;

		return count;
	}

	/**
	 * @return the total occupancy of the parking lot
	 */
	// taken from reference sol
	public int getTotalOccupancy() {
		int occupancyCount = 0;
		// Loop through each spot in the occupancy array.
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numSpotsPerRow; j++) {
				// If the spot is not null, it means there is a car parked there.
				if (occupancy[i][j] != null) {
					occupancyCount++;
				}
			}
		}
		return occupancyCount;
	}
	// taken from reference sol
	private void calculateLotDimensions(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));

		while (scanner.hasNext()) {
			String str = scanner.nextLine().trim();

			if (str.isEmpty()) {
				// Do nothing
			} else {
				numRows++;
				String[] tokens = str.split(SEPARATOR);
				numSpotsPerRow = Integer.max(tokens.length, numSpotsPerRow);
			}
		}

		scanner.close();
	}
	// taken from reference sol
	private void populateDesignFromFile(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));

		int rowNumber = 0;

		// while loop for reading the lot design
		while (scanner.hasNext()) {
			String str = scanner.nextLine().trim();


			if (str.isEmpty()) {
				// Do nothing
			} else {
				String[] tokens = str.split(SEPARATOR);
				for (int i = 0; i < tokens.length; i++)
					lotDesign[rowNumber][i] = Util.getCarTypeByLabel(tokens[i].trim());
				rowNumber++;
			}
		}
		scanner.close();
	}

	/**
	 * NOTE: This method is complete; you do not need to change it.
	 * @return String containing the parking lot information
	 */
	public String toString() {
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
}