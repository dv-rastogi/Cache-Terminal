/*
AUTHOR: DIVYANSH RASTOGI [2019464]
*/

import java.util.*;
import java.io.*;
import java.lang.*;

public class DivyanshRastogi_2019464_FinalAssignment extends helper{
	static boolean initialized = false; //if a cache has been initialized
	static int size; //for universal cache
	static int cacheLines; //for universal cache
	static int blockSize; //for universal cache
	static Scanner sc; 
	static int cacheType; //determining the type of cache
	static String cacheName = "$"; //cacheName 
	static int timer = 1; //clock simulated
	static faCache fa; //faCache
	static dmCache dm; //dmCache
	static nwCache nw; //nwCache
	static void clear(){ //Refresh the whole caches
		initialized = false;
		cacheType = 0;
		size = 0;
		cacheLines = 0;
		blockSize = 0;
		cacheName = "$";
		timer = 1;
		fa = new faCache();
		dm = new dmCache();
		nw = new nwCache();
	}
	static boolean isPow2(int num){
		int i=1;
		while(i<=32){
			if((1<<i)==num)
				return true;
			++i;
		}
		return false;
	}
	static void chooseCache(){ //choose a cache
		System.out.print("Enter (1) FA cache (2) DM cache (3) N-way cache : ");
		cacheType = sc.nextInt();
		if(cacheType==1) cacheName = "FACache";
		else if(cacheType==2) cacheName = "DMCache";
		else cacheName = "NWayCache";
	}
	static void getParam(){ //get universal cache parameters
		System.out.print("Enter Size of Cache: ");
		size = sc.nextInt();
		System.out.print("Enter Number of CacheLines: ");
		cacheLines = sc.nextInt();
		System.out.print("Enter blockSize: ");
		blockSize = sc.nextInt();
	}
	static void knowParam(){ //show universal cache parameters
		System.out.print("Size of Cache: "+size);
		System.out.print("\nSize of Cache Bits: "+log2(size));
		System.out.print("\nCache Lines: "+cacheLines);
		System.out.print("\nCache Lines Bits: "+log2(cacheLines));
		System.out.print("\nBlock Size: "+blockSize);	
		System.out.println("\nBlock Size Bits: "+log2(blockSize));
		if(cacheType==3){
			System.out.print("\nN for NW Cache: "+nw.n);
			System.out.println("\nN for NW Cache Bits: "+nw.nbits);
		}
		System.out.println();

		switch(cacheType){
			case 1: //facache
				System.out.println("ADDRESS FORMAT: <TAG OFFSET>");
				break;
			case 2: //dmcache
				System.out.println("ADDRESS FORMAT: <TAG INDEX OFFSET>");
				break;
			case 3: //nwcache
				System.out.println("ADDRESS FORMAT: <TAG SET_INDEX OFFSET>");
				break;
		}
	}
	static void help(){ //show commands
		System.out.println("TOPIC");
		System.out.println("\tCache Terminal");
		System.out.println("\nDESCRIPTION");
		System.out.println("\t*Simulate Single Level Fully Associative Cache, Direct Mapped Cache, N-way set Cache");
		System.out.println("\t*Caches are simulated on a 32 bit machine");
		System.out.println("\t*An Integer is stored as a data value [size = 1byte]");
		System.out.println("\t*Value at all viable addresses are initialized to 0 by default");
		System.out.println("\t*LRU scheme is followed");
		System.out.println("\t*Addresses are Binary Indexed");
		System.out.println("\t*Caches Parameters are in bytes");
		System.out.println("\t*During eviction, data loss is simulated in single leveled cache");
		System.out.println("\nCOMMANDS");
		System.out.println("\thelp: get a list of commands");
		System.out.println("\texit: exit the terminal");
		System.out.println("\tinit: initiate a cache");
		System.out.println("\tinfo: show cache parameters and address formatting");
		System.out.println("\ttype: show chosen cache type");
		System.out.println("\twrite: read the value at a given address");
		System.out.println("\tread: read the value at a given address");
		System.out.println("\tclear: clear memories of all caches");
		System.out.println("\tprint: print the whole cache");
		System.out.println("\ttime: know the value of cache timer");
	}
	static void startDesign(){
		System.out.println("\n==== CACHE TERMINAL ====");
		System.out.println("Enter 'help' to view the list of commands");
	}
	public static void main(String[] args){
		sc = new Scanner(System.in);
		startDesign();
		boolean exit = false;
		String input;
		while(!exit){
			if(cacheName.equals("$")) System.out.print("\n$> "); //simulate a directory behaviour
			else System.out.print("\n$>"+cacheName+"> "); 
			input = sc.next();
			switch(input){
				case "help":
					help();
					break;
				case "exit":
					exit = true;
					System.out.println("Bye!");
					System.out.println();
					break;
				case "init":
					if(!initialized){
						chooseCache();
						getParam();
						boolean valid1 = (size == cacheLines*blockSize);
						boolean valid2 = (cacheType==1)||(cacheType==2)||(cacheType==3);
						boolean valid3 = isPow2(size)&&isPow2(cacheLines)&&isPow2(blockSize);
						if(!(valid1&&valid2&&valid3)){ //error handling
							clear();
							System.out.println("\nInvalid input format! Reinitialization required!");
							break;
						}
						if(cacheType==1) fa = new faCache(size,cacheLines,blockSize);
						else if(cacheType==2) dm = new dmCache(size,cacheLines,blockSize);
						else nw = new nwCache(size,cacheLines,blockSize);
						initialized = true;
						System.out.println("\n"+cacheName+" has been initialized!");
						if(cacheType==2) System.out.println("All values in the DM Cache have been initalized to 0!");
					}
					else
						System.out.println("Invalid request, clear the cache first!");
					break;
				case "info":
					if(initialized)	knowParam();
					else System.out.println("Invalid request, caches are not initialized!");
					break;
				case "type":
					if(cacheType==0) System.out.println("Invalid request, choose a cache first!");
					else{
						if(cacheType==1) System.out.println("FA Cache");
						else if(cacheType==2) System.out.println("DM Cache");
						else System.out.println("N-way set Cache");
					}
					break;
				case "write":
					if(cacheType==0){
						System.out.println("Invalid request, caches are not initialized!");
						break;
					}
					System.out.print("Enter address : ");
					String addressWrite = sc.next();
					System.out.print("Enter data : ");
					int dataWrite = sc.nextInt();
					while(addressWrite.length()!=32) //32 bit addressing
						addressWrite = "0"+addressWrite;
					System.out.println("Formatted 32-bit address: <b"+addressWrite+">"); System.out.println();
					switch(cacheType){
						case 1:
							fa.write(addressWrite,dataWrite,timer,false);
							break;
						case 2:
							dm.write(addressWrite,dataWrite,timer,false);
							break;
						case 3:
							nw.write(addressWrite,dataWrite,timer,false);
							break;
					}
					++timer; //increment the clock
					System.out.println("Data written!");
					break;
				case "read":
					if(cacheType==0){
						System.out.println("Invalid request, caches are not initialized!");
						break;
					}
					System.out.print("Enter address : ");
					String addressRead = sc.next();
					while(addressRead.length()!=32) //32 bit addressing
						addressRead = "0"+addressRead;
					System.out.println("Formatted 32-bit address: <b"+addressRead+">"); System.out.println();
					switch(cacheType){
						case 1:
							fa.read(addressRead,timer);
							break;
						case 2:
							dm.read(addressRead,timer);
							break;
						case 3:
							nw.read(addressRead,timer);
							break;
					}
					++timer; //increment the clock
					break;
				case "clear":
					clear();
					System.out.println("Caches Cleared!");
					break;
				case "print":
					if(cacheType==0){
						System.out.println("Invalid request, caches are not initialized!");
						break;
					}
					switch(cacheType){
						case 1:
							fa.show();
							break;
						case 2:
							dm.show();
							break;
						case 3:
							nw.show();
							break;
					}
					break;
				case "time":
					if(cacheType==0){
						System.out.println("Invalid request, caches are not initialized!");
						break;
					}
					System.out.println("Cache Timer Count: "+timer);
					break;
				default:
					System.out.println("Invalid command!");
			}
		}
	}
}

class helper{
	static int log2(int num){ //helper functions
		double x = Math.log((double)num);
		double y = Math.log(2);
		return (int)(x/y);
	}
	static int toDeci(String num){ //helper functions
		return Integer.parseInt(num,2);
	}
	static String toBin(int num, int len){ //helper functions
		String first = Integer.toBinaryString(num);
		while(first.length() != len)
			first = "0"+first;
		return first;
	}
}

class tagBlock{
	String tag; //simulating tag array
	block blk; //simulating block/data array
	tagBlock(String tag, block blk){
		this.tag = tag;
		this.blk = blk;
	}
}

class block{
	int time; //a block stores its time
	int size; //a block's size
	int[] data; //data array
	block(int time, int size){
		this.time = time;
		this.size = size;
		this.data = new int[size];
	}
}

class faCache extends helper{
	int s,cl,b;
	int sbits,clbits,bbits;
	ArrayList<tagBlock> table;
	faCache(){
		//refreshing a cache
		this.s = 0;
		this.cl = 0;
		this.b = 0;
		this.sbits = 0;
		this.clbits = 0;
		this.bbits = 0;
		this.table = new ArrayList<>();
	}
	faCache(int s,int cl, int b){
		this.s = s;
		this.cl = cl;
		this.b = b;
		//bits for respective parameters
		this.sbits = log2(s);
		this.clbits = log2(cl);
		this.bbits = log2(b);
		this.table = new ArrayList<>();
	}
	void write(String address, int val, int time, boolean isRead){ //FA CACHE
		String tag = address.substring(0,32-bbits);
		int offset = toDeci(address.substring(32-bbits,32));
		boolean found = false;
		for(tagBlock x: table){
			if (tag.equals(x.tag)){
				System.out.println("Write hit!");
				found = true;
				x.blk.data[offset] = val;
				x.blk.time = time;
				return;
			}
		}
		if(table.size()<cl){ //no replacement needed
			if(!isRead)
				System.out.println("Write miss!");
			else
				System.out.println("Given address is initialized with value 0!");
			table.add(new tagBlock(tag,new block(time,b)));
			table.get(table.size()-1).blk.data[offset] = val;
		}
		else{
			//LRU
			int idx = -1;
			int i=0;
			int minTime = (int)1e9;
			for(tagBlock x: table){
				if(x.blk.time < minTime){
					minTime = x.blk.time;
					idx = i;
				}
				++i;
			}
			if(!isRead) System.out.println("Write miss!");
			else System.out.println("Given address is initialized with value 0!");
			System.out.println("Cache Full!");
			System.out.println("Data block with tag <b"+table.get(idx).tag+"> has been evicted! [LRU]");
			table.remove(idx);
			table.add(new tagBlock(tag,new block(time,b)));
			table.get(table.size()-1).blk.data[offset] = val;
		}
	}
	void read(String address, int time){
		String tag = address.substring(0,32-bbits);
		int offset = toDeci(address.substring(32-bbits,32));
		boolean found = false;
		for(tagBlock x: table){
			if(tag.equals(x.tag)){
				found = true;
				System.out.println("Read hit!");
				//formatted address printed tag,offset
				System.out.println("Address : <b"+address.substring(0,32-bbits)+" "+address.substring(32-bbits,32)+"> || Value : "+x.blk.data[offset]);
				x.blk.time = time;
			}
		}
		if(!found){
			System.out.println("Read Miss!"); //if read miss, load into cache with default value
			this.write(address,0,time,true);
		}
	}
	void show(){
		System.out.println("\n=x=x=x=  FA CACHE  =x=x=x=");
		if(table.size()==0){
			System.out.println("Empty cache!");
			return;
		}
		System.out.println();
		for(tagBlock x: table){
			String opp = "TAG: <b"+x.tag+">";
			for(int i=0;i<opp.length();++i) System.out.print("="); System.out.println();
			System.out.println(opp);
			System.out.println("BLOCK TIME COUNT: "+x.blk.time);
			System.out.println("Offset \t Value");
			for(int i=0;i<x.blk.size;++i)
				System.out.println("|| <" +toBin(i,bbits) + "> ||  " + x.blk.data[i]);
			for(int i=0;i<opp.length();++i) System.out.print("="); System.out.println();
			System.out.println();
		}
	}
}

class dmCache extends helper{
	int s,cl,b;
	int sbits,clbits,bbits;
	ArrayList<tagBlock> table;
	dmCache(){
		//refreshing a cache
		this.s = 0;
		this.cl = 0;
		this.b = 0;
		this.sbits = 0;
		this.clbits = 0;
		this.bbits = 0;
		this.table = new ArrayList<>();
	}
	dmCache(int s,int cl, int b){
		this.s = s;
		this.cl = cl;
		this.b = b;
		//bits for respective parameters
		this.sbits = log2(s);
		this.clbits = log2(cl);
		this.bbits = log2(b);
		this.table = new ArrayList<>();
		this.init(); //initializing with default value
	}
	void init(){
		for(int i=0;i<cl;++i)
			table.add(new tagBlock(toBin(i,32-bbits), new block(0,b)));
	}

	void write(String address, int val, int time, boolean isRead){ //DM cache
		String tag = address.substring(0,32-bbits);
		int offset = toDeci(address.substring(32-bbits,32));
		int idx = toDeci(tag.substring(tag.length()-clbits,tag.length()));
		if (tag.equals(table.get(idx).tag)){
			System.out.println("Write hit!");
			table.get(idx).blk.data[offset] = val;
			table.get(idx).blk.time = time;
		}
		else{
			if(!isRead) System.out.println("Write miss!");
			else System.out.println("Given address is initialized with value 0!");
			table.set(idx, new tagBlock(tag, new block(time,b)));
			table.get(idx).blk.data[offset] = val;
		}
	}

	void read(String address, int time){
		String tag = address.substring(0,32-bbits);
		int offset = toDeci(address.substring(32-bbits,32));
		int idx = toDeci(tag.substring(tag.length()-clbits,tag.length()));
		if (tag.equals(table.get(idx).tag)){
			System.out.println("Read hit!");
			//formatted address printed tag,index,offset
			System.out.println("Address : <b"+tag.substring(0,tag.length()-clbits)+" "+tag.substring(tag.length()-clbits,tag.length())+" "+address.substring(32-bbits,32)+"> || Value : "+table.get(idx).blk.data[offset]);
			table.get(idx).blk.time = time;
		}
		else{
			System.out.println("Read miss!"); //if read miss, load into cache with default value
			this.write(address,0,time,true);
		}
	}
	void show(){
		System.out.println("\n=x=x=x=  DM CACHE  =x=x=x=");
		System.out.println();
		int idx = 0;
		for(tagBlock x: table){
			String opp = "TAG: <b"+x.tag.substring(0,x.tag.length()-clbits)+">";
			for(int i=0;i<opp.length();++i) System.out.print("="); System.out.println();
			System.out.println("INDEX: "+toBin(idx,clbits));
			System.out.println(opp);
			System.out.println("BLOCK TIME COUNT: "+x.blk.time);
			System.out.println("Offset \t Value");
			for(int i=0;i<x.blk.size;++i)
				System.out.println("|| <" +toBin(i,bbits) + "> ||  " + x.blk.data[i]);
			for(int i=0;i<opp.length();++i) System.out.print("="); System.out.println();
			System.out.println();
			++idx;
		}
	}
}

class nwCache extends helper{
	int s,cl,b,n;
	int sbits,clbits,bbits,nbits;
	ArrayList<sets> table;
	nwCache(){
		//refreshing a cache
		this.n = 0;
		this.s = 0;
		this.cl = 0;
		this.b = 0;
		this.sbits = 0;
		this.clbits = 0;
		this.bbits = 0;
		this.nbits = 0;
		this.table = new ArrayList<>();
	}
	nwCache(int s,int cl, int b){
		Scanner scNW = new Scanner(System.in);
		System.out.print("Enter N: ");
		this.n = scNW.nextInt();
		this.s = s;
		this.cl = cl;
		this.b = b;
		//bits for respective parameters
		this.sbits = log2(s);
		this.clbits = log2(cl);
		this.bbits = log2(b);
		this.nbits = log2(n);
		this.table = new ArrayList<>();
		this.init(); //initializing with default value
	}
	void init(){
		for(int i=0;i<(cl/n);++i)
			table.add(new sets(n));
	}

	void write(String address, int val, int time, boolean isRead){ //NW CACHE
		String tag = address.substring(0,32-bbits);
		int offset = toDeci(address.substring(32-bbits,32));
		table.get(toDeci(tag.substring(tag.length()-log2((cl/n)),tag.length()))).write(address,val,time,isRead,bbits,b,offset,log2(cl/n));
	}

	void read(String address, int time){
		String tag = address.substring(0,32-bbits);
		int offset = toDeci(address.substring(32-bbits,32));
		table.get(toDeci(tag.substring(tag.length()-log2((cl/n)),tag.length()))).read(tag,offset,time,address,bbits,b,log2(cl/n));
	}
	void show(){
		System.out.println("\n=x=x=x=  N-Way Set CACHE  =x=x=x=");
		System.out.println("[ N = "+n+" ]");
		System.out.println();
		int scnt = 0;
		for(sets y: table){
			String setstr = "SET: "+toBin(scnt,log2((cl/n)));
			for(int i=0;i<setstr.length();++i) System.out.print("+"); System.out.println();
			System.out.println(setstr+"\n");
			if(y.table.size() == 0) System.out.println("Empty Set!");
			else{
				for(tagBlock x: y.table){
					String opp = "TAG: <b"+x.tag.substring(0,x.tag.length()-log2((cl/n)))+">";
					for(int i=0;i<opp.length();++i) System.out.print("="); System.out.println();
					System.out.println(opp);
					System.out.println("BLOCK TIME COUNT: "+x.blk.time);
					System.out.println("Offset \t Value");
					for(int i=0;i<x.blk.size;++i)
						System.out.println("|| <" +toBin(i,bbits) + "> ||  " + x.blk.data[i]);
					for(int i=0;i<opp.length();++i) System.out.print("="); System.out.println();
					System.out.println();
				}
			}
			for(int i=0;i<setstr.length();++i) System.out.print("+"); System.out.println(); System.out.println();
			scnt+=1;
		}
	}
}

class sets{ //each set inside n-way cache
	int size;
	ArrayList<tagBlock> table;
	sets(int size){
		this.size = size;
		this.table = new ArrayList<>();
	}

	void write(String address, int val, int time, boolean isRead, int bbits, int b, int offset, int log2cln){ //Writing in NW Set
		String tag = address.substring(0,32-bbits);
		boolean found = false;
		for(tagBlock x: table){
			if(tag.equals(x.tag)){
				System.out.println("Write hit!");
				found = true;
				x.blk.data[offset] = val;
				x.blk.time = time;
				return;
			}
		}
		if(table.size() < size){
			if(!isRead) System.out.println("Write miss!");
			else System.out.println("Given address is initialized with value 0!");
			table.add(new tagBlock(tag,new block(time,b)));
			table.get(table.size()-1).blk.data[offset] = val;
		}
		else{
			//LRU in a set
			int idx = -1;
			int i=0;
			int minTime = (int)1e9;
			for(tagBlock x: table){
				if(x.blk.time < minTime){
					minTime = x.blk.time;
					idx = i;
				}
				++i;
			}
			if(!isRead) System.out.println("Write miss!");
			else System.out.println("Given address is initialized with value 0!");
			System.out.println("Cache Full!");
			System.out.println("Data block with tag <b"+table.get(idx).tag.substring(0,table.get(idx).tag.length()-log2cln)+"> has been evicted! [LRU in SET]");
			table.remove(idx);
			table.add(new tagBlock(tag,new block(time,b)));
			table.get(table.size()-1).blk.data[offset] = val;
		}
	}

	void read(String tag, int offset, int time, String address, int bbits, int b, int log2cln){
		boolean found = false;
		for(tagBlock x: table){
			if(tag.equals(x.tag)){
				found = true;
				System.out.println("Read hit!");
				//formatted address printed tag,setno,offset
				System.out.println("Address : <b"+tag.substring(0,tag.length()-log2cln)+" "+tag.substring(tag.length()-log2cln,tag.length())+" "+address.substring(address.length()-bbits,address.length())+"> || Value : "+x.blk.data[offset]);
				x.blk.time = time;
			}
		}
		if(!found){
			System.out.println("Read miss!"); //if read miss, load into cache with default value
			this.write(address,0,time,true,bbits,b,offset,log2cln);
		}
	}
}
