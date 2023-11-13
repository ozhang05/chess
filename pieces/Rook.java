package pieces;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.*;

import framework.Board;

public class Rook extends Piece{
	boolean firstMove;

	public Rook(boolean color) {
		super(color, false, true);
		for(int i = -7; i < Board.BOARDSIZE; i++) {
			if (i != 0) {
				addPoint(i, 0);
				addPoint(0, i);
			}
		}
	}

	public Rook(Piece p) {
		this(p.getColor());
	}

	public Image getImage() {

		try {
			img = ImageIO.read(getClass().getResourceAsStream("/piece_images/br.png"));
			if (super.getColor()) {
				img = ImageIO.read(getClass().getResourceAsStream("/piece_images/wr.png"));
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		return img;

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