/*
 * Create 2 threads to sum up the numbers in an array
 * First thread sums up the even numbers
 * Second thread sums up the odd numbers
 * Finally, the program prints out the total sum returned by two threads
 */


#include <pthread.h>
#include <stdio.h>
#include <unistd.h>


// Structure to pass in thread_function_parameters
struct sum_up_params {
	int* numbersToSum;
	int howManyNumbers;
	int threadSum;
};

// Thread function to sum up the numbers
void* sum_up(void* parameters) {
	
	// Passing a pointer as parameter, so have to do an explicit cast
	struct sum_up_params* sum_p = (struct sum_up_params*) parameters;
	
	// Do a for loop as much as the numbers to sum
	for(int i = 0; i < sum_p->howManyNumbers; i++) {
		sum_p->threadSum += sum_p->numbersToSum[i];
	}
	
	printf("%d\n", sum_p->threadSum);
	
	// Return pointer to result
	return (void*) &(sum_p->threadSum);
}

int main() {

	// Initialize an array to sum up the numbers
	int arr[] = {3, 5, 1, 10, 4, 9, 6, 8, 7, 2};
	int evenNums[10];
	int oddNums[10];
	
	// Array size
	int arraySize = sizeof(arr);

	// Variables for thread IDs
	pthread_t thread_ID1;
	pthread_t thread_ID2;
	
	// Variables for return values of the threads
	int* th1_returnValue;
	int* th2_returnValue;
	
	// Variables to specify which arguments to use
	struct sum_up_params th1_args;
	struct sum_up_params th2_args;
	
	// Assign the attributes to the thread arguments
	th1_args.numbersToSum = evenNums;
	th1_args.howManyNumbers = 0;
	th1_args.threadSum = 0;
	
	th2_args.numbersToSum = oddNums;
	th2_args.howManyNumbers = 0;
	th2_args.threadSum = 0;
	
	// For loop to decide if the number is even or odd and to push it to the corresponding array
	for(int j = 0; j < 10; j++) {
		if(arr[j] % 2 == 0) {
			// It's even, push it to evenNums array
			evenNums[th1_args.howManyNumbers] = arr[j];
			th1_args.howManyNumbers++;
		}
		else {
			// It's odd, push it to oddNums array
			oddNums[th2_args.howManyNumbers] = arr[j];
			th2_args.howManyNumbers++;
		}
	}
	
	// Create two threads
	// int pthread_create(pthread_t *thread, const pthread_attr_t *attr, void *(*start_routine) (void *), void *arg);
	pthread_create(&thread_ID1, NULL, &sum_up, &th1_args);
	pthread_create(&thread_ID2, NULL, &sum_up, &th2_args);
	
	// Wait for the return values of the threads
	// As hoin requires a pointer instead of a value, I have to point the return value via a pointer
	pthread_join(thread_ID1, (void**) &th1_returnValue);
	pthread_join(thread_ID2, (void**) &th2_returnValue);
	
	printf("Sum of two arrays: %d\n", (*th1_returnValue + *th2_returnValue));

	return 0;
}
