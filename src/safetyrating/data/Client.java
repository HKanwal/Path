package safetyrating.data;

import java.io.IOException;


/**
 * Client to test CollisionDatabase.
 * 
 * @author Harkeerat Kanwal
 */
public class Client {

	public static void main(String[] args) {
		CollisionDatabase data = null;
		try {
			data = new CollisionDatabase();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Parsing complete.");
		System.out.println("Number of entries: " + data.getNumEntries());
		System.out.println("Total size of hash maps: " + data.getTotalSize());

		QueryFactory factory = new QueryFactory(data);
		factory.start();
	}

}
