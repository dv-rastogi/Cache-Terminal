# Single-Level-Caches
Single level caches implemented with a user-friendly terminal interface.

CACHE TERMINAL

Documentation 

Project Description && Implementation Presuppositions

Single level Fully Associative Cache, Direct Mapped Cache, N-way Set Cache are implemented in a highly user-interactive terminal interface. The programming language of the aforementioned implementation is Java (based on OOPS). 

The implemented caches simulate to a very high extent the functionality with extensive showcase of oops modularity to model a real-world computer’s inbuilt cache. The programming done is very user-friendly, error-free and the application is very convenient to use and easy to get familiar with the inbuilt functionalities. 

Implementation presuppositions: -
 
Dependencies
Java (TM) SE Runtime Environment (build 13.0.1+9)

Usage/ Code Functionality
Initialization
Direct the program “DivyanshRastogi_2019464_FinalAssignment.java” into a suitable directory, and load your command prompt into the same directory and execute the program.


The following will be displayed on the terminal:

 
Commands

•	help
o	Get an overview of the functionality of the caches and various commands.
 

•	exit
o	Exit the cache terminal.
 

•	init
o	Initialize a cache, when a cache is initialized, the program simulates a directory behaviour.
 

•	info
o	Show initialized cache parameters, the bits required for each parameter and the address format breakdown a cache simulates for accessing a data value.
 

•	type
o	Show initialized cache type.
 

•	write
o	Write a data value at a specific address.
o	Input data must be an integer value. [1 Byte]
o	The input address is binary and shall not be inputted compulsorily as a 32bit address, as the program auto completes the address.
o	The program outputs a write hit or a write miss.
o	For FA cache and NW cache, LRU schemes are followed (when caches are full) in which the data block with the oldest timer count is evicted. As the caches are single level, evicted data block is reinitialized to 0.
o	For DM cache, in case of hit, the data is overwritten at the input address but in case of a miss, the block at the index calculated from the address is evicted and reinitialized to 0.
o	The written block gets allotted the program’s timer count.
 
 
 
 

•	read
o	Read the value at a specific address.
o	The input address is binary and shall not be inputted compulsorily as a 32bit address, as the program auto completes the address.
o	The program outputs a read hit or read miss.
o	In case of a read hit, the program outputs the respective cache’s formatted address with the data value and updates the timer count of the read block with the program’s timer count.
o	In case of read miss, the block is loaded(written) into the cache initialized with default value i.e. 0. The loaded block’s timer count is updated with the program’s timer count.
 
 
 

•	clear
o	Refresh your caches, reinitialize them by setting respective parameters to default.
o	Return to the root directory.
 
 
 

•	time
o	Know the value of cache timer which is used to time the block.
o	The cache timer is incremental and resets to 0 when cleared.
o	Forms the basis of our LRU policy.
 
•	print
o	Print the initialized cache.
o	Printing is done according to address formatting to aid to user convenience.
o	DM cache is initialized by default value and address while FA and NW are set to empty.
 
 












 
Error Handling/Reporting
Various error handling measures have been introduced in the program to handle unexpected inputs and user responses. Error reporting/handling in the program is showcased below: - 

•	For a command, that isn’t within the set of commands, a user gets the following output: -
 
 
•	For inputs such as ‘info’, ‘time’, ’type’, ‘write’, ‘read’, ‘print’ in which cache initialization is required, if the caches are not initialized, the program outputs the following: -
 
 
 

•	When a cache is initialized, the user is requested for cache parameters which must be powers of 2 and satisfying the condition of S = CL x B with a valid cache type. If the above isn’t followed, the program outputs the following: -
 

•	Once initialized a cache, if you request to initialize the cache again before clearing, the program outputs the following: -
 

•	The input format of the addresses while reading and writing aren’t expected to always be 32 bits. To aid to user convenience, the program autocompletes the address and shows the formatted 32-bit address: -
 


Code Overview of Cache functionalities
The programming language of choice was JAVA. Taking advantage of its modularity, caches and their functionalities are very distinctly represented. The program is menu driven with very user-friendly commands and functionalities.
 

The caches are required to be initiated first with cache parameters (in bytes).
 

After initiation, for reading and writing address inputs and data inputs are required. Presupposition states that an Integer takes one byte in our system. The addresses are binary indexed. After input, the address is formatted according to cache requirements.  
 

The fundamental units, block of an array and the mapping of each tag to a block have been atomized to classes to showcase their fundamental nature.

    

Separate classes for each of the caches have been implemented to modularize their functionality with a super main file class driving the program. A helper class has been created in order to extract helper functions such as binary and decimal conversion.
   
   

The writing and reading are simulated as in a real cache.
•	For the FA cache, first the cache is empty, the input address is broken down into tag and offset and then tags are compared individually and if found, it’s written/read else write/read miss it passed and the block is loaded into the cache. When the cache is full LRU policy is catered to which works in accordance with the timer set for the caches which increments on every read and write.

•	For the DM cache, first the cache is filled with default addresses starting from 0. For every input address, it’s broken down to tag, index and offset. Read and write miss only occur when at the current index, the tag doesn’t match, else a read/write hit is passed and the data is overwritten/read.

•	For NW cache, combination of policies is followed, first all sets are empty, then set is found according to policies of DM cache, while reading and writing inside a set is done by FA policies (LRU in a set).
