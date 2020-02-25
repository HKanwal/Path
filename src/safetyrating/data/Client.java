package safetyrating.data;

import java.io.IOException;
import safetyrating.data.CollisionDatabase;


/**
 * Client to test CollisionDatabase.
 * 
 * @author Harkeerat Kanwal
 */
public class Client {

	public static void main(String[] args) {
		try {
			CollisionDatabase data = new CollisionDatabase();
			EntryNode matches = data.searchBy("C_YEAR", 2001);
			EntryNode currentNode = matches;
			System.out.println("Searching for C_YEAR = 00");
			while (currentNode != null) {
				System.out.println("Found: " + currentNode);
				currentNode = currentNode.getNext();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
