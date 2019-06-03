public class Cliente extends Thread {

	// Attributes
	private int id;
	private int tipoPagamento;
	public Rivendita rivendita;
	private int bigliettiDesiderati;
	private int bigliettiComprati;
	
	// Constructor
	public Cliente(int i, Rivendita r, int desiderati) {
		this.id = i;
		this.tipoPagamento = (int) Math.round(Math.random()); // 0 contanti, 1 carta
		this.rivendita = r;
		this.bigliettiDesiderati = desiderati;
		this.bigliettiComprati = 0;
	}
	
	// Run method
	public void run() {
		this.rivendita.mettiInCoda(this.id, this.tipoPagamento);

		// Se il cliente ancora non ha comprato i biglietti desiderati, fa acquista
		while(this.bigliettiDesiderati != this.bigliettiComprati) {
			this.acquista((this.bigliettiDesiderati - this.bigliettiComprati), this.id);
		}
	}
	
	public void acquista(int n, int cliente) {
		this.bigliettiComprati = (this.bigliettiComprati + this.rivendita.serviCliente(n, cliente));
	}
}
