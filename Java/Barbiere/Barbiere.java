public class Barbiere extends Thread {

	// Attributes
	private int id;
	private SalaAttesa salaAttesa;
	private int timesServed;
	private int[]	barbieriServedTime;
	// private boolean isOccupied;
	
	// Constructor
	public Barbiere(int id, SalaAttesa salaAttesa, int[] barbieriServedTime) {
		this.id = id;
		this.salaAttesa = salaAttesa;
		this.setName("Barber nu #" + id);
		this.timesServed = 0;
		this.barbieriServedTime = barbieriServedTime;
		// this.isOccupied = false;
	}
	
	// Methods
	public int getTimesServed() {
		return this.timesServed;
	}
	
	public boolean hasAllServed() {
		// If all barbers have served 2 times, sets it to 0
		int j = 0;
		for(int i=0; i<barbieriServedTime.length; i++) {
			if(barbieriServedTime[i] >= 2) {
				j++;
			}
		}
		return j==3;
	}
	
	public void prendiCliente() {
		while(this.salaAttesa.hasClientWaiting()) {
			// Check if all barbers have served
			if(hasAllServed()) {
				// Set it to 0 again
				for(int i=0; i<this.barbieriServedTime.length; i++) {
					this.barbieriServedTime[i] = 0;
				}
			}
			
			// Serve the client
			this.salaAttesa.serviCliente(this.id, this.barbieriServedTime[this.id]);
			
			// Increment timesServed by 1
			timesServed++;
			this.barbieriServedTime[this.id] = timesServed;
						
			System.out.println("Cliente paid the fee.");
		}
	}
	
	// Run method
	public void run() {
		prendiCliente();
	}

}
