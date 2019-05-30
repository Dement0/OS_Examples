import java.util.Random;

public class Biglietteria {

	// Attributes
	private int bigliettiResidui;
	private final static int lotto = 50;
	
	// Constructor
	public Biglietteria(int inizioTOT) {
		this.bigliettiResidui = inizioTOT;
	}
	
	// Main method
	public static void main(String[] args) {
	
		// Variables
		int numRivendite = 5;
		int totaleBiglietti = 1000;
		int clientiMax = 100;
		int bigliettiMax = 50; // Il cliente puo' chiedere max 50 biglietti
		
		Cliente c[] = new Cliente[clientiMax]; // Array di clienti
		Biglietteria biglietteria = new Biglietteria(totaleBiglietti);
		
		Rivendita rivendite[] = new Rivendita[numRivendite];
		for(int i=0; i<numRivendite; i++) {
			rivendite[i] = new Rivendita(biglietteria, i); // Assiocia ID i alla biglietteria
		}
		
		Random r = new Random();
		int index;
		int biglietti;
		
		for(int i=0; i<clientiMax; i++) {
			index = r.nextInt(numRivendite);
			biglietti = r.nextInt(bigliettiMax) + 1; // Quanti biglietti richiederanno i clienti
			
			c[i] = new Cliente(i, rivendite[index], biglietti);
			c[i].start();
			
			System.out.println("-- C" + i + " chiede " + biglietti + " biglietti di R" + index);
			
			try{
				Thread.sleep(r.nextInt(100));
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
		
	public synchronized int allocaLotto(int id) {
		
		int bigliettiRilasciati = lotto;
		
		// Se ci sono 40 biglietti rimasti pero il lotto richiede 50, do soltanto 40
		if(bigliettiResidui < lotto) {
			bigliettiRilasciati = bigliettiResidui;
		}
		bigliettiResidui = bigliettiResidui - bigliettiRilasciati;
		System.out.println(bigliettiRilasciati + " tickets rilasciati ad R" + id + "; -> " + bigliettiResidui + " tickets ancora disponibili.");
		return bigliettiRilasciati;
	}
}
