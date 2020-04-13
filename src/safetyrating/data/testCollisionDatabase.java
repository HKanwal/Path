package safetyrating.data;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import safetyrating.data.CollisionDatabase;

/**
 * Testing for the CollisionDatabase class.
 * 
 * @author Harkeerat Kanwal
 */
public class testCollisionDatabase {
	private static CollisionDatabase data;
    
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		data = new CollisionDatabase();
	}

	@AfterClass
	public static void tearDownAfterClass() {
		data = null;
	}

	@Test
	public void testGetNumEntries() {
		assertTrue(data.getNumEntries() == 6772564);
	}

    @Test
    public void testGetTotalSize() {
        assertTrue(data.getTotalSize() == 40635378);
    }

    @Test
    public void testGetAttrs() {
		String[] attrs = data.getAttrs();
		String[] _attrs = new String[] {"C_MNTH", "C_WDAY", "C_HOUR", "C_WTHR", "C_RSUR", "V_TYPE"};
		assertTrue(attrs.length == _attrs.length);

		for (int i = 0; i < attrs.length; i++) {
			assertTrue(attrs[i].equals(_attrs[i]));
		}
	}

	@Test
	public void testGet1() {
		ArrayList<Entry> results = data.get("C_WTHR", 3);
		for (Entry result : results) {
			assertTrue(result.C_WTHR() == 3);
		}
	}

	@Test
	public void testGet2() {
		String[] queryAttrs = new String[] {"C_WTHR", "C_MNTH"};
		int[] queryVals = new int[] {1, 1};
		ArrayList<Entry> results = data.get(queryAttrs, queryVals);
		for (Entry result : results) {
			assertTrue(result.C_WTHR == 1 && result.C_MNTH == 2);
		}
	}

	@Test
	public void testCount1() {
		assertTrue(data.count("C_WHTR", 1) == data.get("C_WTHR", 1).size());
	}

	@Test
	public void testCount2() {
		String[] queryAttrs = new String[] {"C_WTHR", "C_MNTH"};
		int[] queryVals = new int[] {1, 1};
		assertTrue(data.count(queryAttrs, queryVals) == data.get(queryAttrs, queryVals).size());
	}
	
}
