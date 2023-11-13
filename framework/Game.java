package framework;

import java.util.Scanner;
import java.awt.Point;
import pieces.*;
import player.*;


public class Game {
	int[] list = new int[4];
	public static Point[] move = new Point[2];
	Scanner myObj = new Scanner(System.in);
	static Board b = new Board();
	RandBot bot = new RandBot();
	int y1, x1, y2, x2;

	public Game() {

		// int turnCount = 1;
		// boolean won = false;
		// boolean stalemate = false;
		// boolean moveMade = false;
		// while (!won && !stalemate) {
		// 	if (moveMade) {
		// 		moveMade = false;
		// 	}
		// 	//choose one of the following print methods. the first one flips the board each turn, the second one plays from the perspective of white
		// 	//System.out.println(b.toString(turnCount%2 == 0));
		// 	System.out.println("new iteration");
		// 	System.out.println(b.toString(false));

		// 	if (b.checkKingCheck(turnCount%2!=0)) {
		// 		if (b.isCheckmated(turnCount%2!=0)) {
		// 			won = true;
		// 			if (turnCount%2 != 0) {
		// 				System.out.println("Black won!");
		// 			} else {
		// 				System.out.println("White won!");
		// 			}
		// 			break;
		// 		}
		// 	} else if (b.isStalemated(turnCount%2==0)) {
		// 		System.out.println("Stalemated");
		// 		stalemate = true;
		// 		break;
		// 	}
		// 	System.out.println("Input your four values separated by spaces (y1 x1 y2 x2): ");
		// 	/*
		// 	for (int i = 0; i < 4; i++) {
		// 		list[i] = Math.abs(myObj.nextInt());
		// 	}
		// 	*/

		// 	move = b.getMove();

		// 	System.out.println("move received");

		// 	// y1 = list[0];
		// 	// x1 = list[1];
		// 	// y2 = list[2];
		// 	// x2 = list[3];

		// 	y1 = (int) move[0].getY();
		// 	x1 = (int) move[0].getX();
		// 	y2 = (int) move[1].getY();
		// 	x2 = (int) move[1].getX();
		// 	System.out.println(y1);
		// 	System.out.println(x1);
		// 	System.out.println(y2);
		// 	System.out.println(x2);

		// 	if (!(y1 > Board.boardSize-1 || x1 > Board.boardSize-1 || y2 > Board.boardSize-1 || x2 > Board.boardSize-1)) {
		// 		if (!b.board[y1][x1].pieceExists()) {
		// 			System.out.println("No piece here");
		// 			continue;
		// 		}
		// 		if (b.board[y1][x1].getPiece().getColor() == (turnCount%2 == 0)) {
		// 			System.out.println("Wrong color");
		// 			continue;
		// 		}
		// 		if (b.board[y1][x1].getPiece().canDiag()) {
		// 			if (b.isDiagMove(y1, x1, y2, x2)) {
		// 				if (!b.canMoveDiag(y1, x1, y2, x2)) {
		// 					System.out.println("Piece is blocked");
		// 					continue;
		// 				}
		// 			}
		// 		}
		// 		if (b.board[y1][x1].getPiece().canHori()) {
		// 			if (b.isHoriMove(y1, x1, y2, x2)) {
		// 				if (!b.canMoveHori(y1, x1, y2, x2)) {
		// 					System.out.println("Piece is blocked");
		// 					continue;
		// 				}
		// 			}
		// 		}
		// 		if (b.createsACheckMove(y1, x1, y2, x2)) {
		// 			//System.out.println(y1 + " , " + x1);
		// 			System.out.println("this is the inputs for the created check: " + y2 + " , " + x2);
		// 			System.out.println("You are creating a check");
		// 			continue;
		// 		}
		// 		if (b.board[y2][x2].pieceExists()) {
		// 			if (b.board[y1][x1].getPiece().getColor() == b.board[y2][x2].getPiece().getColor()) {
		// 				System.out.println("Can't take your own piece");
		// 				continue;
		// 			}
		// 			if (b.board[y1][x1].canTake(y1, x1, y2, x2)) {
		// 				if (b.checkPawnQueen(y2)) {
		// 					if (b.board[y1][x1].getPiece().getValue().equals("p")) {
		// 						promote();
		// 						moveMade = true;
		// 						turnCount++;
		// 						continue;
		// 					}
		// 				}
		// 				b.takePiece(y1, x1, y2, x2);
		// 				moveMade = true;
		// 				turnCount++;
		// 			} else {
		// 				System.out.println("This is not a valid take move");
		// 				continue;
		// 			}
		// 		} else if (b.isValidCastle(turnCount%2 != 0, y1, x1, y2, x2) && !b.checkKingCheck(turnCount%2 != 0)) {
		// 			b.castle(turnCount%2 != 0, y1, x1, y2, x2);
		// 			moveMade = true;
		// 			turnCount++;
		// 			continue;
		// 		} else if (b.board[y1][x1].isValidMove(y1, x1, y2, x2)) {
		// 			if (b.board[y1][x1].getPiece().getValue().equals("p")) {
		// 				if (b.checkPawnQueen(y2)) {
		// 					promote();
		// 					moveMade = true;
		// 					turnCount++;
		// 					continue;
		// 				}
		// 			}
		// 			b.movePiece(y1, x1, y2, x2);
		// 			moveMade = true;
		// 			turnCount++;
		// 		} else {
		// 			System.out.println("invalid: " + y1 + " , " + x1 + " , " + y2 + " , " + x2 + " , " + b.isValidCastle(turnCount%2 != 0, y1, x1, y2, x2));
		// 			System.out.println("This is not a valid move");
		// 			continue;
		// 		}
		// 	} else {
		// 		System.out.println("one of your values is too large. please try again");
		// 		continue;
		// 	}
		// }
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