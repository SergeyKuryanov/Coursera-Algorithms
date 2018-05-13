import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] sequence = createSequence();

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int i = 0;

            for (i = 0; i < sequence.length; i++) {
                if (sequence[i] == c) break;
            }

            BinaryStdOut.write((char) i);
            sequence = moveToFront(sequence, c, i);
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] sequence = createSequence();

        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readChar();
            char c = sequence[i];
            BinaryStdOut.write(c);
            
            sequence = moveToFront(sequence, c, i);
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    private static char[] createSequence() {
        char[] sequence = new char[R];
        for (int i = 0; i < R; i++) {
            sequence[i] = (char) i;
        }

        return sequence;
    }

    private static char[] moveToFront(char[] sequence, char c, int i) {
        for (int j = i; j > 0; j--) {
            sequence[j] = sequence[j - 1];
        }

        sequence[0] = c;

        return sequence;
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException();
    }
}