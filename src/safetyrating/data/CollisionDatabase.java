package safetyrating.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.FileReader;
import java.io.BufferedReader;
import safetyrating.data.EntryNode;
import safetyrating.data.Key;


/**
 * Parses data from the collision data .csv file and provides interface
 * to access and interact with it. The data structure used is a 
 * hash table (symbol table) using seperate chaining.
 * 
 * @author Harkeerat Kanwal
 */
public class CollisionDatabase {
	/**
	 * The hash table itself. Uses separate chaining and linked lists for collision
	 * resolution.
	 */
	private Map<Key, EntryNode> hashTable = new HashMap<Key, EntryNode>();
	
	/**
	 * The path of the .csv database file.
	 */
	private String dataFile = "NCDB_1999_to_2017.csv";
	
	/**
	 * Instantiate the database object. Go through .csv file once and fill hash table
	 * with its entries.
	 * 
	 * @throws IOException When unable to open or read the .csv file.
	 */
	public CollisionDatabase() throws IOException {
		BufferedReader data = new BufferedReader(new FileReader(dataFile));
		String line;
		EntryNode currentNode;
		EntryNode nodeInTable;
		Key currentKey;
		int lineCounter = 0;
		
		// Statistics.
		int smallestHashVal = 1000;
		int largestHashVal = 1000;
		
		// Reads all lines of data-set.
		while ((line = data.readLine()) != null) {
			// Skip first line (headers).
			if (lineCounter == 0) {
				lineCounter++;
				continue;
			}
			
			// Create node from current data line.
			currentNode = new EntryNode(line);
			
			// Ignore entries with unspecified V_YEAR, C_HOUR or C_WTHR.
			if (currentNode.V_YEAR() == -1 || currentNode.C_HOUR() == -1 || currentNode.C_WTHR() == -1) {
				continue;
			}
			
			// Create key and add node to linked list in hash table.
			currentKey = new Key(currentNode.V_YEAR(), currentNode.C_HOUR(), currentNode.C_WTHR());
			nodeInTable = hashTable.get(currentKey);
			if (nodeInTable != null) {
				currentNode.linkNext(nodeInTable);
			}
			System.out.println("Inserting key: " + currentKey);
			hashTable.put(currentKey, currentNode);
			
			// Statistics.
			lineCounter++;
			if (currentKey.hashCode() < smallestHashVal) {
				smallestHashVal = currentKey.hashCode();
			}
			if (currentKey.hashCode() > largestHashVal) {
				largestHashVal = currentKey.hashCode();
			}
		}
		System.out.println("Parsing complete.");
		System.out.println("Number of keys inserted: " + lineCounter);
		System.out.println("Smallest Hash Value: " + smallestHashVal);
			System.out.println("Largest Hash Value: " + largestHashVal);	
		data.close();
	}

	/**
	 * Get an entry (linked list node) from the hash table by key.
	 * 
	 * @param key The key.
	 * @return An EntryNode. The first node of the linked list in requested index location
	 *   of hash table.
	 */
	public EntryNode get(Key key) {
		return hashTable.get(key);
	}
	
	/**
	 * Get a linked list of entries in hash table by attribute and value (refer to NCDB_Data_Dictionary.docx).
	 * If requested attribute is not part of key information, will take linear time to search (must check all entries).
	 * Admissible attributes are: C_YEAR, C_MNTH, C_WDAY, C_HOUR, C_SEV, C_VEHS, C_CONF, C_RCFG, C_WTHR, C_RSUR, C_RALN,
	 * C_TRAF, V_ID, V_TYPE, V_YEAR, P_ID, P_SEX, P_AGE, P_PSN, P_ISEV, P_SAFE, P_USER, C_CASE
	 */ // TODO
//	public EntryNode getByAttr(String attr, int val) {
//		if () {
//			
//		}
//	}
}
