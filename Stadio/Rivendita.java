import java.util.LinkedList;

public class Rivendita {

	// Attributes
	private Biglietteria biglietteria;
	public int id;
	private int bigliettiVendibili;
	private LinkedList<Integer> codaClienti = new LinkedList<Integer>();
	
	// Constructor
	public Rivendita(Biglietteria b, int i) {
		this.biglietteria = b;
		this.id = i;
		this.bigliettiVendibili = this.biglietteria.allocaLotto(this.id);
	}
	
	public synchronized void mettiInCoda(int cliente) {
		this.codaClienti.add(cliente);
		this.stampaCoda();
	}
	
	public synchronized int serviCliente(int nRichiesta, int cliente) {
		
		// Se non ci sono biglietti nella rivendita, chiedi
		if(this.bigliettiVendibili == 0) {
			rifornisci();
		}
		
		// Finche il cliente che ha fatto la richiesta non e' il primo cliente, aspetta
		while(cliente != this.codaClienti.getFirst()) {
			try{
				wait();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		int bigliettiVenduti = nRichiesta;
		
		// Se il cliente richiede piu' biglietti che sono vendibili, aspetta ancora in coda
		if(bigliettiVendibili - nRichiesta < 0) {
			bigliettiVenduti = bigliettiVendibili;
			System.out.println("-- C" + cliente + " ha ottenuto soltanto " + bigliettiVendibili + " di " + nRichiesta + " biglietti - aspetta rifornimento.");
		}
		
		bigliettiVendibili = bigliettiVendibili - bigliettiVenduti;
		
		if(bigliettiVenduti == nRichiesta) {
			System.out.println("-- C" + cliente + " ha ottenuto " + bigliettiVenduti + " biglietti.");
			this.codaClienti.removeFirst();
			stampaCoda();
		}
		
		// Pagamento
		try {
			Thread.sleep((int)Math.round(Math.random() * 1000));
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		notifyAll(); // Notifica altri clienti che stavano aspettando cosi controllano se sono il primo della coda
		return bigliettiVenduti;
	}
	
	public void rifornisci() {
		System.out.println("R" + this.id + " non ha abbastanza biglietti disponibili.");
		this.bigliettiVendibili = this.biglietteria.allocaLotto(this.id);
		System.out.println("R" + this.id + " ha ottenuto un lotto");
	}
	
	void stampaCoda() {
		System.out.println("Coda R" + this.id + ": C" + this.codaClienti); // C[0, 7, 10]
	}
}
