<h1 align = "center"> CACHE TERMINAL </h1>

<h3> Project Description </h3>

> Single level Fully Associative Cache, Direct Mapped Cache, N-way Set Cache are implemented in a highly user-interactive terminal interface. The programming language of the aforementioned implementation is Java (based on OOPS).

> The implemented caches simulate to a very high extent the functionality with extensive showcase of oops modularity to model a real-world computer’s inbuilt cache. The programming done is very user-friendly, error-free and the application is very convenient to use and easy to get familiar with the inbuilt functionalities.

<h3> Implementation presuppositions </h3>
 
<ins> Dependencies </ins>

Java (TM) SE Runtime Environment (build 13.0.1+9)

<ins> Usage/ Code Functionality </ins>

> Direct the program “Main.java” into a suitable directory, and load your command prompt into the same directory and execute the program.

`java Main.java`

---
<h3> Commands </h3>
<dl>
<dt> help </dt>
<dd>- Get an overview of the functionality of the caches and various commands. </dd>
 
<dt> exit </dt>
<dd>- Exit the cache terminal. </dd>
 
<dt> init </dt>
<dd>- Initialize a cache, when a cache is initialized, the program simulates a directory behaviour. </dd>
 
<dt>	info </dt>
<dd>-	Show initialized cache parameters, the bits required for each parameter and the address format breakdown a cache simulates for accessing a data value. </dd>

<dt> type </dt>
<dd>- Show initialized cache type. </dd>

<dt> write </dt>
<dd>-	Write a data value at a specific address. </dd>
<dd>- Input data must be an integer value. [1 Byte] </dd>
<dd>-	The input address is binary and shall not be inputted compulsorily as a 32bit address, as the program auto completes the address. </dd>
<dd>- The program outputs a write hit or a write miss. </dd>
<dd>-	For FA cache and NW cache, LRU schemes are followed (when caches are full) in which the data block with the oldest timer count is evicted. As the caches are single level, evicted data block is reinitialized to 0. </dd>
<dd>- For DM cache, in case of hit, the data is overwritten at the input address but in case of a miss, the block at the index calculated from the address is evicted and reinitialized to 0. </dd>
<dd>- The written block gets allotted the program’s timer count. </dd>

<dt> read </dt> 
<dd>-	Read the value at a specific address. </dd>
<dd>-	The input address is binary and shall not be inputted compulsorily as a 32bit address, as the program auto completes the address. </dd>
<dd>-	The program outputs a read hit or read miss. </dd>
<dd>-	In case of a read hit, the program outputs the respective cache’s formatted address with the data value and updates the timer count of the read block with the program’s timer count. </dd>
<dd>-	In case of read miss, the block is loaded(written) into the cache initialized with default value i.e. 0. The loaded block’s timer count is updated with the program’s timer count. </dd>

<dt>	clear </dt> 
<dd>- Refresh your caches, reinitialize them by setting respective parameters to default. </dd>
<dd>- Return to the root directory. </dd>

<dt> time </dt>
<dd>- Know the value of cache timer which is used to time the block. </dd>
<dd>- The cache timer is incremental and resets to 0 when cleared. </dd>
<dd>- Forms the basis of our LRU policy. </dd>
 
<dt>	print </dt>
<dd>- Print the initialized cache. </dd>
<dd>- Printing is done according to address formatting to aid to user convenience. </dd>
<dd>- DM cache is initialized by default value and address while FA and NW are set to empty. </dd>
 
 </dl>

---
**For Information on overview of Cache functionalities and Error Handling in the project, Please refer the docs (pdf)!** :books:
