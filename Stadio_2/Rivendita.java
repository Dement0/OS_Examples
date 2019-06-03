import java.util.LinkedList;

public class Rivendita {

	// Attributes
	private Biglietteria biglietteria;
	public int id;
	private int bigliettiVendibili;
	private LinkedList<Integer> codaCarta = new LinkedList<Integer>(); // Coda per carta di credito
	private LinkedList<Integer> codaContanti = new LinkedList<Integer>();
	
	// Constructor
	public Rivendita(Biglietteria b, int i) {
		this.biglietteria = b; // Assiocia la biglietteria a questa rivedita per poter tracciare la rivendita
		this.id = i;
		this.bigliettiVendibili = this.biglietteria.allocaLotto(this.id);
	}
	
	public synchronized void mettiInCoda(int cliente, int tipoPagamento) {
		// If client wants to pay with card, goes to codaCarta
		if(tipoPagamento == 1) {
			this.codaCarta.add(cliente);
		}
		// Else, goes to codaContanti
		else {
			this.codaContanti.add(cliente);
		}
		this.stampaCoda();
	}
	
	public synchronized int serviCliente(int nRichiesta, int cliente) {
		
		// Se non ci sono biglietti nella rivendita, chiedi a biglietteria
		if(this.bigliettiVendibili == 0) {
			rifornisci();
		}
		
		// Se nella codaCarta ci sono clienti pero' il cliente che viene schedulato non e' il primo
		// o se nella codaCarta non ci sono clienti e nella codaContanti ci sono clienti pero' il cliente che
		// viene schedulato non e' il primo, aspetta
		while(this.codaCarta.size() > 0 && cliente != this.codaCarta.getFirst() || this.codaCarta.size() == 0 && this.codaContanti.size() > 0 && cliente != this.codaContanti.getFirst()) {
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
			
			if(this.codaCarta.size() > 0 && cliente == this.codaCarta.getFirst()) {
				this.codaCarta.removeFirst();
			} else {
				this.codaContanti.pop();
			}
			
			stampaCoda();
		}
		
		// Pagamento
		try {
			if(this.codaCarta.size() > 0 && cliente == this.codaCarta.getFirst()) {
				Thread.sleep((int) Math.round(Math.random() * 1000));
			} else {
				Thread.sleep((int) Math.round(Math.random() * 5000)); // Pagamento contanti dura di piu'
			}
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
		System.out.println("Coda carta di R" + this.id + ": C" + this.codaCarta); // C[0, 7, 10]
		System.out.println("Coda contanti di R" + this.id + ": C" + this.codaContanti); // C[0, 7, 10]
	}
}
