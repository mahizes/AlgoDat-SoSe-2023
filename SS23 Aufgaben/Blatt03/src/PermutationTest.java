import java.util.LinkedList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PermutationTest {
	PermutationVariation p1;
	PermutationVariation p2;
	public int n1;
	public int n2;
	int cases=1;
	
	void initialize() {
		n1=4;
		n2=6;
		Cases c= new Cases();
		p1= c.switchforTesting(cases, n1);
		p2= c.switchforTesting(cases, n2);
	}
	

	@Test
	void testPermutation() {
		initialize();
		assertEquals(n1, p1.original.length, "P1: Length of Original Array Doesn't Match n1");
		assertArrayEquals(sort(p1.original), p1.original, "P1: Original Array is Not Sorted");
		assertNotNull(p1.allDerangements, "P1: allDerangements is Null! Should be initialized.");
		assertTrue(p1.allDerangements.isEmpty(), "P1: allDerangements Should be Empty Before derangements() Call");
	
		assertEquals(n2, p2.original.length, "P2: Length of Original Array Doesn't Match n2");
		assertArrayEquals(sort(p2.original), p2.original, "P2: Original Array is Not Sorted");
		assertNotNull(p2.allDerangements, "P2: allDerangements is Null! Should be initialized.");
		assertTrue(p2.allDerangements.isEmpty(), "P2: allDerangements Should be Empty Before derangements() Call");
	}

	@Test
	void testDerangements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		p1.derangements();
		p2.derangements();
	
		assertEquals(9, p1.allDerangements.size(), "P1: Derangement Count Incorrect. Expected 9.");
		for (int[] derangement : p1.allDerangements) {
			assertFixedPointFree(derangement, p1.original);
		}
	
		assertEquals(265, p2.allDerangements.size(), "P2: Derangement Count Incorrect. Expected 265.");
		for (int[] derangement : p2.allDerangements) {
			assertFixedPointFree(derangement, p2.original);
		}
	}
	
	@Test
	void testsameElements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		p1.derangements();
		p2.derangements();
	
		assertFalse(p1.allDerangements.isEmpty(), "P1: No Derangements Found, allDerangements is Empty!");
		for (int[] derangement : p1.allDerangements) {
			assertArrayEquals(sort(derangement), p1.original, "P1: Element mismatch in derangement");
		}
	
		assertFalse(p2.allDerangements.isEmpty(), "P2: No Derangements Found, allDerangements is Empty!");
		for (int[] derangement : p2.allDerangements) {
			assertArrayEquals(sort(derangement), p2.original, "P2: Element mismatch in derangement");
		}
	}

	private int[] sort(int[] array) {
		int[] sortedArray = array.clone();
		Arrays.sort(sortedArray);
		return sortedArray;
	}

	private void assertFixedPointFree(int[] permutation, int[] original) {
		for (int i = 0; i < permutation.length; i++) {
			assertFalse(permutation[i] == original[i], "Detected Fixed Point in Permutation. Expected Fixed-Point-Free!");
		}
	}
	
	void setCases(int c) {
		this.cases=c;
	}
	
	public void fixConstructor() {
		//in case there is something wrong with the constructor
		p1.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n1;i++)
			p1.original[i]=2*i+1;
		
		p2.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n2;i++)
			p2.original[i]=i+1;
	}
}


