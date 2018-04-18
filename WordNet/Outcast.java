import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int outcaseDistance = 0;
        String outcast = nouns[0];

        for (String noun : nouns) {
            int distance = distance(noun, nouns);

            if (distance > outcaseDistance) {
                outcaseDistance = distance;
                outcast = noun;
            }
        }

        return outcast;
    }

    private int distance(String noun, String[] nouns) {
        int distance = 0;

        for (int i = 0; i < nouns.length; i++) {
            distance += this.wordNet.distance(noun, nouns[i]);
        }

        return distance;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
 }