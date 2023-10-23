import java.util.LinkedList;
import java.util.List;
/**
 * Calculate the derangement of a permutation.
 * Parts of this class might work, others might not. The goal is to write tests, to test, 
 * which ones are correct and which are not.
 * @author Vera Röhr
 * @version 1.0
 * @since   2019-01-23
 *
 */
public class Permutation1 extends PermutationVariation {

	public Permutation1(int N) {
		super(N);
        for (int i = 0; i < N; i++) {
        	this.original[i] = i + 1;
        }
        this.allDerangements=new LinkedList<int[]>();
	}
	
    public void derangements() {
        backtracking(new LinkedList<Integer>());
    }

    public void backtracking(LinkedList<Integer> candidate) {
    	int[] o=this.original;
        if (candidate.size() == o.length) {
        	int[] finalist=new int[o.length];
        	for(int i=0;i<o.length;i++)
        		finalist[i]=candidate.get(i);
        	this.allDerangements.add(finalist);
            }
        else {
            for (int i = 0; i < o.length; i++) {
                if (!candidate.contains(o[i]) && i != candidate.size()) {
                    candidate.addLast(o[i]);
                }
            }
        }
    }

}

