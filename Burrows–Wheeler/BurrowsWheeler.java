import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static final int R = 256;
    
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        while (!BinaryStdIn.isEmpty()) {
            String string = BinaryStdIn.readString();

            CircularSuffixArray suffixArray = new CircularSuffixArray(string);

            for (int i = 0; i < suffixArray.length(); i++) {
                if (suffixArray.index(i) == 0) {
                    BinaryStdOut.write(i);
                    break;
                }
            }

            for (int i = 0; i < suffixArray.length(); i++) {
                int index = suffixArray.index(i) - 1;
                if (index < 0) index = string.length() - 1;
                
                BinaryStdOut.write(string.charAt(index));
            }
        }

        BinaryStdIn.close();
        BinaryStdOut.close();   
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        while (!BinaryStdIn.isEmpty()) {
            int first = BinaryStdIn.readInt();
            String string = BinaryStdIn.readString();

            char[] t = string.toCharArray();
            int[] next = new int[t.length];
            char[] sorted = new char[t.length];
            int[] counts = new int[R + 1];
            
            for (int i = 0; i < t.length; i++) {
                counts[t[i] + 1]++;
            }

            for (int r = 0; r < R; r++) {
                counts[r + 1] += counts[r];
            }

            for (int i = 0; i < t.length; i++) {
                sorted[counts[t[i]]++] = t[i];
            }

            counts = new int[R + 1];
            for (int i = 0; i < t.length; i++) {
                counts[sorted[i] + 1]++;
            }

            for (int r = 0; r < R; r++) {
                counts[r + 1] += counts[r];
            }

            for (int j = 0; j < t.length; j++) {
                int i = counts[t[j]]++;
                next[i] = j;
            }

            for (int i = 0, index = first; i < t.length; i++, index = next[index]) {
                BinaryStdOut.write(sorted[index]);
            }
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException();
    }
}