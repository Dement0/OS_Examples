// Shared resource
import java.util.ArrayList;
import java.util.LinkedList;

public class Deposit {

	// Attributes
	private LinkedList<Integer> waitingQueue = new LinkedList<>();
	private int room0;
	private int room1;
	private int room2;
	private ArrayList<Integer> customersRoom0;
	private ArrayList<Integer> customersRoom1;
	private ArrayList<Integer> customersRoom2;
		
	// Constructor
	public Deposit(int roomCapacity) {
		this.room0 = roomCapacity;
		this.room1 = roomCapacity;
		this.room2 = roomCapacity;
		
		this.customersRoom0 = new ArrayList<Integer>();
		this.customersRoom1 = new ArrayList<Integer>();
		this.customersRoom2 = new ArrayList<Integer>();
	}

	// Methods
	public synchronized void enqueueCustomer(int id) {
		this.waitingQueue.add(id);
		System.out.println("Added customer #" + id + " to queue.");
	}
	
	public synchronized void acceptCustomer(int id, int luggageQuantity) {
		// Enter controls
		while((this.room0 < luggageQuantity) && (this.room1 < luggageQuantity) && (this.room2 < luggageQuantity) || this.waitingQueue.getFirst() != id) {
			try {
				System.out.println("No space or queue breach! Customer #" + id + " waits.");
				wait();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// If passed controls, make it enter checking the available room
		if(this.room0 >= luggageQuantity) {
			this.waitingQueue.removeFirst();
			this.room0 -= luggageQuantity;
			this.customersRoom0.add(id);
		} else if(this.room1 >= luggageQuantity) {
			this.waitingQueue.removeFirst();
			this.room1 -= luggageQuantity;
			this.customersRoom1.add(id);
		} else if(this.room2 >= luggageQuantity) {
			this.waitingQueue.removeFirst();
			this.room2 -= luggageQuantity;
			this.customersRoom2.add(id);
		} else {
			// There might be an error about enter controls
			System.out.println("Possible error. Check the enter controls.");
		}
		
		// Notify other threads
		System.out.println("Customer #" + id + " left his/her luggage.");
		notifyAll();
	}
	
	public synchronized void returnLuggage(int id, int luggageQuantity) {
		// Enter controls
		while((!(this.customersRoom0.contains(id))) && (!(this.customersRoom1.contains(id))) && (!(this.customersRoom2.contains(id)))) {
			try {
				System.out.println("Customer hasn't left his luggage or queue breach! Customer #" + id + " waits.");
				wait();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// If passed controls, make it exit
		if(this.customersRoom0.contains(id)) {
			this.room0 += luggageQuantity;
		} else if(this.customersRoom1.contains(id)) {
			this.room1 += luggageQuantity;
		} else if(this.customersRoom2.contains(id)) {
			this.room2 += luggageQuantity;
		} else {
			// There might be an error about enter controls
			System.out.println("Possible error. Check the exit controls.");
		}
		
		// Notify other threads
		System.out.println("Customer #" + id + " got his/her luggage back.");
		notifyAll();
	}
}
