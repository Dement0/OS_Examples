public class Car extends Thread {

	// Attributes
	private int id;
	private int entrance;
	private int exit;
	private ParkingArea parkingArea;
	
	// Constructor
	public Car(int id, int indexForEntrance, int indexForExit, ParkingArea parkingArea) {
		this.id = id;
		this.entrance = indexForEntrance;
		this.exit = indexForExit;
		this.parkingArea = parkingArea;
	}
	
	// Methods
	private void enqueueToEnter() {
		this.parkingArea.enqueueToEnter(this.id, this.entrance);
	}
	
	private void enterParkingArea() {
		this.parkingArea.makeCarEnter(this.id, this.entrance);
	}
	
	private void stayParked() {
		try {
			System.out.println("Car #" + this.id + " is parked.");
			Thread.sleep((int) Math.round(Math.random() * 5001));
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void enqueueToExit() {
		this.parkingArea.enqueueToExit(this.id, this.exit);
	}
	
	private void exitParkingArea() {
		this.parkingArea.makeCarExit(this.id, this.exit);
	}
	
	// Run method
	@Override
	public void run() {
		enqueueToEnter();
		enterParkingArea();
		stayParked();
		enqueueToExit();
		exitParkingArea();
	}
}
