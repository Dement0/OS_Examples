public class Customer extends Thread {

	// Attributes
	private int id;
	private int luggageQuantity;
	private Deposit deposit;
	
	// Constructor
	public Customer(int id, int luggageQuantity, Deposit deposit) {
		this.id = id;
		this.luggageQuantity = luggageQuantity;
		this.deposit = deposit;
	}
	
	// Methods
	private void putLuggage() {
		this.deposit.acceptCustomer(this.id, this.luggageQuantity);
		
		// Make luggage remain some time
		try {
			Thread.sleep((int) Math.round(Math.random() * 5001));
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void takeLuggage() {
		this.deposit.returnLuggage(this.id, this.luggageQuantity);
	}
	
	// Run method
	@Override
	public void run() {
		putLuggage();
		takeLuggage();
	}
}
