#include <stdio.h>
#include <unistd.h>
#include <wait.h>

int main() {
  int child_status;

  pid_t pid;
  printf("main program is %d\n", (int)getpid());

  pid = fork();

  if(pid==0) {
    // Fork is called in parent, so child process pid is 0
    printf("Child process\n");
    printf("C: child ID is %d\n", (int)getpid());
    printf("C: child ID of the child is %d\n", (int)pid);
  }
  else {
    // Parent called the fork, so pid is the ID of the child
    printf("Parent process\n");
    printf("P: parent ID is %d\n", (int)getpid());
    printf("P: child ID is %d\n", (int)pid);
  }

  // Wait for the child to terminate first
  wait(&child_status);

  return 0;
}
