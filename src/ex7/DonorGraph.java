package ex7;

import java.util.*;

public class DonorGraph {
    private List<List<Match>> adjList;

    // The donatingTo array indicates which repient each donor is
    // affiliated with. Specifically, the donor at index i has volunteered
    // to donate a kidney on behalf of recipient donatingTo[i].
    // The matchScores 2d array gives the match scores associated with each
    // donor-recipient pair. Specificically, matchScores[x][y] gives the
    // HLA score for donor x and reciplient y.
    public DonorGraph(int[] donorToBenefit, int[][] matchScores) {
        // TODO
        // ensure to get the number of recipients
        int numRecipients = matchScores[0].length;
        this.adjList = new ArrayList<>(numRecipients);

        // initialize the adjlist
        for (int i = 0; i < numRecipients; i++) {
            adjList.add(new ArrayList<>());
        }

        // fill in the adjlist, don't really care which beneficiary the donor wants to donate to.
        // whatever who is a match with that donor, add it to the beneficiary's graph
        for (int donor = 0; donor < donorToBenefit.length; donor++) {
            int beneficiary = donorToBenefit[donor];  // this represents the recipient that the donor wants to donate to
            for (int recipient = 0; recipient < numRecipients; recipient++) {
                if (matchScores[donor][recipient] >= 60) {  // the match score has to greater than 60
                    adjList.get(beneficiary).add(new Match(donor, beneficiary, recipient));
                }
            }
        }
    }

    // Will be used by the autograder to verify your graph's structure.
    // It's probably also going to helpful for your debugging.
    public boolean isAdjacent(int start, int end) {
        for (Match m : adjList.get(start)) {
            if (m.recipient == end)
                return true;
        }
        return false;
    }

    // Will be used by the autograder to verify your graph's structure.
    // It's probably also going to helpful for your debugging.
    public int getDonor(int beneficiary, int recipient) {
        for (Match m : adjList.get(beneficiary)) {
            if (m.recipient == recipient)
                return m.donor;
        }
        return -1;
    }


    // returns a chain of matches to make a donor cycle
    // which includes the given recipient.
    // Returns an empty list if no cycle exists.
    public List<Match> findCycle(int recipient) {
        // TODO
        List<Match> cycle = new ArrayList<>();
        boolean[] visited = new boolean[adjList.size()];
        // DFS search if there is a perfect cycle then everybody happy in that cycle
        if (DFS(visited, cycle, recipient, recipient)) {
            return cycle;
        }
        return new ArrayList<>();  // empty list
    }

    /**
     * @param visited
     * @param cycle
     * @param begin
     * @param curr
     * @return
     */
    private boolean DFS(boolean[] visited, List<Match> cycle, int begin, int curr) {
        visited[curr] = true;  // mark this node as visited

        // traversal all the possible curr -> recipient paths, that are useful
        for (Match match : adjList.get(curr)) {
            // if the donor's recipient is back to begin, then this is a closed cycle
            // also there is already some paths, so it's a useful cycle
            if (match.recipient == begin && cycle.size() > 0) {
                cycle.add(match);  // don't miss this one
                return true;
            }

            // recursively search for the next recipient
            if (!visited[match.recipient]) {
                // add this match
                cycle.add(match);
                // not backed to the begin yet, do another layer of DFS, try to find the begin
                // if the begin is found then return true. otherwise delete this cycle, like what dfs does.
                if (DFS(visited, cycle, begin, match.recipient)) {
                    return true;
                }
                cycle.remove((cycle.size() - 1));
            }
        }
        return false;  // no cycle
    }

    // returns true or false to indicate whether there
    // is some cycle which includes the given recipient.
    public boolean hasCycle(int recipient) {
        // TODO
        boolean[] visited = new boolean[adjList.size()];
        // because adjlist is a member field, no need to pass in, and it's already there
        // to be examined, but we do need to initialize a visited[], as a passed in parameter
        return DFS(visited, new ArrayList<>(), recipient, recipient);
    }
}
