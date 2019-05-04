/*
 * First child creates a random number between 1-100
 * Sends it to parent via first pipe
 * Parent sends this number to the second child via a second pipe
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <wait.h>

int main() {

	// Define variables
	int fd[2];
	int fd_2[2];
	int number;
	pid_t pid;
	
	// Create the pipe
	pipe(fd);
	
	// Fork to create a child
	pid = fork();
	
	if(pid == -1) {
		printf("Fork error.\n");
		exit(-1);	
	}
	
	if(pid == 0) {
		// Child process
		// Close pipe input side
		close(fd[0]);
		
		// Produce a random number between 1-100
		srand(time(NULL));
		number = rand() % 101;
		
		// Write it to the pipe output side
		write(fd[1], (void *) &number, sizeof(int));		
		printf("Child-1: Number %d sent to the parent.\n", number);
	}
	else {
		// Parent process
		// Close pipe output side
		close(fd[1]);
		
		// Create another pipe
		pipe(fd_2);
		
		// Read the number from the first pipe input side
		read(fd[0], (void *) &number, sizeof(int));
		printf("Parent: Number %d received from first child.\n", number);
		
		wait(NULL);
		
		// Fork to create the second child
		pid = fork();
		
		if(pid == -1) {
			printf("Fork error.\n");
			exit(-1);
		}
		
		if(pid == 0) {
			// Second child process
			// Close the second pipe output side
			close(fd_2[1]);
			
			// Read the number from the second pipe input side
			read(fd_2[0], (void *) &number, sizeof(int));
			printf("Child-2: Number %d received from the parent.\n", number);
		}
		else {
			// Parent process
			// Close the second pipe input side
			close(fd_2[0]);
			
			// Write the number to the first pipe output side
			write(fd_2[1], (void *) &number, sizeof(int));
			printf("Parent: Number %d sent to the second child.\n", number);
			
			wait(NULL);
		}
		
		return 0;
	}

}
