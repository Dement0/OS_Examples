public class Bacchetta {

	// Attributes
	private boolean bacchettaLibera;
	public int bacchettaNum;
	
	// Constructor
	public Bacchetta(int id) {
		this.bacchettaNum = id;
		this.bacchettaLibera = true;
	}
	
	// Synchronized methods
	public synchronized void prendi() {
	
		// If bacchetta is not available, wait
		while(!this.bacchettaLibera) {
			try {
				System.out.println(Thread.currentThread().getName() + " sta aspettando la bacchetta " + this.bacchettaNum);
				wait();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.bacchettaLibera = false;
	
	}
	
	public synchronized void rilascia() {
		this.bacchettaLibera = true; // Set bacchettaLibera to true
		notifyAll(); // Notify all sleeping threads
	}

}
