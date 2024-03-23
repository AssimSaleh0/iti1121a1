public class CapacityOptimizer {
	private static final int NUM_RUNS = 10;

	private static final double THRESHOLD = 5.0d;

    public static int getOptimalNumberOfSpots(int hourlyRate) {
        int optimalNumberOfSpots = 1; // Start with the minimum number of spots

        while (true) {
            double averageQueueLength = 0;
            for (int i = 0; i < NUM_RUNS; i++) {
                // Run the simulation for the current number of spots
                Simulator sim = new Simulator(new ParkingLot(optimalNumberOfSpots), hourlyRate, Simulator.SIMULATION_DURATION);
                sim.simulate();
                averageQueueLength += sim.getIncomingQueueSize();
            }
            averageQueueLength /= NUM_RUNS;

            // Print out the simulation result for the current number of spots
            System.out.println("==== Setting lot capacity to: " + optimalNumberOfSpots + "====");
            for (int i = 0; i < NUM_RUNS; i++) {
                // The printed queue length is just a placeholder since you're printing after the simulations are done
                System.out.println("Simulation run " + (i + 1) + ": Queue length at the end of simulation run: " + averageQueueLength);
            }

            // If the average queue length is less than or equal to the threshold, then we've found our optimal number of spots
            if (averageQueueLength <= THRESHOLD) {
                break;
            }

            // Otherwise, increase the number of spots and try again
            optimalNumberOfSpots++;
        }

        return optimalNumberOfSpots;
    }

	public static void main(String args[]) {
	
		StudentInfo.display();

		long mainStart = System.currentTimeMillis();

		if (args.length < 1) {
			System.out.println("Usage: java CapacityOptimizer <hourly rate of arrival>");
			System.out.println("Example: java CapacityOptimizer 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		int hourlyRate = Integer.parseInt(args[0]);

		int lotSize = getOptimalNumberOfSpots(hourlyRate);

		System.out.println();
		System.out.println("SIMULATION IS COMPLETE!");
		System.out.println("The smallest number of parking spots required: " + lotSize);

		long mainEnd = System.currentTimeMillis();

		System.out.println("Total execution time: " + ((mainEnd - mainStart) / 1000f) + " seconds");

	}
}