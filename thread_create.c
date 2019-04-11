/*
 * Creates a thread
 *
 */

#include <pthread.h>
#include <stdio.h>
#include <unistd.h>

// Thread function
void* print1(void* unused) {
	while(1) {
		printf("-\n");
		fflush(stdout);
		sleep(1);
	}
	
	return NULL;
}

int main() {

  // Variable for the thread ID
	pthread_t thread_id;
	
	// int pthread_create(pthread_t *thread, const pthread_attr_t *attr, void *(*start_routine) (void *), void *arg);
	pthread_create(&thread_id, NULL, &print1, NULL);
	
	while(1) {
		printf("o\n");

		// for buffer issues of printf() with sleep()
		fflush(stdout);

		sleep(1);
	}
	
	return 0;

}

// $ gcc -o thread_create thread_create.c -lpthread (to compile)
