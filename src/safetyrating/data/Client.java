package safetyrating.data;

import java.io.IOException;


/**
 * Client to test and use CollisionDatabase.
 * 
 * @author Harkeerat Kanwal
 */
public class Client {
	public static void main(String[] args) {
		// Initialize database (parse data file).
		CollisionDatabase data = null;
		try {
			data = new CollisionDatabase();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		// Print some statistics upon parse completion.
		System.out.println("Parsing complete.");
		System.out.println("Number of entries: " + data.getNumEntries());
		System.out.println("Total size of hash maps: " + data.getTotalSize());

		QueryFactory factory = new QueryFactory(data);
		factory.start(); // Run CLI
	}

}
