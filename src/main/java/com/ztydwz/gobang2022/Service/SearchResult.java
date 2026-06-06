package main.java.com.ztydwz.gobang2022.Service;

public class SearchResult {
    public final int score;
    public final int row;
    public final int col;
    public final int depth;
    public final long elapsedMs;

    public SearchResult(int score, int row, int col, int depth, long elapsedMs) {
        this.score = score;
        this.row = row;
        this.col = col;
        this.depth = depth;
        this.elapsedMs = elapsedMs;
    }

    public static SearchResult empty(long elapsedMs) {
        return new SearchResult(Integer.MIN_VALUE, -1, -1, 0, elapsedMs);
    }
}
