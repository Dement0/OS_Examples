public class Searcher extends Thread {

	// Attributes
	int[] values;
	int startIndex;
	int endIndex;
	int minNumber;
	
	// Constructor
	public Searcher(int startIndex, int endIndex, int[] values) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.values = values;
		this.minNumber = values[startIndex]; // Min value for the thread is the element at the start index
	}
	
	// Methods
	public void run() {
		for(int i = this.startIndex + 1; i <= this.endIndex; i++) {
			// If the current element is less than minNumber, assign it to minNumber
			if(values[i] < this.minNumber) {
				this.minNumber = values[i];
			}
		}
	}
	
	public int getMinNumber() {
		return this.minNumber;
	}
}
