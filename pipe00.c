#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <stdlib.h>
#include <string.h>

int main() {

	// Reading file descriptor in fd[0]
	// Writing file descriptor in fd[1]
  int fd[2];
  
  pid_t pid;
  char string[] = "Hello\n";
  char readBuffer[80];
  
  // pipe() function should be called before fork() function thus child process can see that as well
  pipe(fd);
  
  pid = fork();
  
  if(pid==0) {
  	// Child process
  	// Close pipe input side
  	printf("Child: closing pipe input...\n");
  	close(fd[0]);
  	
  	// Send string to the output side
  	printf("Child: sending string %s", string);
  	
  	// int write(int handle, void *buffer, int nbyte);
  	write(fd[1], string, (strlen(string) + 1));
  	
  	exit(0);
  }
  else {
 		// Parent process
 		// Close pipe output side
 		printf("Parent: closing pipe output...\n"); 
 		close(fd[1]);
 		
 		// Read string from the pipe input
 		printf("Parent: receiving...\n");
 		
 		// int read(int handle, void *buffer, int nbyte);
 		read(fd[0], readBuffer, sizeof(readBuffer));
 		printf("Parent: received string %s", readBuffer);
  }
  
  return 0;

}
