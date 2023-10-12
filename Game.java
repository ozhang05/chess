import java.util.Scanner;
public class Game {
	int[] list = new int[4];
	Scanner myObj = new Scanner(System.in);
	static Board b = new Board();
	Game() {
		int turnCount = 1;
		boolean won = false;
		boolean stalemate = false;
		boolean moveMade = false;
		while (!won && !stalemate) {
			if (moveMade) {
				//this clears the console
				//System.out.print("\033[H\033[2J");
				//System.out.flush();
				moveMade = false;
			}
			System.out.println(b.toString(turnCount%2 == 0));
			//below is used to check different aspects of the game by following real ones
			//System.out.println(b.toString(false));
			if (b.checkKingCheck(turnCount%2!=0)) {
				//System.out.println("some1 checked");
				if (b.isCheckmated(turnCount%2!=0)) {
					won = true;
					if (turnCount%2 != 0) {
						System.out.println("black won");
					} else {
						System.out.println("white won");
					}
					break;
				}
			} else if (b.isStalemated(turnCount%2==0)) {
				System.out.println("stalemated");
				stalemate = true;
				break;
			}
			System.out.println("input your four values (y1, x1, y2, x2): ");
			for (int i = 0; i < 4; i++) {
				list[i] = myObj.nextInt();
			}
			if (!(list[0] > Board.boardSize-1 || list[1] > Board.boardSize-1 || list[2] > Board.boardSize-1 || list[3] > Board.boardSize-1)) {
				if (!b.board[list[0]][list[1]].pieceExists()) {
					System.out.println("no piece here");
					continue;
				}
				if (b.board[list[0]][list[1]].getPiece().getColor() == (turnCount%2 == 0)) {
					System.out.println("wrong color");
					continue;
				}
				if (b.board[list[0]][list[1]].getPiece().canDiag()) {
					if (b.isDiagMove(list[0], list[1], list[2], list[3])) {
						if (!b.canMoveDiag(list[0], list[1], list[2], list[3])) {
							System.out.println("blocked diagonally");
							continue;
						}
					}
				}
				if (b.board[list[0]][list[1]].getPiece().canHori()) {
					if (b.isHoriMove(list[0], list[1], list[2], list[3])) {
						if (!b.canMoveHori(list[0], list[1], list[2], list[3])) {
							System.out.println("isblocked horizontally");
							continue;
						}
					}
				}
				if (b.board[list[2]][list[3]].pieceExists()) {
					if (b.board[list[0]][list[1]].getPiece().getColor() == b.board[list[2]][list[3]].getPiece().getColor()) {
						System.out.println("cannot take same color, try again");
						continue;
					}
					if (b.board[list[0]][list[1]].getPiece().checkKing()) {
						if (b.checkKingCheck(turnCount%2!=0, list[2], list[3])) {
							System.out.println("under check when taking here");
							continue;
						}
					}
					if (b.createsACheckTake(list[0], list[1], list[2], list[3])) {
						System.out.println("you are taking into check");
						continue;
					}
					if (b.board[list[0]][list[1]].canTake(list[0], list[1], list[2], list[3])) {
						if (b.board[list[0]][list[1]].getPiece().getValue().equals("p")) {
							if (b.checkPawnQueen(list[2])) {
								queen();
								moveMade = true;
								turnCount++;
								continue;
							}
						}
						b.takePiece(list[0], list[1], list[2], list[3]);
						moveMade = true;
						turnCount++;
					} else {
						System.out.println("this is not a valid take move, please try again");
						continue;
					}
				} else if (b.isValidCastle(turnCount%2 != 0, list[0], list[1], list[2], list[3]) && !b.checkKingCheck(turnCount%2 != 0)) {
					b.castle(turnCount%2 != 0, list[0], list[1], list[2], list[3]);
					moveMade = true;
					turnCount++;
					System.out.println("is valid castle");
					continue;
				}	else if (b.board[list[0]][list[1]].isValidMove(list[0], list[1], list[2], list[3])) {
					if (b.board[list[0]][list[1]].getPiece().checkKing()) {
						if (b.checkKingCheck(turnCount%2!=0, list[2], list[3])) {
							System.out.println("moving into check");
							continue;
						}
					}
					if (b.createsACheckMove(list[0], list[1], list[2], list[3])) {
						System.out.println("you are creating a check with this move");
						continue;
					}
					if (b.board[list[0]][list[1]].getPiece().getValue().equals("p")) {
						if (b.checkPawnQueen(list[2])) {
							queen();
							moveMade = true;
							turnCount++;
							continue;
						}
					}
					b.movePiece(list[0], list[1], list[2], list[3]);
					moveMade = true;
					turnCount++;
				} else {
					System.out.println("this is not a valid move, please try again");
					continue;
				}
			} else {
				System.out.println("one of your values is too large. please try again");
				continue;
			}
		}
  	}
	public void queen() {
		String temp;
		System.out.println("you have queened, please type the letter for hte piece you want. queen q, rook r, knight n, bishop b");
		temp = myObj.nextLine();
		System.out.println(temp);
		temp = myObj.nextLine();
		System.out.println(temp);
		while (!(temp.equals("q") || temp.equals("r") || temp.equals("n") || temp.equals("b"))){
			System.out.println("try again, use q, r, n, and b");
			temp = myObj.nextLine();
		}
		if (temp.equals("q")) {
			b.board[list[2]][list[3]].setPiece(new Queen(b.board[list[0]][list[1]].getPiece()));
			b.board[list[0]][list[1]].removePiece();
		} else if (temp.equals("r")) {
			b.board[list[2]][list[3]].setPiece(new Rook(b.board[list[0]][list[1]].getPiece()));
			b.board[list[0]][list[1]].removePiece();
		} else if (temp.equals("n")) {
			b.board[list[2]][list[3]].setPiece(new Knight(b.board[list[0]][list[1]].getPiece()));
			b.board[list[0]][list[1]].removePiece();
		} else if (temp.equals("b")) {
			b.board[list[2]][list[3]].setPiece(new Bishop(b.board[list[0]][list[1]].getPiece()));
			b.board[list[0]][list[1]].removePiece();
		}
	}
}