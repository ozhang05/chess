public class Pawn extends Piece{
		public int counter;
		
		Pawn(boolean color) {
			super(color, false, true);
			if (color) {
				addPoint(1, 0);
				addPoint(2, 0);
			} else {
				addPoint(-1, 0);
				addPoint(-2, 0);
			}
			
		}
		
		public void changeMoveStatus(boolean m) {
				if (m) {
						if (getLength() == 2) {
								removePoint(1);
						}
				} else {
						if (getColor()) {
								addPoint(2, 0);
						} else {
								addPoint(-2, 0);
						}
				}
				super.changeMoveStatus(m);
		}
		
		public boolean isValidMove(int y1, int x1, int y2, int x2) {
			if (!pieceColor) {
				if (!getFirstMove()) {
					return y1-y2 <= 2 && y1-y2 > 0 && x1 == x2 && withinBounds(y1, x1) && withinBounds(y2, x2);
				}
				return y1-y2 == 1 && x1 == x2 && withinBounds(y1, x1) && withinBounds(y2, x2);
			}
			if (!getFirstMove()) {
				return y2-y1 <= 2 && y2-y1 > 0 && x1 == x2 && withinBounds(y1, x1) && withinBounds(y2, x2);
			}
			return y2-y1 == 1 && x1 == x2 && withinBounds(y1, x1) && withinBounds(y2, x2);
		}
		
		public boolean canTake(int y1, int x1, int y2, int x2) {
				if (!pieceColor) {
						return y2<y1 && Math.abs(y2-y1) == 1 && Math.abs(x2-x1) == 1;
				}
				return y2>y1 && Math.abs(y2-y1) == 1 && Math.abs(x2-x1) == 1;
		}
		
		public String getValue() {
				return "p";
		}
	}