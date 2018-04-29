import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.HashMap;
import java.util.HashSet;

public class BaseballElimination {
    private static final int SOURCE_INDEX = 0;
    private static final int SINK_INDEX = 1;

    private final HashMap<String, Integer> teamsMap;
    private final String[] teams;
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] against;
    private final HashMap<String, HashSet<String>> eliminationMap;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int teamsCount = in.readInt();

        this.teamsMap = new HashMap<>();
        this.teams = new String[teamsCount];
        this.wins = new int[teamsCount];
        this.losses = new int[teamsCount];
        this.remaining = new int[teamsCount];
        this.against = new int[teamsCount][teamsCount];
        this.eliminationMap = new HashMap<>();

        for (int i = 0; i < teamsCount; i++) {
            String teamName = in.readString();

            this.teamsMap.put(teamName, i);
            this.teams[i] = teamName;

            this.wins[i] = in.readInt();
            this.losses[i] = in.readInt();
            this.remaining[i] = in.readInt();

            for (int j = 0; j < teamsCount; j++) {
                this.against[i][j] = in.readInt();
            }
        }
    }           
    
    // number of teams
    public int numberOfTeams() {
        return teams.length;
    }
    
    // all teams
    public Iterable<String> teams() {
        return this.teamsMap.keySet();
    }
    
    // number of wins for given team
    public int wins(String team) {
        return this.wins[this.teamIndex(team)];
    }
    
    // number of losses for given team
    public int losses(String team) {
        return this.losses[this.teamIndex(team)];
    }
    
    // number of remaining games for given team
    public int remaining(String team) {
        return this.remaining[this.teamIndex(team)];
    }
    
    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return this.against[this.teamIndex(team1)][this.teamIndex(team2)];
    }
    
    private Integer teamIndex(String team) {
        Integer teamIndex = this.teamsMap.get(team);
        if (teamIndex == null) {
            throw new IllegalArgumentException();
        }

        return teamIndex;
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return !eliminationSet(team).isEmpty();
    }
    
    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return isEliminated(team) ? eliminationSet(team) : null;
    }
    
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

    private HashSet<String> eliminationSet(String team) {
        if (this.teamsMap.get(team) == null) {
            throw new IllegalArgumentException();
        }
        
        if (this.eliminationMap.get(team) == null) {
            if (!trivialElimination(team)) {
                calculateElimination(team);
            }
        }

        return this.eliminationMap.get(team);
    }

    private boolean trivialElimination(String team) {
        Integer teamIndex = this.teamIndex(team);
        int maxWins = this.wins[teamIndex] + this.remaining[teamIndex];
        HashSet<String> eliminatingTeams = new HashSet<>();

        for (int i = 0; i < this.numberOfTeams(); i++) {
            if (i == teamIndex) continue;

            if (maxWins < wins[i]) {
                eliminatingTeams.add(this.teams[i]);
            }
        }

        this.eliminationMap.put(team, eliminatingTeams);

        return !eliminatingTeams.isEmpty();
    }

    private void calculateElimination(String team) {
        int teamIndex = teamIndex(team);
        int teamsCount = this.teams.length;
        int opponentTeams = teamsCount - 1;
        int totalGames = teamsCount * opponentTeams;
        int uniqueGames = totalGames / 2;
        int opponentsGames = uniqueGames - opponentTeams;

        int edgesCount = 2 + opponentsGames + opponentTeams;

        FlowNetwork network = new FlowNetwork(edgesCount);
        
        int gameEdgeIndex = opponentTeams + 2;

        int maxWins = this.wins[teamIndex] + this.remaining[teamIndex];

        for (int i = 0; i < this.numberOfTeams(); i++) {
            if (i == teamIndex) continue;

            int opponentIndex = i < teamIndex ? i + 2 : i + 1;
            FlowEdge teamToSinkEdge = new FlowEdge(opponentIndex, SINK_INDEX, maxWins - this.wins[i]);
            network.addEdge(teamToSinkEdge);

            for (int j = 0; j < i; j++) {
                if (j == teamIndex) continue;

                int gamesCount = this.against[i][j];

                FlowEdge sourceToGameEdge = new FlowEdge(SOURCE_INDEX, gameEdgeIndex, gamesCount);
                network.addEdge(sourceToGameEdge);
                
                FlowEdge team1WinEdge = new FlowEdge(gameEdgeIndex, opponentIndex, Double.POSITIVE_INFINITY);
                network.addEdge(team1WinEdge);

                int team2Index = j < teamIndex ? j + 2 : j + 1;
                FlowEdge team2WinEdge = new FlowEdge(gameEdgeIndex, team2Index, Double.POSITIVE_INFINITY);
                network.addEdge(team2WinEdge);

                gameEdgeIndex++;
            }
        }

        FordFulkerson maxFlow = new FordFulkerson(network, SOURCE_INDEX, SINK_INDEX);
        HashSet<String> eliminatingTeams = new HashSet<>();

        for (int i = 0; i < this.numberOfTeams(); i++) {
            if (i == teamIndex) continue;

            int opponentIndex = i < teamIndex ? i + 2 : i + 1;

            if (maxFlow.inCut(opponentIndex)) {
                eliminatingTeams.add(teams[i]);
            }
        }

        this.eliminationMap.put(team, eliminatingTeams);
    }
}