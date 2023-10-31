import java.awt.Image;
import java.io.IOException;
import javax.imageio.*;

public class Bishop extends Piece{
	Bishop(boolean color) {
		super(color, true, false);
		for (int i = 1; i < Board.boardSize; i++) {
			addPoint(i, i);
			addPoint(i, -i);
			addPoint(-i, i);
			addPoint(-i, -i);
		}
	}

	public Image getImage() {

		try {
			img = ImageIO.read(getClass().getResourceAsStream("/piece_images/bb.png"));
			if (super.getColor()) {
				img = ImageIO.read(getClass().getResourceAsStream("/piece_images/wb.png"));
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		return img;

	}

	Bishop(Piece p) {
		this(p.getColor());
	}
	public boolean isValidMove(int y1, int x1, int y2, int x2) {
		double tempY = Math.abs((1.0 * y1) - y2);
		double tempX = Math.abs((1.0 * x1) - x2);
		return tempY/tempX == 1;
	}
	public boolean canTake(int a, int b, int c, int d) {
		return isValidMove(a, b, c, d);
	}
	public String getValue() {
		return "b";
	}
}