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
 * hash table (symbol table).
 * 
 * @author Harkeerat Kanwal
 */
public class CollisionDatabase {
	/**
	 * The hash table itself. Uses separate chaining and linked lists for collision
	 * resolution.
	 */
	private Map<Key, EntryNode> hashTable = new HashMap<Key, EntryNode>();
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
		int smallestHashVal = 1000;
		int largestHashVal = 1000;
		while ((line = data.readLine()) != null) {
			if (lineCounter == 0) {
				lineCounter++;
				continue;
			}
			currentNode = new EntryNode(line);
			if (currentNode.V_YEAR() == -1 || currentNode.C_HOUR() == -1 || currentNode.C_WTHR() == -1) {
				continue;
			}
			currentKey = new Key(currentNode.V_YEAR(), currentNode.C_HOUR(), currentNode.C_WTHR());
			if (currentKey.hashCode() < smallestHashVal) {
				smallestHashVal = currentKey.hashCode();
			}
			if (currentKey.hashCode() > largestHashVal) {
				largestHashVal = currentKey.hashCode();
			}
			nodeInTable = hashTable.get(currentKey);
			if (nodeInTable != null) {
				currentNode.linkNext(nodeInTable);
			}
			System.out.println("Inserting key: " + currentKey);
			hashTable.put(currentKey, currentNode);
			lineCounter++;
		}
		System.out.println("Parsing complete.");
		System.out.println("Number of keys inserted: " + lineCounter);
		System.out.println("Smallest Hash Value: " + smallestHashVal);
			System.out.println("Largest Hash Value: " + largestHashVal);	
		data.close();
	}
	
	public EntryNode get(Key key) {
		return hashTable.get(key);
	}
}
