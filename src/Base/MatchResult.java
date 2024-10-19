package Base;

public class MatchResult {
    public int firstRow, firstCol;
    public int secondRow, secondCol;

    public MatchResult(int firstRow, int firstCol, int secondRow, int secondCol) {
        this.firstRow = firstRow;
        this.firstCol = firstCol;
        this.secondRow = secondRow;
        this.secondCol = secondCol;
    }
}
