import java.util.LinkedList;

public class ParkingArea {

	// Attributes
	// Lists for enter and exit
	LinkedList<Integer> queueToEnter0 = new LinkedList<>();
	LinkedList<Integer> queueToEnter1 = new LinkedList<>();
	LinkedList<Integer> queueToEnter2 = new LinkedList<>();
	
	LinkedList<Integer> queueToExit0 = new LinkedList<>();
	LinkedList<Integer> queueToExit1 = new LinkedList<>();
	
	private int capacity;
	
	// Constructor
	public ParkingArea(int capacity) {
		this.capacity = capacity;
	}
	
	// Methods
	public synchronized void enqueueToEnter(int id, int entrance) {
		// Put the car to the corresponding queue
		if(entrance == 0) {
			this.queueToEnter0.add(id);
		}
		if(entrance == 1) {
			this.queueToEnter1.add(id);
		}
		if(entrance == 2) {
			this.queueToEnter2.add(id);
		}
	}
	
	public synchronized void makeCarEnter(int id, int entrance) {
		// If entrance is 0, check the queueToEnter0
		if(entrance == 0) {
			// Enter controls
			while(this.capacity == 0 || this.queueToEnter0.getFirst() != id) {
				try {
					System.out.println("No capacity or queue breach. Car " + id + " waits to enter.");
					wait();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// If passed the controls, make it enter
			this.queueToEnter0.removeFirst();
			this.capacity--;
			System.out.println("Car " + id + " enters to Parking Area.");
			
			// Notify other threads
			notifyAll();
		}
		
		// If entrance is 1, check the queueToEnter1
		if(entrance == 1) {
			// Enter controls
			while(this.capacity == 0 || this.queueToEnter1.getFirst() != id) {
				try {
					System.out.println("No capacity or queue breach. Car " + id + " waits to enter.");
					wait();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// If passed controls, make it enter
			this.queueToEnter1.removeFirst();
			this.capacity--;
			System.out.println("Car " + id + " enters to Parking Area.");
			
			// Notify other threads
			notifyAll();
		}
		
		// If entrance is 2, check the queueToEnter2
		if(entrance == 2) {
			// Enter controls
			while(this.capacity == 0 || this.queueToEnter2.getFirst() != id) {
				try {
					System.out.println("No capacity or queue breach. Car " + id + " waits to enter.");
					wait();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// If passed controls, make it enter
			this.queueToEnter2.removeFirst();
			this.capacity--;
			System.out.println("Car " + id + " enters to Parking Area.");
			
			// Notify other threads
			notifyAll();
		}
	}
	
	public synchronized void enqueueToExit(int id, int exit) {
		// Put the car to the corresponding queue
		if(exit == 0) {
			this.queueToExit0.add(id);
		}
		if(exit == 1) {
			this.queueToExit1.add(id);
		}
	}
	
	public synchronized void makeCarExit(int id, int exit) {
		// If exit is 0, check the queueToExit0
		if(exit == 0) {
			// Exit controls
			while(this.queueToExit0.getFirst() != id) {
				try {
					System.out.println("Queue breach. Car " + id + " waits to exit.");
					wait();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// If passed controls, make it exit
			this.queueToExit0.removeFirst();
			this.capacity++;
			System.out.println("Car " + id + " exits the Parking Area.");
			
			// Notify other threads
			notifyAll();
		}
		
		// If exit is 1, check the queueToExit1
		if(exit == 1) {
			// Exit controls
			while(this.queueToExit1.getFirst() != id) {
				try {
					System.out.println("Queue breach. Car " + id + " waits to exit.");
					wait();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// If passed controls, make it exit
			this.queueToExit1.removeFirst();
			this.capacity++;
			System.out.println("Car " + id + " exits the Parking Area.");
			
			// Notify other threads
			notifyAll();
		}
	}
}
