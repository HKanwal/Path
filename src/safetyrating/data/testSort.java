/**
 * 
 */
package safetyrating.data;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Pesara
 *
 */
public class testSort {

	private static CollisionDatabase data;
	private ArrayList<Entry> testEntry;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		data = new CollisionDatabase();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		data = null;
	}
	
	@Before
	public void setUp() throws Exception{
		testEntry = data.get("C_WTHR", 1);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		testEntry = null;
	}

	/**
	 * Test method for {@link safetyrating.data.Sort#sort(java.util.ArrayList, java.lang.String)}.
	 */
	@Test
	public void testSort() {
		Sort.sort(testEntry, "C_MNTH");
		boolean fold = Sort.isSorted(testEntry, "C_MNTH");
		assertTrue(fold);
	}

	/**
	 * Test method for {@link safetyrating.data.Sort#InsertionSort(java.util.ArrayList)}.
	 */
	@Test
	public void testInsertionSort() {
		Sort.InsertionSort(testEntry, "C_MNTH");
		boolean fold = Sort.isSorted(testEntry, "C_MNTH");
		assertTrue(fold);
	}

	/**
	 * Test method for {@link safetyrating.data.Sort#QuickSort(java.util.ArrayList)}.
	 */
	@Test
	public void testQuickSort() {
		Sort.QuickSort(testEntry, "C_MNTH");
		boolean fold = Sort.isSorted(testEntry, "C_MNTH");
		assertTrue(fold);
	}

	/**
	 * Test method for {@link safetyrating.data.Sort#MergeSort(java.util.ArrayList)}.
	 */
	@Test
	public void testMergeSort() {
		Sort.MergeSort(testEntry, "C_MNTH");
		boolean fold = Sort.isSorted(testEntry, "C_MNTH");
		assertTrue(fold);
	}

	/**
	 * Test method for {@link safetyrating.data.Sort#isSorted(java.util.ArrayList)}.
	 */
	@Test
	public void testIsSorted() {
		Sort.InsertionSort(testEntry, "C_MNTH");
		boolean fold = Sort.isSorted(testEntry, "C_MNTH");
		boolean foldb = true;
		int len = testEntry.size();
		for(int i =0;i<len-1;i++) {
			if(testEntry.get(i).C_MNTH() > testEntry.get(i+1).C_MNTH()) {
				foldb =false;
				break;
			}
		}
		assertTrue(fold && foldb);
	}

}
