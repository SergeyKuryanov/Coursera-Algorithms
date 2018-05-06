## Programming Assignment 4: Boggle

[Assignment Specification](http://coursera.cs.princeton.edu/algs4/assignments/boggle.html)

### Write a program to play the word game Boggle®.

The Boggle game. Boggle is a word game designed by Allan Turoff and distributed by Hasbro. It involves a board made up of 16 cubic dice, where each die has a letter printed on each of its 6 sides. At the beginning of the game, the 16 dice are shaken and randomly distributed into a 4-by-4 tray, with only the top sides of the dice visible. The players compete to accumulate points by building valid words from the dice, according to these rules:

- A valid word must be composed by following a sequence of adjacent dice—two dice are adjacent if they are horizontal, vertical, or diagonal neighbors.
- A valid word can use each die at most once.
- A valid word must contain at least 3 letters.
- A valid word must be in the dictionary (which typically does not contain proper nouns).

__Performance.__ If you choose your data structures and algorithms judiciously, your program can preprocess the dictionary and find all valid words in a random Hasbro board (or even a random 10-by-10 board) in a fraction of a second. To stress test the performance of your implementation, create one BoggleSolver object (from a given dictionary); then, repeatedly generate and solve random Hasbro boards. How many random Hasbro boards can you solve per second? For full credit, your program must be able to solve thousands of random Hasbro boards per second. The goal on this assignment is raw speed—for example, it's fine to use 10× more memory if the program is 10× faster.