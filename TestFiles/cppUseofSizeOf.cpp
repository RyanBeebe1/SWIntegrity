include<iostream>
using namespace std; 

// main function - 
// where the execution of program begins 
int main() 
{
    char *pass;
	//Should return 1
    sizeof pass;
	printf("Use of sizeof on pointer correctly: %d\n", sizeof(*pass));
    sizeof(pass);
	//Should return 8
	printf("Use of sizeof on pointer incorrectly: %d\n", sizeof(pass));
	return 0; 
} 
