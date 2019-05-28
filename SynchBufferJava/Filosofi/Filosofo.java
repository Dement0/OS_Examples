public class Filosofo extends Thread {

	// Attributes
	private int IDF;
		private final int NSEC = 0;
	// private final int NSEC = 2;
	private Bacchetta bacchettaSin;
	private Bacchetta bacchettaDes;
	
	// Constructor
	public Filosofo(int id, Bacchetta sin, Bacchetta des) {
	
		this.IDF = id;
		this.setName("Filosofo #" + this.IDF);
		this.bacchettaSin = sin;
		this.bacchettaDes = des;
	}
	
	// Methods
	private void pensa() {
		System.out.println(this.getName() + " sta pensando..");
		try {
			Thread.sleep(Math.round(Math.random() * NSEC * 1000));
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void mangia() {
		System.out.println(this.getName() + " sta mangiando..");
		try {
			Thread.sleep(Math.round(Math.random() * NSEC * 1000));
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void prendiBacchette() {
		System.out.println(this.getName() + " sta cercando di prendere la bacchetta SX: " + this.bacchettaSin.bacchettaNum);
		this.bacchettaSin.prendi();
		System.out.println(this.getName() + " ha preso la bacchetta SX: " + this.bacchettaSin.bacchettaNum);
		
		System.out.println(this.getName() + " sta cercando di prendere la bacchetta DX: " + this.bacchettaDes.bacchettaNum);
		this.bacchettaDes.prendi();
		System.out.println(this.getName() + " ha preso la bacchetta DX: " + this.bacchettaDes.bacchettaNum);
	}
	
	public void rilasciaBacchette() {
		System.out.println(this.getName() + " ha finito di mangiare, rilascia le bacchette");
		bacchettaSin.rilascia();
		bacchettaDes.rilascia();
	} 
	
	// Run method
	public void run() {
		for(;;) {
			this.pensa();
			this.prendiBacchette();
			this.mangia();
			this.rilasciaBacchette();
		}
	}

}
