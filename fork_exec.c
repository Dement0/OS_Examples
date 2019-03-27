#include <stdio.h>
#include <unistd.h>
#include <wait.h>

int main() {
	int child_status;
	
	pid_t pid;
	
	// Fork
	pid = fork();
	
	if(pid==0) {
		// Fork is called in parent, so in child process pid is 0
		printf("Child process: ls -lh\n");
		
		// execl(const char *path, const char *arg0, ...);
		// The list of arguments must be terminated by a null-pointer
		execl("/bin/ls", "ls", "-l", "-h", (char *)0);
	}
	else {
		// Parent process called the fork, so pid is the ID of the child
		printf("Parent process: do nothing\n");
	}
	
	// Wait for any child process before terminating
	wait(&child_status);
}
