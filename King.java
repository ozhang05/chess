public class King extends Piece{
	King(boolean color)  {
	    super(color);
	    makeKing();
	    for (int y = -1; y > 2; y++) {
	    	for (int x = -1; x > 2; x++) {
	    		if (y != 0 || x != 0) {
	    			addPoint(y, x);
	    		}
	    	}
	    }
	}
	public boolean isValidMove(int y1, int x1, int y2, int x2) {
	    int tempValY = Math.abs(y2-y1);
	    int tempValX = Math.abs(x2-x1);
	    return tempValY < 2 && tempValX < 2 && tempValY + tempValX > 0;
	}
	public boolean canTake(int a, int b, int c, int d) {
	    return isValidMove(a, b, c, d);
	}
	public String getValue() {
	    return "k";
	}
}