package pieces;

import java.util.ArrayList;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Piece {
	boolean firstMove = false;
	ArrayList<Integer> validYMove = new ArrayList<Integer>();
	ArrayList<Integer> validXMove = new ArrayList<Integer>();
	BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
	public boolean pieceColor;
	public boolean canMoveDiag, canMoveHori;
	public boolean isKing = false;
	//white = true, black = false

	public Piece(boolean color, boolean diag, boolean hori) {
		pieceColor = color;
		canMoveDiag = diag;
		canMoveHori = hori;
	}

	public Piece(boolean color) {
		pieceColor = color;
	}

	public Piece(){
	}

	public boolean getColor() {
		return pieceColor;
	}

	public Image getImage() {
		return img;
	}

	public boolean canDiag() {
		return canMoveDiag;
	}

	public boolean canHori() {
		return canMoveHori;
	}

	public void changeMoveStatus(boolean b) {
		firstMove = b;
	}

	public void addPoint(int y, int x) {
		validYMove.add(y);
		validXMove.add(x);
	}

	public void removePoint(int i) {
		validYMove.remove(i);
		validXMove.remove(i);
	}

	public int getY(int i) {
		return validYMove.get(i);
	}

	public int getX(int i) {
		return validXMove.get(i);
	}

	public int getLength() {
		return validYMove.size();
	}

	public boolean withinBounds (int y, int x) {
		return y < 8 && x < 8;
	}

	public boolean canTake (int a, int b, int c, int d) {
		return false;
	}

	public boolean isValidMove (int a, int b, int c, int d) {
		return false;
	}

	public String getValue() {
		return " ";
	}

	public boolean checkKing() {
		return isKing;
	}

	public void makeKing() {
		isKing = true;
	}

	public boolean getFirstMove() {
		return firstMove;
	}

}