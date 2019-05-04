public class SearchTheMinNum {

	public static void main(String[] args) throws InterruptedException {
		int[] arr = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144};
		int numberOfThreads = 6;
		findTheMinNum(arr, numberOfThreads);
	}
	
	public static void findTheMinNum(int[] values, int numberOfThreads) throws InterruptedException {
			
			// Variables
			int sizeOfArray = values.length;
			int range = sizeOfArray / numberOfThreads; // Defines the range of each thread
			int minNumbers[] = new int[numberOfThreads];
			int minNumber;
			Searcher searcher;
			
			// Loop as much threads we have
			for(int i = 0; i < numberOfThreads; i++) {
				// If it's the last thread
				if(i == numberOfThreads - 1) {
					searcher = new Searcher(i * range, sizeOfArray - 1, values);
				}
				else {
					searcher = new Searcher(i * range, ((i + 1) * range - 1), values);
				}
				
				// Start the thread
				searcher.start();
				
				try {
					// Wait for the thread to terminate
					searcher.join();
					
					// Assign the return value to the minNumbers array
					minNumbers[i] = searcher.getMinNumber();
					System.out.println("Result of thread " + (i + 1) + ": " + minNumbers[i]);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// Minimum number of each thread is stored in minNumbers array
			// Loop to determine the lowest value
			minNumber = minNumbers[0];
			for(int j = 1; j < numberOfThreads; j++) {
				if(minNumbers[j] < minNumber) {
					minNumber = minNumbers[j];
				}
			}
			
			System.out.println("Min number is: " + minNumber);
	}
}
