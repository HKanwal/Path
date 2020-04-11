/**
 * Adding an initial sort method - not fully integrated
 */
package safetyrating.data;

import java.util.ArrayList;

/**
 * @author Pesara Amarasekera
 *
 */

/**
 * Alright pesera, here's what we need. Can you implement 
 * quicksort with the assumption that you are given an 
 * ArrayList of Entries where the entries are instances 
 * of the Entry class in the repo? You must additionally 
 * sort based on the attribute that is given eg. C_WTHR.
 * @author Pesara
 *
 */
public class Sort {
	
	
	//Modified compare function to adapt to Entry type
	//Added support for 3way quick, insertion and merge sort
	private static ArrayList<Entry> aux;
	private static String attribute;
	
	public static void sort(ArrayList<Entry> a, String s) {
		attribute = s;
		QuickSort(a);
	}
	
	/**
	 * Insertion sort implementation adapted to ArrayList
	 * @param a - Array list to sort
	 */
	public static void InsertionSort(ArrayList<Entry> a) {
		int n = a.size();
		for(int i = 1; i < n; i++) {
			for(int j=i;j>0 && less(a.get(i),a.get(j-1)); j--) {
				exchange(a,j,j-1);
			}
		}
	}
	
	/**
	 * Quick sort implementation adapted to ArrayList
	 * @param a  - Array list to sort
	 */
	public static void QuickSort(ArrayList<Entry> a) {
		quicksort(a, 0, a.size()-1);
	}
	
	//3-way quicksort implementation to reduce worst case run-time complexity
	private static void quicksort(ArrayList<Entry> a, int lo, int hi) {
		if (hi<=lo) return;
		int lt = lo, i = lo+1, gt = hi;
		Entry v = a.get(lo);
		while(i <= gt) {
			int cmp = compare(a.get(i),v);
			if		(cmp < 0) exchange(a, lt++, i++);
			else if (cmp > 0) exchange(a, i, gt--);
			else			  i++;
		}
		
		quicksort(a, lo, lt-1);
		quicksort(a,gt + 1,hi);
	}
	
	private static int compare(Entry a, Entry b) {
		int acomp = 0;
		int bcomp = 0;
		
		acomp = a.getAttr(attribute);
		bcomp = b.getAttr(attribute);
		
		if(acomp==bcomp) {
			return 0;
		}else if(acomp<bcomp) {
			return -1;
		}
		return 1;
//		if(attribute.contentEquals("C_YEAR")){
//			
//		}else if(attribute.contentEquals("C_MNTH")){
//			
//	    }else if(attribute.contentEquals("C_WDAY")){
//	    	
//	    }else if(attribute.contentEquals("C_HOUR")){
//	    	
//	    }else if(attribute.contentEquals("C_SEV")) {
//	    	
//	    }else if(attribute.contentEquals("C_VEHS")) {
//	    	
//	    }else if(attribute.contentEquals("C_CONF")) {
//	    	
//	    }else if(attribute.contentEquals("C_RCFG")) {
//	    	
//	    }else if(attribute.contentEquals("C_WTHR")) {
//	    	
//	    }else if(attribute.contentEquals("C_RSUR")) {
//	    	
//	    }else if(attribute.contentEquals("C_RALN")) {
//	    	
//	    }else if(attribute.contentEquals("C_TRAF")) {
//	    	
//	    }else if(attribute.contentEquals("V_ID")) {
//	    	
//	    }else if(attribute.contentEquals("V_TYPE")) {
//	    	
//	    }else if(attribute.contentEquals( "V_YEAR")) {
//	    	
//	    }else if(attribute.contentEquals( "V_YEAR")) {
//	    	
//	    }else if(attribute.contentEquals( "P_ID")) {
//	    	
//	    }else if(attribute.contentEquals( "P_SEX")) {
//	    	
//	    }else if(attribute.contentEquals( "P_AGE")) {
//	    	
//	    }else if(attribute.contentEquals( "P_PSN")) {
//	    	
//	    }else if(attribute.contentEquals( "P_ISEV")) {
//	    	
//	    }else if(attribute.contentEquals( "P_SAFE")) {
//	    	
//	    }else if(attribute.contentEquals( "P_USER")) {
//	    	
//	    }else if(attribute.contentEquals( "C_CASE")) {
//	    	
//	    }
	}
	
	/**
	 * Merge sort implementation adapted to ArrayList
	 * @param a - Array list to sort
	 */
	public static void MergeSort(ArrayList<Entry> a) {
		aux = new ArrayList<Entry>(a.size());
		mergesort(a,0,a.size()-1);
	}
	
	private static void mergesort(ArrayList<Entry> a, int lo, int hi) {
		if(hi <= lo) return;
		int mid = lo + (lo+hi)/2;
		mergesort(a,lo,mid);
		mergesort(a,mid+1,hi);
		merge(a,lo,mid,hi);
	}
	
	//Abstract in-place merge page 271
	private static void merge(ArrayList<Entry> a, int lo, int mid, int hi) {
		int i = lo,j = mid+1;
		
		for(int k=lo; k <= hi; k++) {
			aux.set(k,a.get(k)); 
		}
		
		for (int k = lo; k <= hi; k++) {
			if(i>mid)  					  a.set(k, a.get(j++));
			else if (j > hi) 			  a.set(k, a.get(i++));
			else if(less(aux.get(j), aux.get(i))) a.set(k, a.get(j++));
			else						  a.set(k, a.get(i++));
			
		}
	}

	public static boolean isSorted(ArrayList<Entry> a) {
		for (int i = 1; i<a.size();i++) {
			if(less(a.get(i),a.get(i-1))) return false;
		}
		return true;
	}
	
	private static void exchange(ArrayList<Entry> a,int c, int b) {
		Entry t = a.get(c);
		a.set(c,  a.get(b));
		a.set(b, t);
	}
	
	
	private static boolean less(Entry a, Entry b) {
		int s = compare(a,b);
		if(s<0) {
			return true;
		}
		return false;
	}
	
}
