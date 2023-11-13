package pieces;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.*;

public class Knight extends Piece{
	public Knight(boolean color) {
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

	public Knight(Piece p) {
		this(p.getColor());
	}

	public Image getImage() {

		try {
			img = ImageIO.read(getClass().getResourceAsStream("/piece_images/bn.png"));
			if (super.getColor()) {
				img = ImageIO.read(getClass().getResourceAsStream("/piece_images/wn.png"));
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		return img;

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