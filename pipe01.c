/*
 * Creates 2 pipes for intercommunication between
 * parent and child processes
 */
 
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <stdlib.h>

int main() {

	// File descriptors for 2 pipes
	int fd_1[2];
	int fd_2[2];
	
	// Define variables
	pid_t pid;
	int numbers[] = {1, 2, 3, 5, 8, 13};
	int readBuffer[80];
	int calculateBuffer[80];
	
	// Create 2 pipes
	pipe(fd_1);
	pipe(fd_2);
	
	// Fork to create a child process
	pid = fork();
	
	if(pid == -1) {
		printf("fork error.\n");
		exit(-1);
	}
	
	if(pid == 0) {
		// Child process
		// Close first pipe input side
		close(fd_1[0]);
		
		// Close second pipe output side
		close(fd_2[1]);
		
		// Read the numbers from the second pipe input side
		printf("C: Reading the numbers from the parent...\n");
		read(fd_2[0], readBuffer, sizeof(calculateBuffer));
		
		// Multiply numbers by 2 in the readBuffer and put them in the calculateBuffer
		for(int j=0; j<6; j++) {
			calculateBuffer[j] = readBuffer[j] * 2;
		}
		
		// Write the result to the first pipe output side
		// int write(int handle, void *buffer, int nbyte);
		printf("C: Sending the result to the parent...\n");
		write(fd_1[1], calculateBuffer, sizeof(calculateBuffer));
		exit(0);
	}
	else {
		// Parent process
		// Close first pipe output side
		close(fd_1[1]);
		
		// Close second pipe input side
		close(fd_2[0]);
		
		// Write the numbers to the second pipe output side
		// int write(int handle, void *buffer, int nbyte);
		printf("P: Sending the numbers to the child...\n");
		write(fd_2[1], numbers, sizeof(numbers));
		
		// Read the result from first pipe input side
		// int read(int handle, void *buffer, int nbyte);
		printf("P: Reading the results from the child...\n");
		read(fd_1[0], readBuffer, sizeof(readBuffer));
		
		// Print the result
		printf("Numbers multiplied by 2:\n");
		for(int i=0; i<6; i++) {
			printf("%d ", readBuffer[i]);
		}
		printf("\n");
	}

  return 0;
}
