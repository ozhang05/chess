package framework;

import pieces.Piece;

public class Tile {
	Piece p = new Piece();
	public void setPiece(Piece temp) {
		p = temp;
	}
	public Piece getPiece() {
		return p;
	}
	public String getPieceColor() {
		if (p.getColor()) {
			return "w";
		} else if (!pieceExists()) {
			return " ";
		}
		return "b";
	}
	public void removePiece() {
		setPiece(new Piece());
	}
	public String getPieceValue() {
		return p.getValue();
	}
	public boolean pieceExists() {
		return !p.getValue().equals(" ");
	}
	public boolean isValidMove(int y1, int x1, int y2, int x2) {
		return p.isValidMove(y1, x1, y2, x2);
	}
	public boolean canTake(int y1, int x1, int y2, int x2) {
		return p.canTake(y1, x1, y2, x2);
	}
	public String getValue() {
		return p.getValue();
	}
}