package framework;

import java.util.Scanner;
import java.awt.Point;
import pieces.*;
import player.*;



public class Game {
	public static void main(String[] args) {
		new Game();
	}

	int[] list = new int[4];
	public static Point[] move = new Point[2];
	Scanner myObj = new Scanner(System.in);
	static Board b = new Board();
	RandBot bot = new RandBot();
	int y1, x1, y2, x2;

	public Game() {

		startMenu();
		
  	}

	public void startMenu() {
		
	}

	public void promote() {
		String temp;
		System.out.println("you have queened, please type the letter for hte piece you want. queen q, rook r, knight n, bishop b");
		temp = myObj.nextLine();
		System.out.println(temp);
		while (!(temp.equals("q") || temp.equals("r") || temp.equals("n") || temp.equals("b"))){
			System.out.println("try again, use q, r, n, or b");
			temp = myObj.nextLine();
		}
		if (temp.equals("q")) {
			b.board[y2][x2].setPiece(new Queen(b.board[y1][x1].getPiece()));
			b.board[y1][x1].removePiece();
		} else if (temp.equals("r")) {
			b.board[y2][x2].setPiece(new Rook(b.board[y1][x1].getPiece()));
			b.board[y1][x1].removePiece();
		} else if (temp.equals("n")) {
			b.board[y2][x2].setPiece(new Knight(b.board[y1][x1].getPiece()));
			b.board[y1][x1].removePiece();
		} else if (temp.equals("b")) {
			b.board[y2][x2].setPiece(new Bishop(b.board[y1][x1].getPiece()));
			b.board[y1][x1].removePiece();
		}
	}
}