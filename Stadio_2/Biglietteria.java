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
		int clientiMax = 10;
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
			index = r.nextInt(numRivendite); // Per creare un cliente mettendolo ad una Rivendita in maniera casuale
			biglietti = r.nextInt(bigliettiMax) + 1; // Quanti biglietti richiederanno i clienti, in maniera casuale
			
			c[i] = new Cliente(i, rivendite[index], biglietti);
			c[i].start(); // Start threads
			
			System.out.println("-- C" + i + " chiede " + biglietti + " biglietti ad R" + index);
			
			try{
				Thread.sleep(r.nextInt(100));
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Metodo per allocare un lotto di biglietti ad una Rivendita
	// Deve essere sincronizato perche devo servire soltanto ad una rivendita nello stesso tempo	
	public synchronized int allocaLotto(int id) {
		
		int bigliettiRilasciati = lotto;
		
		// Se ci sono 40 biglietti rimasti pero il lotto richiede 50, do soltanto 40
		if(this.bigliettiResidui < lotto) {
			bigliettiRilasciati = bigliettiResidui;
		}
		
		// Aggiorna bigliettiResidui nella biglietteria
		this.bigliettiResidui = bigliettiResidui - bigliettiRilasciati;
		System.out.println(bigliettiRilasciati + " tickets rilasciati ad R" + id + "; -> " + bigliettiResidui + " tickets ancora disponibili.");
		
		return bigliettiRilasciati;
	}
}
