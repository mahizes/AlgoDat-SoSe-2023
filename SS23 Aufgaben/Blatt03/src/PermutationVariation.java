import java.util.LinkedList;
import java.util.Arrays;

/**
 * Superclass to a few Permutation classes, which calculate the derangement of a permutation.
 * Parts of them work, others do not. The goal is to write tests, to test, 
 * which ones are correct and which are not.
 * @author Vera Röhr
 * @version 1.0
 * @since   2019-01-23
 *
 */
public abstract class PermutationVariation {
	public int [] original;
	public LinkedList<int[]> allDerangements;
	/**
	 * Builds the original permutation.
	 * @param N size of the permutation.
	 */
	public PermutationVariation(int N) {
        this.original = new int[N];
	}
	/**
	 * @param candidate list for the derangement backtracking.
	 */
	public abstract void backtracking(LinkedList<Integer> candidate);
	
	/**
	 * Calls the backtracking with an empty list.
	 */
	public abstract void derangements();
	

    @Override
    public String toString() {
        String str = "";
        for (int[] perm : allDerangements) {
            str += Arrays.toString(perm) + "\n";
        }
        return str;
    }


}

