package framework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import pieces.*;

public class Board extends JFrame{
	public static final int BOARDSIZE = 8;
	public static final int TILEPIXELSIZE = 100;
	int turnCount = 0;
	public Tile[][] board = new Tile[BOARDSIZE][BOARDSIZE];
	//BELOW IS USED FOR RENDERING
	//ALSO SIZE
	JLabel[][] imageGrid = new JLabel[BOARDSIZE][BOARDSIZE];
	public JFrame frame = new JFrame();
	private Point[] movePoints = new Point[2];
	int wKingX, wKingY;
	int bKingX, bKingY;
	
	Board() {
		for (int y = 0; y < BOARDSIZE; y++) {
			for (int x = 0; x < BOARDSIZE; x++) {
				board[y][x] = new Tile();
			}
		}
		makePiecesTop();
		makePiecesBot();
		createBoard();
	}

	private void updateBoard() {
		for (int y = 7; y >= 0; y--) {
			for (int x = 0; x < BOARDSIZE; x++) {
				Image i = board[y][x].getPiece().getImage();
				i = i.getScaledInstance(TILEPIXELSIZE, TILEPIXELSIZE, Image.SCALE_DEFAULT);
				imageGrid[7-y][x].setIcon(new ImageIcon(i));
				imageGrid[7-y][x].setBorder(null);
			}
		}
		SwingUtilities.updateComponentTreeUI(frame);
	}

	private void createBoard() {
		System.out.println("Alignment (y, x): " + frame.getBounds().getY() + ", " + frame.getLocation() + ", ");
		JLabel b;
		frame.setVisible(true);
		for (int y = 7; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				Image i = board[y][x].getPiece().getImage();
				i = i.getScaledInstance(TILEPIXELSIZE, TILEPIXELSIZE, Image.SCALE_DEFAULT);
				b = new JLabel();
				if (board[y][x].pieceExists()) {
					b = new JLabel(new ImageIcon(i));
				}
				b.setOpaque(true);
				b.setBorder(null);
				// b.addMouseListener(new MouseListener() {
				// 	public void mousePressed(MouseEvent e) {
				// 		//System.out.println("This is the click (y, x): " + (e.getYOnScreen()-(int)frame.getLocation().getY()) + ", " + frame.getLocationOnScreen().getY());
				// 		//System.out.println("This is the click2 (y, x): " + frame.getLocationOnScreen() + ", " + e.getX() + ", " + e.getLocationOnScreen());
				// 		//System.out.println("Test equation: " + (frame.getLocationOnScreen().getY() - SIZEADJ));
				// 		System.out.println("Test easier adj: " + frame.getSize());
				// 		System.out.println("Test equiv: " + (e.getLocationOnScreen().getY() - SIZEADJ - frame.getLocationOnScreen().getY()));

				// 		//System.out.println("Test equation 2: " + (e.getYOnScreen() - frame.getLocation().getY() + frame.getLocationOnScreen().getY()));

				// 		getClick(e.getYOnScreen() - SIZEADJ - (int)frame.getLocationOnScreen().getY(), e.getXOnScreen()-e.getX());
				// 	}
				// 	public void mouseReleased(MouseEvent e) {
				// 		System.out.println("This is the release: " + e.getYOnScreen() + ", " + e.getXOnScreen());
				// 		System.out.println(e.getPoint());
				// 		getRelease(e.getYOnScreen() - SIZEADJ, e.getXOnScreen());
				// 		//getRelease(e.getYOnScreen()/*-e.getY()*/, e.getXOnScreen()/*-e.getX()*/);
				// 	}
				// 	public void mouseClicked(MouseEvent e) {
				// 		//System.out.println("mouse clicked here: y is " + e.getYOnScreen() + ", x is " + e.getXOnScreen());
				// 	}
				// 	public void mouseExited(MouseEvent e) {

				// 	}
				// 	public void mouseEntered(MouseEvent e) {

				// 	}
				// });
				if ((y+x)%2 == 0) {
					b.setBackground(Color.decode("#769656"));
				} else {
					b.setBackground(Color.decode("#eeeed2"));
				}
				imageGrid[7-y][x] = b;
				frame.add(b);
			}
		}

		frame.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				System.out.println("frame mouse pressed y: " + e.getY() + ", x: " + e.getX());
				System.out.println("frame pos on screen y: " + e.getLocationOnScreen().getY() + ", x: " + e.getLocationOnScreen().getX());
				System.out.println("get on screen screen y: " + e.getYOnScreen() + ", x: " + e.getXOnScreen());
				getClick(e.getYOnScreen(), e.getXOnScreen());
			}
			public void mouseReleased(MouseEvent e) {
				getRelease(e.getYOnScreen(), e.getXOnScreen());
			}
			public void mouseClicked(MouseEvent e) {

			}
			public void mouseEntered(MouseEvent e) {

			}
			public void mouseExited(MouseEvent e) {

			}
		});

		frame.setLayout(new GridLayout(BOARDSIZE, BOARDSIZE));
		frame.pack();
		frame.setResizable(false);
		frame.setTitle("Chess Board");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void getClick(int y, int x) {
		int sizeAdjY = (int)imageGrid[0][0].getLocationOnScreen().getY()%TILEPIXELSIZE;
		int sizeAdjX = (int)imageGrid[0][0].getLocationOnScreen().getX()%TILEPIXELSIZE;
		System.out.println("test sizeAdjY: " + sizeAdjY);
		System.out.println("test sizeAdjX: " + sizeAdjX);
		System.out.println("mouse pressed here: x is " + (x-sizeAdjX)/TILEPIXELSIZE + ", y is " + (7-((y-sizeAdjY)/TILEPIXELSIZE)));
		movePoints[0] = new Point((x-sizeAdjX)/TILEPIXELSIZE, 7-((y-sizeAdjY)/TILEPIXELSIZE));
	}

	public void getRelease(int y, int x) {
		int sizeAdjY = (int)imageGrid[0][0].getLocationOnScreen().getY()%TILEPIXELSIZE;
		int sizeAdjX = (int)imageGrid[0][0].getLocationOnScreen().getX()%TILEPIXELSIZE;
		movePoints[1] = new Point((x-sizeAdjX)/TILEPIXELSIZE, 7-((y-sizeAdjY)/TILEPIXELSIZE));
		System.out.println("mouse pressed here: x is " + (x-sizeAdjX)/TILEPIXELSIZE + ", y is " + (7-((y-sizeAdjY)/TILEPIXELSIZE)));
		if (isValidMove(movePoints)) {
			makeMove(movePoints);
		}
	}

	public Point[] getMove() {
		return movePoints;
	}

	public int getTurnCount() {
		return turnCount;
	}

	public void makeMove(Point[] p) {
		int y1 = (int)p[0].getY();
		int x1 = (int)p[0].getX();
		int y2 = (int)p[1].getY();
		int x2 = (int)p[1].getX();
		makeMove(y1, x1, y2, x2);
	}

	public void makeMove(int y1, int x1, int y2, int x2) {
		if (board[y2][x2].pieceExists()) {
			takePiece(y1, x1, y2, x2);
		} else if (isValidCastle(turnCount % 2 == 0, y1, x1, y2, x2)) {
			castle(turnCount % 2 == 0, y1, x1, y2, x2);
		} else {
			movePiece(y1, x1, y2, x2);
		}
		turnCount++;
		System.out.println(toString(false));
		updateBoard();
		//System.out.println(toString(true));
	}
	
	public String toString(boolean isWhite) {
		String temp = "";
		if (isWhite) {
			for (int y = 0; y < BOARDSIZE; y++) {
				temp += y + ". ";
				for (int x = 0; x < BOARDSIZE; x++) {
					temp += board[y][x].getPieceColor() + board[y][x].getValue() + " ";
				}
				temp += "\n";
			}
			temp += "   ";
			for (int i = 0; i < BOARDSIZE; i++) {
				temp += i + ". ";
			}
		} else {
			for (int y = BOARDSIZE-1; y >= 0; y--) {
				temp += y + ". ";
				for (int x = 0; x < BOARDSIZE; x++) {
					temp += board[y][x].getPieceColor() + board[y][x].getValue() + " ";
				}
				temp += "\n";
			}
			temp += "   ";
		 	for (int i = 0; i < BOARDSIZE; i++) {
				temp += i + ". ";
			}
		}
		return temp;
	}
	
	public boolean isCheckmated(boolean c) {
		int checkCount = 0;
		//coordinates for piece checking the king
		int checkingY = 0, checkingX = 0;
		int lowerVal = 0, higherVal = 0;
		int lowerXVal = 0, higherXVal = 0;
		int scenario = 0;
		boolean isY = false;
		int checkedKingY = bKingY;
		int checkedKingX = bKingX;
		if (c) {
			checkedKingY = wKingY;
			checkedKingX = wKingX;
		}

		// checks if the king can move out of check
		for (int y = -1; y < 2; y++){
			if (checkedKingY + y < 0 || checkedKingY + y >= BOARDSIZE) {
				continue;
			}
			for (int x = -1; x < 2; x++) {
				if (checkedKingX + x < 0 || checkedKingX + x >= BOARDSIZE) {
					continue;
				}
				if (checkKingCheck(c, checkedKingY + y, checkedKingX + x)) {
					continue;
				}
				if (!board[checkedKingY + y][checkedKingX + x].pieceExists()) {
					return false;
				} else if (c != board[checkedKingY+y][checkedKingX+x].getPiece().getColor()) {
					return false;
				}
			}
		}
		//this finds the piece checking the king, and stores its coordinates
		for (int y = 0; y < BOARDSIZE; y++) {
			for (int x = 0; x < BOARDSIZE; x++) {
				if (board[y][x].getPiece().getColor() != c) {
					if (board[y][x].canTake(y, x, checkedKingY, checkedKingX)) {
						if (!canMoveHori(y, x, checkedKingY, checkedKingX)) {
							continue;
						}
						if (!canMoveDiag(y, x, checkedKingY, checkedKingX)) {
							continue;
						}
						checkingY = y;
						checkingX = x;
						checkCount++;
					}
				}
			}
		}

		if (checkCount > 1) {
			//if the king is checked by 2 pieces, and can't move it is checkmate
			return true;
		}
		if ((Math.abs(checkingY-checkedKingY) > 1 || Math.abs(checkingX-checkedKingX) > 1) && !board[checkingY][checkingX].getPiece().getValue().equals("n")) {
			//scenario 0 is for if you are being checked by a knight
			//scenario 1 is for when you are being checked in a straight line
			//scenario 2 is for when you are being checked diagonally

			//assign higher and lower val so that later loops can check from higher value to lower value instead of having to change depending on direction
			if (checkedKingY == checkingY) {
				scenario = 1;
				higherVal = checkedKingX;
				lowerVal = checkingX;
				if (checkingX > checkedKingX) {
					higherVal = checkingX;
					lowerVal = checkedKingX;
				}
			} else if (checkedKingX == checkingX) {
				scenario = 1;
				isY = true;
				higherVal = checkedKingY;
				lowerVal = checkingY;
				if (checkingY > checkedKingY) {
					higherVal = checkingY;
					lowerVal = checkedKingX;
				}
			} else {
				higherVal = checkingY;
				lowerVal = checkedKingY;

				if (checkingY < checkedKingY) {
					higherVal = checkedKingY;
					lowerVal = checkingY;
				}

				higherXVal = checkingX;
				lowerXVal = checkedKingX;

				if (checkingX < checkedKingX) {
					higherVal = checkedKingX;
					lowerVal = checkingX;
				}
				scenario = 2;
			}
		}

		int currentStatic = 0, currentMoving = 0;
		for (int y = 0; y < BOARDSIZE; y++) {
			for (int x = 0; x < BOARDSIZE; x++) {
				if (board[y][x].getPiece().getColor() == c) {
					//if you can take the checking piece, then it is not checkmate (returns false)
					if (board[y][x].canTake(y, x, checkingY, checkingX) && !createsACheckMove(y, x, checkingY, checkingX)) {
						if (!canMoveHori(y, x, checkingY, checkingX)) {
							continue;
						}
						if (!canMoveDiag(y, x, checkingY, checkingX)) {
							continue;
						}
						return false;
					}
					if (scenario == 1) {
						Piece p = board[y][x].getPiece();
						int listLength = p.getLength();

						//the value of these variables changes whether you are being checked horizontally or vertically
						//this allows for one if statement to work for both horizontal and vertical checking
						int staticVal = y;
						int movingVal = x;
						int tempKingVal = checkedKingY;
						if (isY) {
							staticVal = x;
							movingVal = y;
							tempKingVal = checkedKingX;
						}
						for (int i = 0; i < listLength; i++) {
							currentStatic = p.getY(i);
							currentMoving = p.getX(i);
							if (isY) {
								currentStatic = p.getX(i);
								currentMoving = p.getY(i);
							}

							if (lowerVal < movingVal+currentMoving && movingVal+currentMoving > higherVal && staticVal+currentStatic == tempKingVal) {
								if (!createsACheckMove(y, x, y+p.getY(i), x+p.getX(i))) {
									if (!canMoveHori(y, x, y+p.getY(i), x+p.getX(i))) {
										continue;
									}
									if (!canMoveDiag(y, x, y+p.getY(i), x+p.getX(i))) {
										continue;
									}
									return false;
								}
								continue;
							}
						}
					} else if (scenario == 2) {
						Piece p = board[y][x].getPiece();
						int listLength = p.getLength();
						for (int i = 0; i < listLength; i++) {
							if (lowerVal < y+p.getY(i) && y+p.getY(i) < higherVal) {
								if (lowerXVal < x+p.getX(i) && x+p.getX(i) < higherXVal) {
									if (higherVal - (y+p.getY(i)) == higherXVal - (x+p.getX(i))) {
										if (!createsACheckMove(y, x, y+p.getY(i), x+p.getX(i))) {
											if (!canMoveHori(y, x, y+p.getY(i), x+p.getX(i))) {
												continue;
											}
											if (!canMoveDiag(y, x, y+p.getY(i), x+p.getX(i))) {
												continue;
											}
											return false;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean isStalemated(boolean c) {
		for (int y = 0; y < BOARDSIZE; y++) {
			for (int x = 0; x < BOARDSIZE; x++) {
				Piece p = board[y][x].getPiece();
				if (p.getColor() != c) {
					for (int i = 0 ; i < p.getLength(); i++) {
						if (y+p.getY(i) >= 0 && y+p.getY(i) < BOARDSIZE && x+p.getX(i) >= 0 && x+p.getX(i) < BOARDSIZE) {
							if (board[y+p.getY(i)][x+p.getX(i)].pieceExists()) {
								if (board[y+p.getY(i)][x+p.getX(i)].getPiece().getColor() == c && !createsACheckMove(y, x, y+p.getY(i), x+p.getX(i)) && board[y][x].getPiece().canTake(y, x, y+p.getY(i), x+p.getX(i))) {
									if (!canMoveHori(y, x, y+p.getY(i), x+p.getX(i))) {
										continue;
									}
									if (!canMoveDiag(y, x, y+p.getY(i), x+p.getX(i))) {
										continue;
									}
									return false;
								}
							} else {
								if (!createsACheckMove(y, x, y+p.getY(i), x+p.getX(i))) {
									if (canMoveHori(y, x, y+p.getY(i), x+p.getX(i))) {
										return false;
									}
									if (canMoveDiag(y, x, y+p.getY(i), x+p.getX(i))) {
										return false;
									}
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean checkPawnQueen(int y) {
		return y == 0 || y == BOARDSIZE-1;
	}
	
	public boolean checkKingCheck(boolean c) {
		if (c) {
			return checkKingCheck(c, wKingY, wKingX);
		}
		return checkKingCheck(c, bKingY, bKingX);
	}
	
	public boolean checkKingCheck(boolean c, int newY, int newX) {
		//checks if the king is under check when if it moves to newY, newX
		//kings color = c
		for (int y = 0; y < BOARDSIZE; y++) {
			for (int x = 0; x < BOARDSIZE; x++) {
				if(board[y][x].canTake(y, x, newY, newX) && board[y][x].getPiece().getColor() != c) {
					if (!canMoveDiag(y, x, newY, newX)) {
						continue;
					}
					if (!canMoveHori(y, x, newY, newX)) {
						continue;
					}
					System.out.println("checkKingCheck: True");
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkCastleCheck(boolean c, int x1, int x2) {
		//this is to check if the king will be checked on any of the squares he moves to while castling
		int y = bKingY;
		if (c) {
			y = wKingY;
		}
		if (x1 > x2) {
			int temp = x2;
			x2 = x1;
			x1 = temp;
		}
		for (int x = x1; x < x2; x++) {
			if (checkKingCheck(c, y, x))
				return true;
		}
		return false;
	}

	public boolean canMoveHori(int y1, int x1, int y2, int x2) {
		int smallestVal, highestVal;
		if (!board[y1][x1].getPiece().canHori()) {
			return true;
		}
		if (y1 != y2 && x1 != x2) {
			return true;
		}
		if (y1 == y2) {
			smallestVal = x1;
			highestVal = x2;
			if (x1 > x2) {
				smallestVal = x2;
				highestVal = x1;
			}
			for (int i = smallestVal + 1; i < highestVal; i++) {
				if (board[y1][i].pieceExists()) {
					return false;
				}
			}
		} else {
			smallestVal = y1;
			highestVal = y2;
			if (y1 > y2) {
				smallestVal = y2;
				highestVal = y1;
			}
			for (int i = smallestVal + 1; i < highestVal; i++) {
				if (board[i][x1].pieceExists()) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean canMoveDiag(int y1, int x1, int y2, int x2) {
		int smallestY = y1, smallestX = x1;
		if (!board[y1][x1].getPiece().canDiag()) {
			return true;
		}
		if (Math.abs(((1.0*y2)-y1)/((1.0*x2)-x1)) != 1) {
			return true;
		}
		if (y1 > y2) {
			smallestY = y2;
		}
		if (x1 > x2) {
			smallestX = x2;
		}
		System.out.println("lil math equation ova here: " + (y1+y2-(2*smallestY)));
		for (int i = 1; i < y1+y2-(2*smallestY); i++) {
			if (board[smallestY+i][smallestX+i].pieceExists()) {
				return false;
			}
		}
		return true;
	}

	public boolean isValidMove(int y1, int x1, int y2, int x2) {
		Point[] p = new Point[2];
		p[0] = new Point(x1, y1);
		p[1] = new Point(x2, y2);
		return isValidMove(p);
	}

	public boolean isValidMove(Point[] points) {

		int y1 = (int)points[0].getY();
		int x1 = (int)points[0].getX();
		int y2 = (int)points[1].getY();
		int x2 = (int)points[1].getX();

		if ((y1 > BOARDSIZE-1 || x1 > BOARDSIZE-1 || y2 > BOARDSIZE-1 || x2 > BOARDSIZE-1)) {
			System.out.println("Move too large");
			return false;
		}
		if ((y1 < 0 || x1 < 0 || y2 < 0 || x2 < 0)) {
			System.out.println("Move too small");
			return false;
		}
		if (!board[y1][x1].pieceExists()) {
			System.out.println("No piece exists");
			return false;
		}
		if (board[y1][x1].getPiece().getColor() != (turnCount % 2 == 0)) {
			System.out.println("Wrong color");
			return false;
		}
		if (!canMoveDiag(y1, x1, y2, x2)) {
			System.out.println("You are blocked diag");
			return false;
		}
		if (!canMoveHori(y1, x1, y2, x2)) {
			System.out.println("You are blocked hori");
			return false;
		}
		if (createsACheckMove(y1, x1, y2, x2)) {
			System.out.println("Creating a check");
			return false;
		}
		if (board[y2][x2].pieceExists()) {
			if (board[y2][x2].getPiece().getColor() == board[y1][x1].getPiece().getColor()) {
				System.out.println("Can't take same color");
				return false;
			}
			if (board[y1][x1].canTake(y1, x1, y2, x2)) {
				//add promoting function
				System.out.println("Valid take detected");
				return true;
			} else {
				System.out.println("Invalid take move");
				return false;
			}
		}	else if (isValidCastle(turnCount%2==0, y1, x1, y2, x2) && checkKingCheck(turnCount%2 == 0)) {
			castle(turnCount%2 == 0, y1, x1, y2, x2);
			return false;
		}	else if (board[y1][x1].isValidMove(y1, x1, y2, x2)) {
			//add promoting function
			System.out.println("Valid move detected");
			return true;
		}
		return false;
	}

	public void hasPawnQueened(int y1, int x1, int y2, int x2) {
		if (board[y1][x1].getPieceValue() != "p") {
			return;
		}
		System.out.println(2);
		
	}
	
	public void adjustKing(int y1, int x1, int y2, int x2) {
		boolean isWhite = board[y1][x1].getPiece().getColor();
		if (board[y1][x1].getPiece().checkKing()) {
			if (isWhite) {
				wKingY = y2;
				wKingX = x2;
			} else {
				bKingY = y2;
				bKingX = x2;
			}
		}
	}
	
	public boolean isValidCastle(boolean c, int y1, int x1, int y2, int x2) {
		int tempKingY = bKingY, tempKingX = bKingX;
		if (c) {
			tempKingY = wKingY;
			tempKingX = wKingX;
		}
		int rx = 0;
		if (x2 == x1+2) {
			rx = 7;
		}

		return y1 == tempKingY && y2 == tempKingY && x1 == tempKingX && (x2 == x1+2 || x2 == x1-2) && !board[y1][x1].getPiece().getFirstMove() && !board[y1][rx].getPiece().getFirstMove() && canMoveHori(y1, x1, y2, x2) && !checkCastleCheck(c, x1, x2);
	}
	
	public void castle(boolean c, int y1, int x1, int y2, int x2) {
		adjustKing(y1, x1, y2, x2);
		board[y2][x2].setPiece(board[y1][x1].getPiece());
		board[y1][x1].removePiece();
		board[y2][x2].p.changeMoveStatus(true);
		int rx = 0;
		int nrx = x2+1;
		if (x2 == x1+2) {
			rx = BOARDSIZE-1;
			nrx = x2-1;
		}
		board[y1][nrx].setPiece(board[y1][rx].getPiece());
		board[y1][rx].removePiece();
		board[y1][nrx].p.changeMoveStatus(true);
	}

	public void createEnPassant(int y1, int x1, int y2, int x2)  {
	}

	public boolean movePiece(int y1, int x1, int y2, int x2) {
		if (board[y1][x1].isValidMove(y1, x1, y2, x2)) {
			adjustKing(y1, x1, y2, x2);
			board[y2][x2].setPiece(board[y1][x1].getPiece());
			board[y1][x1].removePiece();
			board[y2][x2].p.changeMoveStatus(true);
			return true;
		}
		System.out.println("invalid move");
		return false;
	}
	
	public boolean takePiece(int y1, int x1, int y2, int x2) {
		if (board[y1][x1].getPiece().canTake(y1, x1, y2, x2)) {
			adjustKing(y1, x1, y2, x2);
			board[y2][x2].setPiece(board[y1][x1].getPiece());
			board[y1][x1].removePiece();
			board[y2][x2].p.changeMoveStatus(true);
			return true;
		}
		System.out.println("invalid take");
		return false;
	}

	public boolean createsACheckMove(int y1, int x1, int y2, int x2) {
		boolean checked = false;
		boolean firstMove = board[y1][x1].getPiece().getFirstMove();
		Piece p = new Piece();
		if (board[y2][x2].pieceExists()) {
			p = board[y2][x2].getPiece();
			if (!takePiece(y1, x1, y2, x2)) {
				return false;
			}
		} else {
			if (!movePiece(y1, x1, y2, x2)) {
				return false;
			}
		}
		if (checkKingCheck(board[y2][x2].getPiece().getColor())) {
			checked = true;
		}
		board[y1][x1].setPiece(board[y2][x2].getPiece());
		board[y2][x2].setPiece(p);
		board[y1][x1].getPiece().changeMoveStatus(firstMove);

		//This code only runs if the piece detected is a king, so it is ok to use without checking
		adjustKing(y2, x2, y1, x1);
		return checked;
	}

	public void makePiecesTop() {
	//white pieces
		Piece p;
		Piece n = new Knight(true);
		Piece b = new Bishop(true);
		Piece r = new Rook(true);
		Piece q = new Queen(true);
		Piece k = new King(true);
		for (int i = 0; i < BOARDSIZE; i++) {
			p = new Pawn(true);
			board[1][i].setPiece(p);
		}
		board[0][0].setPiece(r);
		board[0][BOARDSIZE-1].setPiece(r);
		board[0][1].setPiece(n);
		board[0][BOARDSIZE-1-1].setPiece(n);
		board[0][2].setPiece(b);
		board[0][BOARDSIZE-2-1].setPiece(b);
		board[0][4].setPiece(k);
		wKingX = 4;
		wKingY = 0;
		board[0][BOARDSIZE-5].setPiece(q);
	}
	
	public void makePiecesBot() {
		Piece p;
		Piece n = new Knight(false);
		Piece b = new Bishop(false);
		Piece r = new Rook(false);
		Piece q = new Queen(false);
		Piece k = new King(false);
		for (int i = 0; i < BOARDSIZE; i++) {
			p = new Pawn(false);
			board[BOARDSIZE-1-1][i].setPiece(p);
		}
		board[BOARDSIZE-1][0].setPiece(r);
		board[BOARDSIZE-1][BOARDSIZE-1].setPiece(r);
		board[BOARDSIZE-1][1].setPiece(n);
		board[BOARDSIZE-1][BOARDSIZE-1-1].setPiece(n);
		board[BOARDSIZE-1][2].setPiece(b);
		board[BOARDSIZE-1][BOARDSIZE-2-1].setPiece(b);
		board[BOARDSIZE-1][3].setPiece(q);
		board[BOARDSIZE-1][BOARDSIZE-3-1].setPiece(k);
		bKingY = BOARDSIZE-1;
		bKingX = BOARDSIZE-3-1;
	}
}