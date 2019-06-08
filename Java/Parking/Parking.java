import java.util.Random;

public class Parking {

	public static void main(String[] args) {
	
		// Create the parking area
		int capacity = 8;
		ParkingArea parkingArea = new ParkingArea(capacity);
		
		// Create Random object
		Random random = new Random();
		
		// Local variables to use
		int indexForEntrance; // 3 entrance
		int indexForExit; // 2 exit
		
		// Create 20 cars
		for(int i=0; i<20; i++) {
		
			indexForEntrance = random.nextInt(3);
			indexForExit = random.nextInt(2);
			
			Car car = new Car(i, indexForEntrance, indexForExit, parkingArea);

			// Start the thread
			car.start();
		}
	}
}
