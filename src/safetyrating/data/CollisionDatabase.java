package safetyrating.data;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import safetyrating.data.EntryNode;
import safetyrating.data.Key;


/**
 * Parses data from the collision data .csv file and provides interface
 * to access and interact with it. The data structure used is a 
 * hash table (symbol table).
 * 
 * @author Harkeerat Kanwal
 */
public class CollisionDatabase {
	/**
	 * The keys for this symbol table include the hour, weather and vehicle model year of
	 * collision (C_HOUR, C_WTHR and V_YEAR) in the form of a String with the format
	 * "C_HOUR/C_WTHR/V_YEAR". This array holds all unique keys. It is also the hash
	 * function, whereby each key's hash value is its index in this array.
	 */
	private Key[] keys = new Key[19824];
	
	/**
	 * The hash table itself. Uses separate chaining and linked lists for collision
	 * resolution.
	 */
	private EntryNode[] hashTable = new EntryNode[19824];
	private String dataFile = "NCDB_1999_to_2017.csv";
	
	/**
	 * In what order of Key attributes is the list currently sorted? Lower indices mean higher levels.
	 * For example: ["V_YEAR", "C_HOUR", "C_WTHR"] means keys are first sorted by V_YEAR, then each
	 * V_YEAR is sub-sorted by C_HOUR and so on.
	 */
	private String[] sortOrder = new String[3];
	
	/**
	 * Instantiate the database object. Go through .csv file once and fill hash table
	 * with its entries.
	 * 
	 * @throws IOException When unable to open or read the .csv file.
	 */
	public CollisionDatabase() throws IOException {
		BufferedReader data = new BufferedReader(new FileReader(dataFile));
		String line;
		initKeys();
		sortOrder[0] = "V_YEAR";
		sortOrder[1] = "C_HOUR";
		sortOrder[2] = "C_WTHR";
		EntryNode currentNode;
		Key currentKey;
		int hashValue;
		int lineCounter = 0;
		while ((line = data.readLine()) != null) {
			if (lineCounter == 0) {
				lineCounter++;
				continue;
			}
			currentNode = new EntryNode(line);
			currentKey = new Key(currentNode.V_YEAR(), currentNode.C_HOUR(), currentNode.C_WTHR());
			hashValue = hash(currentKey);
			if (hashValue == -1) {
				continue;
			}
			System.out.println("Entry Line: " + line);
			System.out.println("Hash value: " + hashValue);
			if (hashTable[hashValue] == null) {
				hashTable[hashValue] = currentNode;
			}
			else {
				currentNode.linkNext(hashTable[hashValue]);
				hashTable[hashValue] = currentNode;
			}
		}
		System.out.println("Parsing complete.");
		data.close();
	}
	
	/**
	 * Searches the dataset for entries whose specified attr has inputed value.
	 * 
	 * @param attr The attribute to search by.
	 * @param val Find entries whose attr is this value.
	 * @return A linked list of EntryNodes (cloned) that match search description.
	 */
	public EntryNode searchBy(String attr, int val) {
		EntryNode matches = null;
		for (int i = 0; i < keys.length; i++) {
			if (hashTable[i] == null) {
				continue;
			}
			if (attr.equals("V_YEAR") && keys[i].V_YEAR() == val) {
				if (matches == null) {
					matches = hashTable[i].clone();
				}
				else {
					matches.append(hashTable[i].clone());
				}
			}
			else if (attr.equals("C_HOUR") && keys[i].C_HOUR() == val) {
				if (matches == null) {
					matches = hashTable[i].clone();
				}
				else {
					matches.append(hashTable[i].clone());
				}
			}
			else if (attr.equals("C_WTHR") && keys[i].C_WTHR() == val) {
				if (matches == null) {
					matches = hashTable[i].clone();
				}
				else {
					matches.append(hashTable[i].clone());
				}
			}
			else {
				// Search all nodes in current chain.
				EntryNode currentNode = hashTable[i];
				EntryNode tmp = null;
				while (currentNode != null) {
					if (currentNode.getAttr(attr) == val) {
						if (matches == null) {
							matches = currentNode.clone();
							matches.linkNext(null);
						}
						else {
							tmp = matches;
							matches = currentNode.clone();
							matches.linkNext(tmp);
						}
					}
					currentNode = currentNode.getNext();
				}
			}
		}
		return matches;
	}
	
	/**
	 * The hash function.
	 * 
	 * @param key The key to hash.
	 * @return The hash value.
	 */
	private int hash(Key key) {
		return findKey(key, 0, keys.length-1);
	}
		
	/**
	 * Populates the keys array with all possible keys.
	 */
	private void initKeys() {
		int keyIndex = 0;
		for (int modelYear = 1901; modelYear <= 2018; modelYear++) {
			for (int hour = 0; hour <= 23; hour++) {
				for (int weather = 1; weather <= 7; weather++) {
					keys[keyIndex] = new Key(modelYear, hour, weather);
					keyIndex++;
				}
			}
		}
	}
	
	/**
	 * Finds the index in keys of the given key or -1 if not found. First uses binary search to
	 * locate a key with the same foremost sorted attribute (sortOrder[0]), then performs a linear
	 * search on the correct side for the exact key.
	 * 
	 * @param key The key to search for.
	 * @param lo The inclusively smallest index of keys in which to search.
	 * @param hi The inclusively highest index of keys in which to search.
	 * @return The index of the key in keys, or -1 if not found.
	 */
	private int findKey(Key key, int lo, int hi) {
		// Base case.
		if (lo > hi) {
			return -1;
		}
		// Calculate middle index.
		int mid = lo + (hi - lo)/2;
		int compareMid;
		if (sortOrder[0].equals("V_YEAR")) {
			compareMid = keys[mid].compareByYearTo(key);
		}
		else if (sortOrder[0].equals("C_HOUR")) {
			compareMid = keys[mid].compareByHourTo(key);
		}
		else if (sortOrder[0].equals("C_WTHR")) {
			compareMid = keys[mid].compareByWeatherTo(key);
		}
		else {
			return -1;
		}
		// Check if middle key matches.
		if (compareMid == 0) {
			// Binary search for sortOrder[0] complete. Now perform linear search for exact
			// key match.
			if (keys[mid].isEqual(key)) {
				return mid;
			}
			if (sortOrder[1].equals("V_YEAR")) {
				if (keys[mid].V_YEAR() < key.V_YEAR()) {
					return linearSearch(key, mid+1, hi, 1);
				}
				else {
					return linearSearch(key, lo, mid-1, -1);
				}
			}
			else if (sortOrder[1].equals("C_HOUR")) {
				if (keys[mid].C_HOUR() < key.C_HOUR()) {
					return linearSearch(key, mid+1, hi, 1);
				}
				else {
					return linearSearch(key, lo, mid-1, -1);
				}
			}
			else {
				if (keys[mid].C_WTHR() < key.C_WTHR()) {
					return linearSearch(key, mid+1, hi, 1);
				}
				else {
					return linearSearch(key, lo, mid-1, -1);
				}
			}
		}
		// If key less than mid, search left sub-array.
		if (compareMid > 0) {
			return findKey(key, lo, mid-1);
		}
		// Otherwise, search right sub-array.
		return findKey(key, mid+1, hi);
	}

	/**
	 * Performs a linear search of a sub-array of keys defined by lo and hi for the given key according to
	 * the given direction. 1 means from left to right. -1 means from right to left.
	 * 
	 * @param key To key to search for.
	 * @param lo The inclusively smallest index of keys in which to search.
	 * @param hi The inclusively highest index of keys in which to search.
	 * @param direction 1 means search from left to right. -1 means from right to left.
	 * @return The index at which the key was found. -1 if not found.
	 */
	private int linearSearch(Key key, int lo, int hi, int direction) {
		if (direction == 1) {
			for (int i = lo; i <= hi; i++) {
				if (keys[i].isEqual(key)) {
					return i;
				}
			}
			return -1;
		}
		else {
			for (int i = lo; i <= hi; i++) {
				if (keys[i].isEqual(key)) {
					return i;
				}
			}
			return -1;
		}
	}
	
}
