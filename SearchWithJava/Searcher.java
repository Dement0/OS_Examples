public class Searcher extends Thread {
	
	// Attributes
	private int startIndex;
	private int endIndex;
	private int[] arrayToSearch;
	private int maxNumber;
	
	// Constructor
	public Searcher(int s, int e, int[] a) {
		this.startIndex = s;
		this.endIndex = e;
		this.arrayToSearch = a;
		this.maxNumber = arrayToSearch[s]; // max number for each thread is it's first element
	}
	
	// Methods
	public void run() {
		for(int i = startIndex+1; i <= endIndex; i++) {
			// If i > maxNumber, assign that to maxNumber
			if(arrayToSearch[i] > maxNumber) {
				this.maxNumber = arrayToSearch[i];
			}
		}
	}
	
	public int getMaxNumber() {
		return this.maxNumber;
	}
}
