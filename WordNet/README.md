## Programming Assignment 1: WordNet

[Assignment Specification](http://coursera.cs.princeton.edu/algs4/assignments/wordnet.html)

### WordNet is a semantic lexicon for the English language that is used extensively by computational linguists and cognitive scientists; for example, it was a key component in IBM's Watson. WordNet groups words into sets of synonyms called synsets and describes semantic relationships between them. One such relationship is the is-a relationship, which connects a hyponym (more specific synset) to a hypernym (more general synset). For example, animal is a hypernym of both bird and fish; bird is a hypernym of eagle, pigeon, and seagull.

__The WordNet digraph.__ Your first task is to build the wordnet digraph: each vertex v is an integer that represents a synset, and each directed edge v→w represents that w is a hypernym of v.

__Shortest ancestral path.__ An ancestral path between two vertices v and w in a digraph is a directed path from v to a common ancestor x, together with a directed path from w to the same ancestor x. A shortest ancestral path is an ancestral path of minimum total length.

__Outcast detection.__ Given a list of wordnet nouns A1, A2, ..., An, which noun is the least related to the others?