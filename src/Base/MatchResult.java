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

    @Override
    public boolean equals(Object obj) {
        // 检查是否是同一对象的引用
        if (this == obj) {
            return true;
        }

        // 检查是否是null或者对象类型不同
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // 强制类型转换
        MatchResult other = (MatchResult) obj;

        // 比较所有的字段
        return this.firstRow == other.firstRow &&
               this.firstCol == other.firstCol &&
               this.secondRow == other.secondRow &&
               this.secondCol == other.secondCol;
    }

}
