package Base;


public class Algr implements Config{
	
	public static boolean checkRow(int r1,int c1,int r2,int c2) {
		if(r1 == r2) {
			int minCol = Math.min(c1,c2);
			int maxCol = Math.max(c1,c2);
 			for(int i = minCol+1;i<maxCol;i++) {
 				if(ICONS[r1][i] != null) {
 					return false;
 				} 
 			}
 			wireList.add(new Point(r1,c1));
 			wireList.add(new Point(r2,c2));
 			return true;
		}
		return false;
	}
	
	public static boolean checkCol(int r1,int c1,int r2,int c2) {
		if(c1 == c2) {
			int minRow = Math.min(r1,r2);
			int maxRow = Math.max(r1,r2);
 			for(int i = minRow+1;i<maxRow;i++) {
 				if(ICONS[i][c1] != null) {
 					return false;
 				}
 			}
 			wireList.add(new Point(r1,c1));
 			wireList.add(new Point(r2,c2));
 			return true;
		}
		return false;
	}
	
	public static boolean onePoint(int r1,int c1,int r2,int c2) {
		if(r1 == r2 || c1 == c2) {
			return false;
		}else {
			if(ICONS[r1][c2] == null && checkRow(r1, c1, r1, c2) && checkCol(r1, c2, r2, c2)) {
				return true;
			}
			wireList.clear();
			if(ICONS[r2][c1] == null && checkRow(r2, c1, r2, c2) && checkCol(r1, c1, r2, c1)) {
				return true;
			}
			wireList.clear();
		}
		return false;
	} 
	
	public static boolean twoPoint(int r1,int c1,int r2,int c2) {
		if(r1 == r2 && c1 == c2) {
			return false;
		}else {
			int minCol = Math.min(c1,c2);
			int maxCol = Math.max(c1,c2);
			int minRow = Math.min(r1,r2);
			int maxRow = Math.max(r1,r2);
			
			for(int i = 0;i<COLS;i++) {
				if(ICONS[r1][i] == null && ICONS[r2][i] == null && checkCol(r1, i, r2, i)) {
					if(checkRow(r1, i, r1, c1)&&checkRow(r2, i, r2, c2)) {
						return true;
					}
				}
				wireList.clear();
			}
			
			for(int i = 0;i<ROWS;i++) {
				if(ICONS[i][c1] == null && ICONS[i][c2] == null && checkRow(i, c1, i, c2)) {
					if(checkCol(i, c1, r1, c1)&&checkCol(i, c2, r2, c2)) {
						return true;
					}
				}
				wireList.clear();
			}
			
		}
		return false;
	}
}
