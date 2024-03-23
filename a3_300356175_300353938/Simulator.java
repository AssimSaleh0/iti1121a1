/**
 * @author Mehrdad Sabetzadeh, University of Ottawa
 *
 */
public class Simulator {

	/**
	 * Length of car plate numbers
	 */
	public static final int PLATE_NUM_LENGTH = 3;

	/**
	 * Number of seconds in one hour
	 */
	public static final int NUM_SECONDS_IN_1H = 3600;

	/**
	 * Maximum duration a car can be parked in the lot
	 */
	public static final int MAX_PARKING_DURATION = 8 * NUM_SECONDS_IN_1H;

	/**
	 * Total duration of the simulation in (simulated) seconds
	 */
	public static final int SIMULATION_DURATION = 24 * NUM_SECONDS_IN_1H;

	/**
	 * The probability distribution for a car leaving the lot based on the duration
	 * that the car has been parked in the lot
	 */
	public static final TriangularDistribution departurePDF = new TriangularDistribution(0, MAX_PARKING_DURATION / 2,
			MAX_PARKING_DURATION);

	/**
	 * The probability that a car would arrive at any given (simulated) second
	 */
	private Rational probabilityOfArrivalPerSec;

	/**
	 * The simulation clock. Initially the clock should be set to zero; the clock
	 * should then be incremented by one unit after each (simulated) second
	 */
	private int clock;

	/**
	 * Total number of steps (simulated seconds) that the simulation should run for.
	 * This value is fixed at the start of the simulation. The simulation loop
	 * should be executed for as long as clock < steps. When clock == steps, the
	 * simulation is finished.
	 */
	private int steps;

	/**
	 * Instance of the parking lot being simulated.
	 */
	private ParkingLot lot;

	/**
	 * Queue for the cars wanting to enter the parking lot
	 */
	private Queue<Spot> incomingQueue;

	/**
	 * Queue for the cars wanting to leave the parking lot
	 */
	private Queue<Spot> outgoingQueue;

	/**
	 * @param lot   is the parking lot to be simulated
	 * @param perHourArrivalRate  is the rate at which cars arrive per hour
	 * @param steps is the total number of steps for simulation
	 */
	public Simulator(ParkingLot lot, int perHourArrivalRate, int steps) {
        this.lot = lot;
        this.probabilityOfArrivalPerSec = new Rational(perHourArrivalRate, NUM_SECONDS_IN_1H);
        this.steps = steps;
        this.clock = 0;
        this.incomingQueue = new LinkedQueue<>();
        this.outgoingQueue = new LinkedQueue<>();
	}


	/**
	 * Simulate the parking lot for the number of steps specified by the steps
	 * instance variable
	 * NOTE: Make sure your implementation of simulate() uses peek() from the Queue interface.
	 */
public void simulate() {
    // Initial validation to ensure the parking lot and probabilities are set correctly
    if (lot == null || probabilityOfArrivalPerSec == null || steps <= 0) {
        throw new IllegalArgumentException("Simulation parameters cannot be null or non-positive.");
    }

    try {
        for (clock = 0; clock < steps; clock++) {
            // Simulate car arrival
            if (RandomGenerator.eventOccurred(probabilityOfArrivalPerSec)) {
                String plateNumber = RandomGenerator.generateRandomString(PLATE_NUM_LENGTH);
                if (plateNumber == null || plateNumber.length() != PLATE_NUM_LENGTH) {
                    throw new IllegalArgumentException("Generated plate number is invalid.");
                }
                Car arrivingCar = new Car(plateNumber);
                incomingQueue.enqueue(new Spot(arrivingCar, clock));
            }

            // Attempt to park cars from the incoming queue
            while (!incomingQueue.isEmpty()) {
                Spot nextSpot = incomingQueue.peek();
                if (nextSpot == null || nextSpot.getCar() == null) {
                    throw new IllegalStateException("Next spot or car is null.");
                }
                if (lot.attemptParking(nextSpot.getCar(), clock)) {
                    incomingQueue.dequeue();
                } else {
                    break;
                }
            }

            // Simulate car departure
            int i = 0;
            while (i < lot.getOccupancy()) {
                Spot spot = lot.getSpotAt(i);
                if (spot == null) {
                    throw new IllegalStateException("Spot retrieved is null.");
                }
                int parkedDuration = clock - spot.getTimestamp();
                if (parkedDuration >= MAX_PARKING_DURATION ||
                        RandomGenerator.eventOccurred(departurePDF.pdf(parkedDuration))) {
                    lot.remove(i);
                } else {
                    i++;
                }
            }
        }
    } catch (Exception e) {
        // Log the exception and potentially rethrow it or handle it as appropriate for your application
        System.err.println("An error occurred during simulation: " + e.getMessage());
        e.printStackTrace();
        // You might want to rethrow the exception or handle it according to your application's needs
    }
}
    

    /**
     * Get the size of the incoming queue after a simulation run.
     *
     * @return the size of the incoming queue.
     */
	public int getIncomingQueueSize() {
		return incomingQueue.size();
	
	}
}