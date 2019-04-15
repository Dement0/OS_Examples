/*
 * Calculates the coefficients of the matrix
 * For every calculation creates a thread
 * To calculate it eliminates the i'th row and the j'th column
 * After elimination calculates the determinant of the remaining matrix
 * Finally multiplicates it with (-1)^(i+j)
 * Puts the result in the A[ij]'th position
 */
 
#include <pthread.h>
#include <stdio.h> 
#include <unistd.h>


// Structure to pass in thread function parameters
struct calculation_parameters {
	int* matrix;
	int i, j;
	int determinant;
};


// Thread function
void* calculate_coefficient(void* parameters) {

	// Helper array
	int helper[4];
	int helperIndex = 0;

	// Cast void* pointer to calculation_parameters pointer
	struct calculation_parameters* cal_p = (struct calculation_parameters*) parameters;
	
	// For loop
	for(int k = 0; k < 3; k++) {

		// Cancel the i'th row
		if(k == cal_p->i) {
			k++;
		}
				
		for(int l = 0; l < 3; l++) {
			
			// Cancel the j'th column
			if(l == cal_p->j) {
				l++;
			}
			
			// Push the current value to the helper array
			helper[helperIndex] = cal_p->matrix[k+l];
			
			// Increment the helperIndex
			helperIndex++;
		}
	}
	
	// Calculate the coefficient
	cal_p->determinant = (helper[0] * helper[3] - helper[1] * helper[2]);
	
	// Return pointer to result
	return (void *) &(cal_p->determinant);
}


// Main
int main() {
	
	// Initialize the array
	int arr[9] = {5, 2, 3, 4, 3, 5, 6, 3, 8};
	
	// Variables for thread IDs
	pthread_t thread_ID1;
	pthread_t thread_ID2;
	pthread_t thread_ID3;
	pthread_t thread_ID4;
	pthread_t thread_ID5;
	pthread_t thread_ID6;
	pthread_t thread_ID7;
	pthread_t thread_ID8;
	pthread_t thread_ID9;
	
	// Variables for return values of the threads
	int* th1_returnValue;
	int* th2_returnValue;
	int* th3_returnValue;
	int* th4_returnValue;
	int* th5_returnValue;
	int* th6_returnValue;
	int* th7_returnValue;
	int* th8_returnValue;
	int* th9_returnValue;
	
	// Variables to specify which arguments to use
	struct calculation_parameters th1_args;
	struct calculation_parameters th2_args;
	struct calculation_parameters th3_args;
	struct calculation_parameters th4_args;
	struct calculation_parameters th5_args;
	struct calculation_parameters th6_args;
	struct calculation_parameters th7_args;
	struct calculation_parameters th8_args;
	struct calculation_parameters th9_args;
	
	// Assign variables to thread arguments
	th1_args.matrix = arr;
	th1_args.i = 0;
	th1_args.j = 0;
	
	th2_args.matrix = arr;
	th2_args.i = 0;
	th2_args.j = 1;
	
	th3_args.matrix = arr;
	th3_args.i = 0;
	th3_args.j = 2;
	
	th4_args.matrix = arr;
	th4_args.i = 1;
	th4_args.j = 0;
	
	th5_args.matrix = arr;
	th5_args.i = 1;
	th5_args.j = 1;
	
	th6_args.matrix = arr;
	th6_args.i = 1;
	th6_args.j = 2;
	
	th7_args.matrix = arr;
	th7_args.i = 2;
	th7_args.j = 0;
	
	th8_args.matrix = arr;
	th8_args.i = 2;
	th8_args.j = 1;
	
	th9_args.matrix = arr;
	th9_args.i = 2;
	th9_args.j = 2;
	
	// Create 9 threads for the 3x3 matrix
	// int pthread_create(pthread_t *thread, const pthread_attr_t *attr, void *(*start_routine) (void *), void *arg);
	pthread_create(&thread_ID1, NULL, &calculate_coefficient, &th1_args);
	pthread_create(&thread_ID2, NULL, &calculate_coefficient, &th2_args);
	pthread_create(&thread_ID3, NULL, &calculate_coefficient, &th3_args);
	pthread_create(&thread_ID4, NULL, &calculate_coefficient, &th4_args);
	pthread_create(&thread_ID5, NULL, &calculate_coefficient, &th5_args);
	pthread_create(&thread_ID6, NULL, &calculate_coefficient, &th6_args);
	pthread_create(&thread_ID7, NULL, &calculate_coefficient, &th7_args);
	pthread_create(&thread_ID8, NULL, &calculate_coefficient, &th8_args);
	pthread_create(&thread_ID9, NULL, &calculate_coefficient, &th9_args);
	
	// Wait for the return values of the threads
	pthread_join(thread_ID1, (void**) &th1_returnValue);
	pthread_join(thread_ID2, (void**) &th2_returnValue);
	pthread_join(thread_ID3, (void**) &th3_returnValue);
	pthread_join(thread_ID4, (void**) &th4_returnValue);
	pthread_join(thread_ID5, (void**) &th5_returnValue);
	pthread_join(thread_ID6, (void**) &th6_returnValue);
	pthread_join(thread_ID7, (void**) &th7_returnValue);
	pthread_join(thread_ID8, (void**) &th8_returnValue);
	pthread_join(thread_ID9, (void**) &th9_returnValue);
	
	// Print results
	printf("First thread result: %d\n", *th1_returnValue);
	printf("Second thread result: %d\n", *th2_returnValue);
	printf("Third thread result: %d\n", *th3_returnValue);
	printf("Fourth thread result: %d\n", *th4_returnValue);
	printf("Fifth thread result: %d\n", *th5_returnValue);
	printf("Sixth thread result: %d\n", *th6_returnValue);
	printf("Seventh thread result: %d\n", *th7_returnValue);
	printf("Eighth thread result: %d\n", *th8_returnValue);
	printf("Nineth thread result: %d\n", *th9_returnValue);
	
	return 0;
}

