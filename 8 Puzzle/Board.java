import java.util.LinkedList;
import java.util.List;

public class Board {
    private final int[][] blocks;
    private final int hamming;
    private final int manhattan;
    private List<Board> neighbors;

    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];

        int hammingScore = 0;
        int manhattanScore = 0;

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int block = blocks[i][j];
                this.blocks[i][j] = block;

                if (block == 0) continue;

                if (block != i * dimension() + j + 1) hammingScore++;

                block -= 1;

                int blockI = block / dimension();
                int blockJ = block - blockI * dimension();
                int distance = Math.abs(i - blockI) + Math.abs(j - blockJ);

                manhattanScore += distance;
            }
        }

        this.hamming = hammingScore;
        this.manhattan = manhattanScore;
    }

    public int dimension() {
        return blocks[0].length;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        int[][] twinBlocks = blocksCopy();

        if (blocks[0][0] != 0 && blocks[0][1] != 0) {
            twinBlocks[0][0] = blocks[0][1];
            twinBlocks[0][1] = blocks[0][0];
        } else {
            twinBlocks[1][0] = blocks[1][1];
            twinBlocks[1][1] = blocks[1][0];
        }

        return new Board(twinBlocks);
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;

        Board anotherBoard = (Board) y;

        if (dimension() != anotherBoard.dimension()) return false;
        
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != anotherBoard.blocks[i][j]) return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() { 
        if (neighbors != null) return neighbors;

        neighbors  = new LinkedList<Board>();

        int zeroI = 0;
        int zeroJ = 0;

        outerLoop:
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                    break outerLoop;
                }
            }
        }

        for (int k = 0; k < 4; k++) {
            int blockI = zeroI;
            int blockJ = zeroJ;

            switch (k) {
                case 0: blockI++; 
                        break;
                case 1: blockI--; 
                        break;
                case 2: blockJ++; 
                        break;
                case 3: blockJ--; 
                        break;
            }

            if (blockI < 0 || blockI >= dimension() || blockJ < 0 || blockJ >= dimension()) continue;
            int[][] heighborBlocks = blocksCopy();
            
            heighborBlocks[zeroI][zeroJ] = heighborBlocks[blockI][blockJ];
            heighborBlocks[blockI][blockJ] = 0;

            Board neighbor = new Board(heighborBlocks);
            neighbors.add(neighbor);
        }

        return neighbors; 
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dimension());

        for (int i = 0; i < dimension(); i++) {
            stringBuilder.append("\n");
            for (int j = 0; j < dimension(); j++) {
                stringBuilder.append(" " + blocks[i][j]);
            }
        }

        return stringBuilder.toString();
    }

    private int[][] blocksCopy() {
        int[][] blocksCopy = new int[dimension()][dimension()];

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                blocksCopy[i][j] = blocks[i][j];
            }
        }

        return blocksCopy;
    }
}