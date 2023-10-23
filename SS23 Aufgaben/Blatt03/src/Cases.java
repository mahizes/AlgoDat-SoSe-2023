/**
 * This is a heler class to switch the test cases in the PermutationTest class.
 * Any changes made to this class will be lost for testing. This specific file is not used for our tests.
 */
public class Cases {
	public PermutationVariation switchforTesting(int cases, int length) {
		PermutationVariation p=null;
		switch(cases) {
		case 0:
			p = new Permutation(length);
			System.out.println(0);
			break;
		case 1:
			p = new Permutation1(length);
			System.out.println(1);
			break;
		}
		return p;
	}

}

