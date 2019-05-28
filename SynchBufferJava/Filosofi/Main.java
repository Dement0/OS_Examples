public class Main {

	public static void main(String[] args) {
	
		// Variables
		int numFilosofi = 5;
		Filosofo filosofi[] = new Filosofo[numFilosofi];
		Bacchetta bacchette[] = new Bacchetta[numFilosofi];
		
		// Create objects
		for(int i=0; i<5; i++) {
			bacchette[i] = new Bacchetta(i);
		}
		
		for(int i=0; i<5; i++) {
			int sinistra, destra;
			
			sinistra = i-1;
			if(sinistra<0) {
				sinistra = numFilosofi - 1; // For the philosophe ID0, sinistra will be 4
			}
			
			destra = i;
			
			filosofi[i] = new Filosofo(i, bacchette[sinistra], bacchette[destra]);
			filosofi[i].start();
		}
	}
}
