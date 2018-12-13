# Software Integrity Tester
The Software Integrity Tester (SIT) is a static code analysis tool that is able to detect several common software vulnerabilities in Java, C++, and ADA code. 


## Getting Started
### Compatible File Types
The SIT recognizes files of the following types
* .ada
* .adb
* .c
* .cc
* .cpp
* .cxx
* .c++
* .java

## Installation

### Clone
* Clone this repo to your local machine using https://github.com/RyanBeebe1/SWIntegrity.git

## Running the SIT
This version of the SIT runs strictly from the command line. It recognizes valid file paths that are defined within it's current directory. 
* "../" is recognized as the parent directory of the current directory
* "./" is recognized as the current directory
### Flags
* -a - Scans for only Ada vulnerabilities
* -c - Scans for only C++ vulnerabilities
* -j - Scans for only Java vulnerabilities
* -all - Scans for all vulnerabilities
* -help - Prints usage instructions
* -r - Recursively searches given directory name for specified language vulnerabilities
* -v - Verbose mode, displays each action that is performed in the terminal window

### Examples
* java SIT -help
* java SIT -c helloWorld.cpp
* java SIT -c -j helloworld.cpp helloworld.java
* java SIT -all -v
* java SIT -j ../TestFiles -r

## Documentation

## Vulnerabilities
|                 Vulnerability                                  | Java  | C++ | Ada |
|:---------------------------------------------------------------|:-----:|:---:|:---:|
| SQL Injection                                                  |   X   |  X  |     |
| OS Command Injection                                           |   X   |     |     |
| Classic Buffer Overflow                                        |       |  X  |     |
| Expired Pointer Dereference                                    |       |  X  |  X  |
| Improper Limitation of a Pathname to a Restricted Directory    |   X   |  X  |  X  |
| Integer Overflow/Wraparound                                    |   X   |  X  |  X  |
| Use of Externally-Controlled Format String                     |       |  X  |     |
| Use of Wrong Operator in String Comparison                     |   X   |     |     |
| Use of Incorrect Operator                                      |       |  X  |     |
| Use of sizeof on Pointer Type                                  |   X   |     |     |
| Use of Hard-coded Credentials                                  |   X   |     |     |
| URL Redirection to Untrusted Site                              |   X   |     |     |
| Use of Potentially Dangerous Function                          |       |  X  |     |
| Use of Broken or Risky Cryptographic Algorithm                 |       |  X  |     |
| Improper use of Clone Method without call to Super             |   X   |     |     |
| Use of a One-Way Hash without Salt                             |   X   |  X  |     |
| Reliance on Untrusted Inputs in a Security Decision            |   X   |  X  |     | 
| Use of finalize() Method without super.finalize()              |   X   |     |     |

## Tests

## Contributing

## Credits
### SIT 3.0 Team
* [Sean Spencer](https://github.com/spencers1/) - Product Owner
* [Joseph Whittier](https//github.com/joeywhitt/) - Scrum Lord
* [Ryan Beebe](https://github.com/RyanBeebe1/) - Developer
* [Manoj George](https://github.com/manojmpg114/) - Developer
* [Joseph McIlvaine](https://github.com/jomac4694/) - Developer
* [Eric Stephens](https://github.com/ericwstephens3/) - Developer

### SIT 2.0 Team





