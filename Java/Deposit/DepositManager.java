import java.util.Random;

public class DepositManager {

	public static void main(String[] args) {
	
		// Variables to create a deposit
		int roomCapacity = 10;
		
		// Create a deposit
		Deposit deposit = new Deposit(roomCapacity);
		
		// Random object
		Random random = new Random();
		
		// Variable to determine a random quantity of luggages
		int luggageQuantity;
		
		// Create 20 customers
		for(int i=0; i<20; i++) {
		
			// Get a random number of luggage, but less than the room capacity
			luggageQuantity = (random.nextInt(roomCapacity - 1) + 1);
			
			Customer customer = new Customer(i, luggageQuantity, deposit);
			
			System.out.println("Created customer #" + i + " with " + luggageQuantity + " luggages.");
			
			// Put the customer into queue
			deposit.enqueueCustomer(i);
			
			// Start threads
			customer.start();
		}
	}
}
