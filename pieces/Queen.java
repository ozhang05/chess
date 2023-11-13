package pieces;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.*;

import framework.Board;

public class Queen extends Piece{
	public Queen(boolean color) {
		super(color, true, true);
		for(int i = -7; i < Board.BOARDSIZE; i++) {
			if (i != 0) {
				addPoint(i, 0);
				addPoint(0, i);
				addPoint(i, i);
				addPoint(i, -i);
			}
		}
	}

	public Queen(Piece p) {
		this(p.getColor());
	}

	public Image getImage() {

		try {
			img = ImageIO.read(getClass().getResourceAsStream("/piece_images/bq.png"));
			if (super.getColor()) {
				img = ImageIO.read(getClass().getResourceAsStream("/piece_images/wq.png"));
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		return img;

	}

	public boolean isValidMove(int y1, int x1, int y2, int x2) {
	  if (y1 == y2 || x1 == x2) {
		  if (y1-y2 == 0 && x1-x2 == 0) {
			  return false;
		  }
			return true;
	  } else {
		  double tempY = Math.abs((1.0 * y1) - y2);
		  double tempX = Math.abs((1.0 * x1) - x2);
		  return tempY/tempX == 1;
	  }
	}
	public boolean canTake(int a, int b, int c, int d) {
	  return isValidMove(a, b, c, d);
	}
	public String getValue() {
	  return "q";
	}
  }