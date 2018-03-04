import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (true) {
            String line = null;
            try {
                line = StdIn.readString();
            } finally { if (line == null) break; }

            queue.enqueue(line);
        }

        Iterator<String> iterator = queue.iterator();

        while (count-- > 0) {
            StdOut.println(iterator.next());
        }
   }
}