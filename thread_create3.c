/*
 * Creates 2 threads which accept parameters and has return values
 *
 */


#include <pthread.h>
#include <stdio.h>
#include <unistd.h>


// Structure to pass print parameters
// Because I can only pass 1 pointer to thread function `(void *), void *arg`, so I specify here if I want more values
struct print_params {
	char character;
	int times;
};


// Thread function
void* print1(void* parameters) {

	// We are passing a pointer, so we have to make explicit cast
	struct print_params* pp = (struct print_params*) parameters;
	
	for(int i=0; i <= pp->times; i++) {
		printf("%c\n", pp->character);
		fflush(stdout);
		sleep(1);
	}
	
	// Cast int to pointer
	return (void*) &(pp->times);
}

int main() {
	// Example with two threads

  // Variable for the thread IDs
	pthread_t thread_id1;
	pthread_t thread_id2;
	
	// Specifying return values of threads
	int* th1_returnValue;
	int* th2_returnValue;
	
	// Variables to specify which arguments to use
	struct print_params th1_args;
	struct print_params th2_args;
	
	// Assign the attributes
	th1_args.character = '-';
	th2_args.character = 'o';
	
	th1_args.times = 30;
	th2_args.times = 20;
	
	// int pthread_create(pthread_t *thread, const pthread_attr_t *attr, void *(*start_routine) (void *), void *arg);
	pthread_create(&thread_id1, NULL, &print1, &th1_args);
	pthread_create(&thread_id2, NULL, &print1, &th2_args);
	
	// As join requires a pointer instead of a value, I have to point that value via a pointer
	pthread_join(thread_id1, (void**) &th1_returnValue);
	pthread_join(thread_id2, (void**) &th2_returnValue);
	
	printf("\nvalue returned by TH1: %d\n", *th1_returnValue);
	printf("value returned by TH2: %d\n", *th2_returnValue);
	
	return 0;
}

// $ gcc -o thread_create thread_create.c -lpthread (to compile)
// $ ps -e -o pid,cmd (to list processes)
