package safetyrating.data;


/**
 * Nodes of the linked list used in the CollisionDatabase hash table
 * for separate chaining collision resolution. Each node represents
 * an entry in the .csv file, and so a person involved in the accident.
 * This is a module storing the data of each entry and providing
 * getters.
 * 
 * @author Harkeerat Kanwal
 */
public class EntryNode {
	private String entry;
	private EntryNode next;
	
	/**
	 * Stores an entry from the data file. Doesn't parse it and only saves the line in String
	 * format to allow faster node creation when converting all data entries to nodes.
	 * 
	 * @param entryLine A line of data from the .csv file.
	 */
	public EntryNode(String entryLine) {
		entry = entryLine;
		next = null;
	}
	
	/**
	 * Creates a clone of the current node.
	 * @return A copy of the current node.
	 */
	public EntryNode clone() {
		return new EntryNode(entry);
	}
	
	/**
	 * Adds or changes the next node in the linked list.
	 * @param node The next EntryNode in the linked list.
	 */
	public void linkNext(EntryNode node) {
		next = node;
	}
	
	/**
	 * Append node to end of this linked list using recursion.
	 * @param node The node to append.
	 */
	public void append(EntryNode node) {
		if (next == null) {
			linkNext(node);
		}
		else {
			next.append(node);
		}
	}
	
	/**
	 * Getter for next node of linked list.
	 * @return The next node in the linked list.
	 */
	public EntryNode getNext() {
		return next;
	}
	
	/**
	 * Parses the String representation of this entry and finds the value of the field at given index.
	 * Unavailable data points returned as -1. Starting from 0, the corresponding attributes for each
	 * index are as follows:
	 * C_YEAR, C_MNTH, C_WDAY, C_HOUR, C_SEV, C_VEHS, C_CONF, C_RCFG, C_WTHR, C_RSUR, C_RALN,
	 * C_TRAF, V_ID, V_TYPE, V_YEAR, P_ID, P_SEX, P_AGE, P_PSN, P_ISEV, P_SAFE, P_USER, C_CASE
	 * 
	 * @param index The index of the field to retrieve.
	 * @return The value of the field as an int. If sex, M is 1 and F is 0, not specified is -1.
	 *   -1 is returned for unspecified entries.
	 */
	private int getField(int index) {
		int currentCol = 0;
		char currentChar;
		String field = "";
		// Loop through each character in this entry.
		for (int i = 0; i < entry.length(); i++) {
			currentChar = entry.charAt(i);
			// Commas represent end of current column in the .csv file format.
			if (currentChar == ',') {
				// Return value of current column because it is the requested field.
				if (currentCol == index) {
					if (field.contains("U") || field.contains("X") || field.contains("N") || field.contains("Q")) {
						return -1;
					}
					else if (field.equals("M") || field.equals("F")) {
						if (field.equals("M")) {
							return 1;
						}
						else if (field.equals("F")) {
							return 0;
						}
						else {
							return -1;
						}
					}
					else {
						return Integer.parseInt(field);
					}
				}
				// Not requested field. Move on to next column.
				else {
					currentCol++;
				}
			}
			// Found the requested field. Save its value to return when end of column reached.
			else if (currentCol == index) {
				field += currentChar;
			}
		}
		return -1;
	}
	
	/**
	 * Return String representation of this entry.
	 * @return The String representation of this entry. The same as its line in the .csv file.
	 */
	public String toString() {
		return entry;
	}
	
	/**
	 * Getter for column 1.
	 * @return Year.
	 */
	public int C_YEAR() {
		return getField(0);
	}
	
	/**
	 * Getter for column 2.
	 * @return Month.
	 */
	public int C_MNTH() {
		return getField(1);
	}
	
	/**
	 * Getter for column 3.
	 * @return Day of the week.
	 */
	public int C_WDAY() {
		return getField(2);
	}
	
	/**
	 * Getter for column 4.
	 * @return Hour of day.
	 */
	public int C_HOUR() {
		return getField(3);
	}
	
	/**
	 * Getter for column 5.
	 * @return Severity of collision.
	 */
	public int C_SEV() {
		return getField(4);
	}
	
	/**
	 * Getter for column 6.
	 * @return Number of vehicles involved.
	 */
	public int C_VEHS() {
		return getField(5);
	}
	
	/**
	 * Getter for column 7.
	 * @return Collision configuration.
	 */
	public int C_CONF() {
		return getField(6);
	}
	
	/**
	 * Getter for column 8.
	 * @return Road configuration.
	 */
	public int C_RCFG() {
		return getField(7);
	}
	
	/**
	 * Getter for column 9.
	 * @return Weather.
	 */
	public int C_WTHR() {
		return getField(8);
	}
	
	/**
	 * Getter for column 10.
	 * @return Road surface.
	 */
	public int C_RSUR() {
		return getField(9);
	}
	
	/**
	 * Getter for column 11.
	 * @return Road alignment.
	 */
	public int C_RALN() {
		return getField(10);
	}
	
	/**
	 * Getter for column 12.
	 * @return Traffic control.
	 */
	public int C_TRAF() {
		return getField(11);
	}
	
	/**
	 * Getter for column 13.
	 * @return Vehicle ID.
	 */
	public int V_ID() {
		return getField(12);
	}
	
	/**
	 * Getter for column 14.
	 * @return Vehicle type.
	 */
	public int V_TYPE() {
		return getField(13);
	}
	
	/**
	 * Getter for column 15.
	 * @return Vehicle model year.
	 */
	public int V_YEAR() {
		return getField(14);
	}
	
	/**
	 * Getter for column 16.
	 * @return Person ID.
	 */
	public int P_ID() {
		return getField(15);
	}
	
	/**
	 * Getter for column 17.
	 * @return Person sex.
	 */
	public int P_SEX() {
		return getField(16);
	}
	
	/**
	 * Getter for column 18.
	 * @return Person age.
	 */
	public int P_AGE() {
		return getField(17);
	}
	
	/**
	 * Getter for column 19.
	 * @return Person position.
	 */
	public int P_PSN() {
		return getField(18);
	}
	
	/**
	 * Getter for column 20.
	 * @return Person injury severity.
	 */
	public int P_ISEV() {
		return getField(19);
	}
	
	/**
	 * Getter for column 21.
	 * @return Person safety device used.
	 */
	public int P_SAFE() {
		return getField(20);
	}
	
	/**
	 * Getter for column 22.
	 * @return Road user class.
	 */
	public int P_USER() {
		return getField(21);
	}
	
	/**
	 * Getter for column 23.
	 * @return Collision case number.
	 */
	public int C_CASE() {
		return getField(22);
	}
	
	/**
	 * Getter for a column but by using String representation of the attribute (eg. "C_HOUR").
	 * 
	 * @param attr String representation of the name of the attribute.
	 * @return The value of the attribute. -1 if couldn't find the attribute or the value of the attribute
	 *   was -1.
	 */
	public int getAttr(String attr) {
		String[] attributes = {"C_YEAR", "C_MNTH", "C_WDAY", "C_HOUR", "C_SEV", "C_VEHS", "C_CONF", "C_RCFG", "C_WTHR",
				"C_RSUR", "C_RALN", "C_TRAF", "V_ID", "V_TYPE", "V_YEAR", "P_ID", "P_SEX", "P_AGE", "P_PSN", "P_ISEV",
				"P_SAFE", "P_USER", "C_CASE"};
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].equals(attr)) {
				return getField(i);
			}
		}
		return -1;
	}
}
