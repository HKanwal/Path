package safetyrating.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.FileReader;
import java.io.BufferedReader;


/**
 * Parses data from the collision data .csv file and provides interface
 * to access and interact with it. The data structure used is a 
 * hash table (symbol table) using separate chaining.
 * 
 * @author Harkeerat Kanwal
 */
public class CollisionDatabase {
	private Map<String, Map<Integer, ArrayList<Entry>>> attrHashMaps = new HashMap<>();
	private String[] attributes = new String[] {"C_MNTH", "C_WDAY", "C_HOUR", "C_WTHR", "C_RSUR", "V_TYPE"};
	private int numEntries;
	private int totalSize;
	
	/**
	 * The path of the .csv database file.
	 */
	private String dataFile = "NCDB_1999_to_2017.csv";
	
	/**
	 * Instantiate the database object. Go through .csv file once and fill hash tables
	 * with its entries.
	 * 
	 * @throws IOException When unable to open or read the .csv file.
	 */
	public CollisionDatabase() throws IOException {
		BufferedReader data = new BufferedReader(new FileReader(dataFile));
		String line;
		Entry currentEntry;
		int lineCounter = 0;
		int totalSize = 0;
		
		initHashMaps();
		
		// Reads all lines of data-set.
		while ((line = data.readLine()) != null) {
			// Skip first line (headers).
			if (lineCounter == 0) {
				lineCounter++;
				continue;
			}
			
			// Create entry from current data line.
			currentEntry = new Entry(line);
			
			// Insert entry into corresponding hash maps.
			String currentAttr;
			int attrVal;
			Map<Integer, ArrayList<Entry>> currentMap;
			
			for (int i = 0; i < attributes.length; i++) {
				currentAttr = attributes[i];
				attrVal = currentEntry.getAttr(currentAttr);
				currentMap = attrHashMaps.get(currentAttr);
				currentMap.putIfAbsent(attrVal, new ArrayList<Entry>());
				currentMap.get(attrVal).add(currentEntry);
				System.out.println("Inserted key " + lineCounter + " into hash map for " + currentAttr);
				totalSize++;
			}
			
			
			// Statistics.
			lineCounter++;
			if (lineCounter > 100000) {
				break;
			}
		}
		data.close();

		numEntries = lineCounter;
		this.totalSize = totalSize;
	}
	
	/**
	 * Initialize the hash maps by creating one for each relevant attribute in the data set.
	 */
	private void initHashMaps() {
		Map<Integer, ArrayList<Entry>> hashMap;
		for (int i = 0; i < attributes.length; i++) {
			hashMap = new HashMap<>();
			attrHashMaps.put(attributes[i], hashMap);
		}
	}
	
	public int getNumEntries() {
		return numEntries;
	}
	
	public int getTotalSize() {
		return totalSize;
	}

	public String[] getAttrs() {
		// Preserve encapsulation.
		return attributes.clone();
	}
	
	/**
	 * Get entries whose given attribute matches given value. Eg: C_MNTH is 5.
	 * 
	 * @param attr The String attribute.
	 * @param val The value of the attribute as an int.
	 * @return An ArrayList of entries that match the query.
	 */
	public ArrayList<Entry> get(String attr, int val) {
		return get(attr).get(val);
	}

	/**
	 * Get the hashmap corresponding to the given attribute.
	 * They keys of the hashmap will be the possible values the attribute can take.
	 * 
	 * @param attr The attribute to query.
	 * @return A hashmap corresponding to the given attr whose keys are the possible
	 *   values the attribute can take and correspond to ArrayLists of the entries with that
	 *   value for the given attribute.
	 */
	public Map<Integer, ArrayList<Entry>> get(String attr) {
		return attrHashMaps.get(attr);
	}
	
	/**
	 * Get all entries whose attributes have given values. For example, all entries (collisions)
	 * that occurred in January (C_MNTH = 1) and during rainy weather (C_WTHR = 3).
	 * 
	 * @param attrs Array of names of attributes. For example: ["C_MNTH", "C_WTHR"]
	 * @param vals Array of corresponding values of the attributes. For example: [1, 3]
	 * @return An ArrayList of entries matching given attribute values.
	 */
	public ArrayList<Entry> get(String[] attrs, int[] vals) {
		ArrayList<Entry>[] results = new ArrayList[attrs.length];
		ArrayList<Entry> filtered = new ArrayList<>();
		int smallest = 0;
		
		// Get results from individual attributes and put in an array.
		// For example: [ArrayList of matching C_MNTH, ArrayList of matching C_WTHR]
		for (int i = 0; i < attrs.length; i++) {
			results[i] = get(attrs[i], vals[i]);
			if (results[i].size() < results[smallest].size()) {
				smallest = i;
			}
		}
		
		// Loop through smallest ArrayList and collate entries that match all given attribute values.
		for (int i = 0; i < results[smallest].size(); i++) {
			for (int j = 0; j < attrs.length; j++) {
				if (results[smallest].get(i).getAttr(attrs[j]) == vals[j]) {
					filtered.add(results[smallest].get(i));
				}
			}
		}
		return filtered;
	}
	
	/**
	 * Count the number of collisions with the given attribute being equal to the given value.
	 * 
	 * @param attr The attribute to query.
	 * @param val The value the attribute must have.
	 * @return An int. The count of the number of matching collisions in the dataset.
	 */
	public int count(String attr, int val) {
		return get(attr, val).size();
	}
	
	/**
	 * Count the number of collision with the given attributes being equal to the given values.
	 * 
	 * @param attrs The attributes to query.
	 * @param vals The values the attributes must have.
	 * @return An int. The count of the number of matching collisions in the dataset.
	 */
	public int count(String[] attrs, int[] vals) {
		return get(attrs, vals).size();
	}

	/**
	 * Perform a query and get the percentage of the total collisions that the results of the query embody.
	 * 
	 * @param attrs The attributes of the query.
	 * @param vals The corresponding values of the attributes.
	 * @return The percentage of collisions that match the given query.
	 */
	public double percentage(String[] attrs, int[] vals) {
		ArrayList<Entry> results = get(attrs, vals);
		return 100*Double.valueOf(results.size())/Double.valueOf(numEntries);
	}
}
