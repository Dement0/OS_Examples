public class SearchTheMaxNum {
	public static void main(String[] args) {
		
		int[] a = {5, 15, 1, 13, 9, 20, 16, 3, 7, 18, 10, 19, 2, 4, 17, 11, 6, 12, 8, 14};
		int numberOfThreads = 5;
		findMaxNum(numberOfThreads, a);
	}
	
	public static void findMaxNum(int numberOfThreads, int[] a) {
		
		int sizeOfArray = a.length;
		int range = sizeOfArray / numberOfThreads;
		int[] maxNumbers = new int[numberOfThreads];
		int maxNum;	
		
		// Loop to search the max number as much threads we have
		for(int i = 0; i <= numberOfThreads-1; i++) {
			
			// Reference for the Thread
			Searcher searcher;
			
			// If it's the last thread
			if(i == numberOfThreads-1) {
				searcher = new Searcher(i*range, sizeOfArray-1, a);
			}
			else {
				searcher = new Searcher(i*range, (i*range + range - 1), a);
			}
			searcher.start();
			try {
				searcher.join();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			maxNumbers[i] = searcher.getMaxNumber();
		}
		
		// Now max number of each thread is stored in maxNumbers
		// Loop to determine and print the max number
		maxNum = maxNumbers[0];	

		for(int j = 1; j < numberOfThreads; j++) {
			if(maxNumbers[j] > maxNum) {
				maxNum = maxNumbers[j];
			}
		}
		
		for(int k = 0; k < numberOfThreads; k++) {
			System.err.println(maxNumbers[k]);
		}
		
		System.err.println("Max number is: " + maxNum);
	}
}
