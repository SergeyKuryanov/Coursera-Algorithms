import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.SET;
import java.util.HashMap;

public class WordNet {
    private final HashMap<String, SET<Integer>> nouns;
    private final HashMap<Integer, SET<String>> synsets;
    private final Digraph dag;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        checkArguments(synsets, hypernyms);

        this.nouns = new HashMap<String, SET<Integer>>();
        this.synsets = new HashMap<Integer, SET<String>>();
        parseSynsets(readAllLines(synsets));

        this.dag = new Digraph(this.synsets.keySet().size());
        parseHypernyms(readAllLines(hypernyms));

        this.sap = new SAP(this.dag);

        checkRoot();
        checkCycle();
    }
 
    private void checkRoot() {
        int roots = 0;
        for (int i = 0; i < this.dag.V(); i++) {
            if (this.dag.outdegree(i) == 0) {
                roots++;
            }
        }

        if (roots != 1) {
            throw new IllegalArgumentException();
        }
    }

    private void checkCycle() {
        DirectedCycle directedCycle = new DirectedCycle(this.dag);

        if (directedCycle.hasCycle()) {
            throw new IllegalArgumentException();
        }
    }

    private void parseSynsets(String[] lines) {
        for (String line : lines) {
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            SET<String> synset = new SET<String>();

            for (String noun : fields[1].split(" ")) {
                synset.add(noun);

                SET<Integer> knownIds = this.nouns.get(noun);
                if (knownIds == null) {
                    knownIds = new SET<Integer>();
                    this.nouns.put(noun, knownIds);
                }
                knownIds.add(id);
            }

            this.synsets.put(id, synset);
        }
    }

    private void parseHypernyms(String[] lines) {
        for (String line : lines) {
            String[] fields = line.split(",");
            int synsetId = Integer.parseInt(fields[0]);

            for (int i = 1; i < fields.length; i++) {
                int hypernymId = Integer.parseInt(fields[i]);
                dag.addEdge(synsetId, hypernymId);
            }
        }
    }

    private String[] readAllLines(String filename) {
        In in = new In(filename);
        String[] lines = in.readAllLines();
        in.close();
        return lines;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.nouns.keySet();
    }
 
    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        checkArgument(word);

        return this.nouns.get(word) != null;
    }
 
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        checkArguments(nounA, nounB);

        SET<Integer> aId = this.nouns.get(nounA);
        SET<Integer> bId = this.nouns.get(nounB);

        checkArguments(aId, bId);

        return this.sap.length(aId, bId);
    }
 
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        checkArguments(nounA, nounB);

        SET<Integer> aId = this.nouns.get(nounA);
        SET<Integer> bId = this.nouns.get(nounB);

        checkArguments(aId, bId);

        int ancestor = this.sap.ancestor(aId, bId);
        return setToString(this.synsets.get(ancestor));
    }

    private String setToString(SET<String> set) {
        String result = "";
        for (String element : set) {
            result += " " + element;
        }

        return result;
    }

    private void checkArgument(String argument) {
        if (argument == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkArguments(String argument1, String argument2) {
        if (argument1 == null || argument2 == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkArguments(SET<Integer> argument1, SET<Integer> argument2) {
        if (argument1 == null || argument2 == null) {
            throw new IllegalArgumentException();
        }
    }
 }