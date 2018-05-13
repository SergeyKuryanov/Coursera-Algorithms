import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private final String string;
    private final Integer[] indices;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if  (s == null) {
            throw new java.lang.IllegalArgumentException();
        }

        this.string = s;

        Integer[] indices = new Integer[s.length()];
        for (Integer i = 0; i < indices.length; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, suffixOrder());
        this.indices = indices;
    }

    // length of s
    public int length() {
        return string.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= this.indices.length) {
            throw new java.lang.IllegalArgumentException();
        }

        return this.indices[i];
    }   

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray suffixArray = new CircularSuffixArray(args[0]);
        for (int i = 0; i < suffixArray.length(); i++) {
            StdOut.print(suffixArray.index(i) + " ");
        }

        StdOut.println("");
    }

    private Comparator<Integer> suffixOrder() {
        return new Comparator<Integer>() {            
            @Override
            public int compare(Integer first, Integer second) {
                for (int i = 0; i < string.length(); i++) {
                    int index1 = (first + i) % string.length();
                    int index2 = (second + i) % string.length();
                    int result = string.charAt(index1) - string.charAt(index2);
                    if (result != 0) { return result; }
                }

                return 0;
            }
        };
    }
 }