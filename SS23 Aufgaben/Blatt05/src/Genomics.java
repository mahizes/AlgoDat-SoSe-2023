public class Genomics {

    
    /**
     * @param strang the String that is being sequenced (e.g. CAGTCCAGTCAGTC)
     * @param dictionary a dictionary of words (e.g. {AGT, CA, CAG, GTC, TC, TCA, TCC})
     * @return number of possible ways to compose strang using words from the dictionary
     */
    public static long optBottomUp(String strang, String[] dictionary)
    {
        int sequenceLength = strang.length();
        long[] numCompositions = new long[sequenceLength + 1];
        numCompositions[sequenceLength] = 1;  // base case: one way to form an empty string

        // Iterate from the end of the sequence to the beginning
        for (int currentIndex = sequenceLength - 1; currentIndex >= 0; currentIndex--) {
            // Check each word in the dictionary
            for (String word : dictionary) {
                // If the sequence starts with the current word at the current index, add the number of ways to form the remaining substring to the count
                if (strang.startsWith(word, currentIndex)) {
                    numCompositions[currentIndex] += numCompositions[currentIndex + word.length()];
                }
            }
        }

        // Return the number of ways to form the entire sequence
        return numCompositions[0];
    }

    


    public static void main(String[] args)
    {
    }
}

