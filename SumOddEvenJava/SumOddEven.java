public class SumOddEven extends Thread {

	// Attributes
	private int[] values;
	private int type;
	private int sum;
	
	// Constructor
	public SumOddEven(int[] values, int type) {
		this.sum = 0;
		this.values = values;
		this.type = type;
	}
	
	// Methods
	public void run() {
		for(int i=type; i<values.length; i+=2) {
			sum += values[i];
		}
	}
	
	public int getSum() {
		return this.sum;
	}
}
