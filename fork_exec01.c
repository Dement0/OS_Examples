/*
 * A parent process creates 2 child processes.
 * First child process asks the user for a letter (character).
 * First child process passes this information to the parent process using a pipe.
 * Pipe is a blocking operation. So I don't have to do wait().
 * Parent process passes this information to the second child process.
 * After receiving this letter, the second child process appends this letter
 * using `exec()` to the `ls` command.
 * Ex: ls -a or ls -l
 */

#include <stdio.h>
#include <unistd.h>
#include <wait.h>
#include <sys/types.h>
#include <stdlib.h>
#include <string.h>

// TODO: Create the file desciptors for the pipe

int main() {
	
	// Create 2 child processes
	int child_status_1;
	int child_status_2;
	
	pid_t pid;
	printf("Main program ID: %d\n", (int)getpid());
	
	// fork to create the first child process
	pid = fork();
	
	// If it's the parent process, then create the second child process
	if(pid > 0) {
		// Create the second child
		pid = fork();
		
		// If parent
		if(pid > 0) {
			printf("Parent process, ID: %d\n", (int)getpid());
		}
		
		// Else if child
		else if(pid == 0) {
			printf("Second child process, ID: %d\n", (int)getpid());
		}
		
		// Else error
		else {
			printf("Fork error.");
		}
	}
	
	// Else if child
	else if(pid == 0) {
		printf("First child process, ID: %d\n", (int)getpid());
	}
	
	// Else error
	else {
		printf("Fork error.");
	}
	
	// Wait for any child process before terminating
	wait(&child_status_1);
	wait(&child_status_2);
	
	return 0;
}









