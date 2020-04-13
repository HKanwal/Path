/**
 * Adding an initial sort method - not fully integrated
 */
package safetyrating.data;

import java.util.ArrayList;

/**
 * @author Pesara Amarasekera
 * Revised April 12, 2020
 */

public class Sort {
	
	
	//Modified compare function to adapt to Entry type
	//Added support for 3way quick, insertion and merge sort
	private static ArrayList<Entry> aux;
	private static String attribute;
	
	//default sort
	public static void sort(ArrayList<Entry> a, String s) {
		QuickSort(a,s);
	}
	
	/**
	 * Insertion sort implementation adapted to ArrayList
	 * @param a - Array list to sort
	 */
	public static void InsertionSort(ArrayList<Entry> a, String s) {
		attribute = s;
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
	public static void QuickSort(ArrayList<Entry> a, String s) {
		attribute = s;
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
	
	//compare helper method
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
	}
	
	/**
	 * Merge sort implementation adapted to ArrayList
	 * @param a - Array list to sort
	 */
	public static void MergeSort(ArrayList<Entry> a,  String s) {
		attribute = s;
		aux = new ArrayList<Entry>(a.size());
		
		for(int i = 0; i< a.size();i++) {
			aux.add(null);
		}
		mergesort(a,0,a.size()-1);
	}
	
	//mergesort
	private static void mergesort(ArrayList<Entry> a, int lo, int hi) {
		if(hi <= lo) return;
		int mid = lo + (hi-lo)/2;
		mergesort(a,lo,mid);
		mergesort(a,mid+1,hi);
		merge(a,lo,mid,hi);
	}
	
	//Abstract in-place merge
	private static void merge(ArrayList<Entry> a, int lo, int mid, int hi) {
		int i = lo,j = mid+1;
		
		for(int k=lo; k <= hi; k++) {
			aux.set(k,a.get(k)); 
		}
		
		for (int k = lo; k <= hi; k++) {
			if(i>mid)  					  a.set(k, aux.get(j++));
			else if (j > hi) 			  a.set(k, aux.get(i++));
			else if(less(aux.get(j), aux.get(i))) a.set(k, aux.get(j++));
			else						  a.set(k, aux.get(i++));
			
		}
	}

	//Makes sure that an arrayList is sorted
	public static boolean isSorted(ArrayList<Entry> a, String s) {
		attribute = s;
		for (int i = 1; i<a.size();i++) {
			if(less(a.get(i),a.get(i-1))) return false;
		}
		return true;
	}
	
	/**
	 * Exchanges entries in an ArrayList
	 */
	private static void exchange(ArrayList<Entry> a,int c, int b) {
		Entry t = a.get(c);
		a.set(c,  a.get(b));
		a.set(b, t);
	}
	
	/**
	 * 
	 * @param a entry to compare
	 * @param b entry to compare
	 * @return true if a is less than b otherwise false
	 */
	private static boolean less(Entry a, Entry b) {
		int s = compare(a,b);
		if(s<0) {
			return true;
		}
		return false;
	}
	
}
