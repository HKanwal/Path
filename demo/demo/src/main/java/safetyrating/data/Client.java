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
			CollisionDatabase data = new CollisionDatabase(); //Collision Database line 48
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
