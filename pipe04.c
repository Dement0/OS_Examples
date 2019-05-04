/*
 * Parent creates a random number between 1-100
 * Sends it to first child via first pipe
 * First child adds 1 to received number
 * Sends this number to the second child via a second pipe
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <wait.h>

int main() {

	// Define variables
	int fd_1[2];
	int fd_2[2];
	
	int child_status_1;
	int child_status_2;
	
	int number;
	
	pid_t pid_1, pid_2;
	
	// Create 2 pipes
	pipe(fd_1);
	pipe(fd_2);
	
	// Fork to create a child
	pid_1 = fork();
	
	if(pid_1 == -1) {
		printf("Fork error.\n");
		exit(-1);
	}
	
	if(pid_1 == 0) {
		// Child-1
		// Close the first pipe output side
		close(fd_1[1]);
		
		// Read the number from the first pipe input side
		read(fd_1[0], &number, sizeof(int));
		printf("Child-1: received the number %d from the parent.\n", number);
		
		// Increment the number by 1
		number++;
		
		// Write the number to the second pipe output side
		write(fd_2[1], &number, sizeof(int));
		printf("Child-1: sent the number %d to the second child.\n", number);
	}
	else {
		// Parent
		// Fork to create the second child
		pid_2 = fork();
		
		if(pid_2 == -1) {
			printf("Fork error.\n");
			exit(-1);
		}
		
		if(pid_2 == 0) {
			// Child-2
			// Close the second pipe output side
			close(fd_2[1]);
			
			// Read the number from the second pipe input side
			read(fd_2[0], &number, sizeof(int));
			printf("Child-2: received the number %d from the first child.\n", number);
		}
		else {
			// Parent
			// Close the first pipe input side
			close(fd_1[0]);
			
			// Produce a random number between 1-100
			srand(time(NULL));
			number = rand() % 101;
			
			// Write the number to the first pipe output side
			write(fd_1[1], &number, sizeof(int));
			printf("Parent: sent the number %d to the first child.\n", number);
		}
	}
	
	// Wait for children
	wait(&child_status_1);
	wait(&child_status_2);
	
	return 0;
}
