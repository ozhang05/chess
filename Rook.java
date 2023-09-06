public class Rook extends Piece{
	boolean firstMove;
	  Rook(boolean color)  {
		  super(color, false, true);
		  for(int i = -7; i < Board.boardSize; i++) {
			  if (i != 0) {
				  addPoint(i, 0);
				  addPoint(0, i);
			  }
		  }
	  }
	  Rook(Piece p) {
		  this(p.getColor());
	  }
	  public boolean isValidMove(int y1, int x1, int y2, int x2) {
	    return y1 == y2 || x1 == x2 && (x1-x2 != 0 || y1-y2 != 0);
	  }
	  public boolean canTake(int a, int b, int c, int d) {
	    return isValidMove(a, b, c, d);
	  }
	  public String getValue() {
	    return "r";
	  }
}