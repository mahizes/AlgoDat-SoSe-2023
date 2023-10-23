import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;


public class StringGenome {
	private String s = "";

	public void addNucleotide(char c) {
		if (c == 'A' || c == 'C' || c == 'G' || c == 'T')
			s = s + c;
		else
			throw new RuntimeException("Illegal nucleotide");
	}

	public char nucleotideAt(int i) {
		if (i < s.length())
			return s.charAt(i);
		else
			throw new RuntimeException("Genome out of bounds");
	}

	public int length() {
		return s.length();
	}

	public String toString() {
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		StringGenome i = (StringGenome) obj;
		return i.s.equals(this.s);
	}

	@Override
	public int hashCode() {
		return this.s.hashCode();
	}

}
