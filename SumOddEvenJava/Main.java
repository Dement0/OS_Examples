public class Main {

	public static int getSum(int[] values) throws InterruptedException {
	
		// Variables
		int sum = 0;
		
		// Create 2 threads
		SumOddEven thread_1 = new SumOddEven(values, 0);
		SumOddEven thread_2 = new SumOddEven(values, 1);
		
		System.out.println("Thread-1 calculates sum of even numbers...");
		System.out.println("Thread-2 calculates sum of odd numbers....");
		System.out.println("-----");
		
		// Start threads
		thread_1.start();
		thread_2.start();
		
		// Wait for threads to complete
		try {
			thread_1.join();
			thread_2.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		// Results
		System.out.println("Thread-1 sum: " + thread_1.getSum());
		System.out.println("Thread-2 sum: " + thread_2.getSum());
		
		return (thread_1.getSum() + thread_2.getSum());
	}

	public static void main(String[] args) throws InterruptedException {
		
		int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		int sum = getSum(arr);
		System.out.println("Sum: " + sum);
	}
}
