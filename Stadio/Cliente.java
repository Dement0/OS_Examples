public class Cliente extends Thread {

	// Attributes
	private int id;
	Rivendita rivendita;
	private int bigliettiDesiderati;
	private int bigliettiComprati;
	
	// Constructor
	public Cliente(int i, Rivendita r, int desiderati) {
		this.id = i;
		this.rivendita = r;
		this.bigliettiDesiderati = desiderati;
		this.bigliettiComprati = 0;
	}
	
	// Run method
	public void run() {
		this.rivendita.mettiInCoda(id);

		// While il cliente ancora non ha comprato i biglietti desiderati		
		while(this.bigliettiDesiderati != this.bigliettiComprati) {
			acquista((this.bigliettiDesiderati - this.bigliettiComprati), this.id);
		}
	}
	
	public void acquista(int n, int cliente) {
		this.bigliettiComprati = (this.bigliettiComprati + this.rivendita.serviCliente(n, cliente));
	}
}
