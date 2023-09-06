public class Knight extends Piece{
	Knight(boolean color)  {
		super(color);
		addPoint(-2, -1);
		addPoint(-2, 1);
		addPoint(-1, 2);
		addPoint(-1, -2);
		addPoint(1, 2);
		addPoint(1, -2);
		addPoint(2, 1);
		addPoint(2, -1);
	}
	Knight(Piece p) {
		this(p.getColor());
	}	
	public boolean isValidMove(int y1, int x1, int y2, int x2) {
		int tempY = Math.abs(y1-y2);
	    int tempX = Math.abs(x1-x2);
	    return tempY + tempX == 3 && tempY > 0 && tempX > 0;
	}

	public boolean canTake(int a, int b, int c, int d) {
		return isValidMove(a, b, c, d);
	}
	  
	public String getValue() {
		return "n";
	}
}