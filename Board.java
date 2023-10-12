public class Board {
	public static final int boardSize = 8;
	public Tile[][] board = new Tile[boardSize][boardSize];
	int wKingX, wKingY;
	int bKingX, bKingY;
	
	Board() {
		for (int y = 0; y < boardSize; y++) {
			for (int x = 0; x < boardSize; x++) {
				board[y][x] = new Tile();
			}
		}
		makePiecesTop();
		makePiecesBot();
	}
	
	public String toString(boolean isWhite) {
		String temp = "";
		if (isWhite) {
			for (int y = 0; y < boardSize; y++) {
				temp += y + ". ";
				for (int x = 0; x < boardSize; x++) {
					temp += board[y][x].getPieceLetter() + board[y][x].getValue() + " ";
				}
				temp += "\n";
			}
			temp += "   ";
			for (int i = 0; i < boardSize; i++) {
				temp += i + ". ";
			}
		} else {
			for (int y = boardSize-1; y >= 0; y--) {
				temp += y + ". ";
				for (int x = 0; x < boardSize; x++) {
					temp += board[y][x].getPieceLetter() + board[y][x].getValue() + " ";
				}
				temp += "\n";
			}
			temp += "   ";
		 	for (int i = 0; i < boardSize; i++) {
				temp += i + ". ";
			}
		}
	return temp;
	}
	
	public boolean isCheckmated(boolean c) {
		int checkCount = 0;
		int checkingY = 0, checkingX = 0;
		int lowerVal = 0, higherVal = 0;
		int scenario = 0;
		//int tempKingY = 0, tempKingX = 0;
		boolean isY = false;
		int checkedKingY = bKingY;
		int checkedKingX = bKingX;
		if (c) {
			checkedKingY = wKingY;
			checkedKingX = wKingX;
		}

		// checks if the king can move out of check
		for (int y = -1; y < 2; y++){
			if (checkedKingY + y >= 0 && checkedKingY + y < boardSize) {
				for (int x = -1; x < 2; x++) {
					if (checkedKingX + x >= 0 && checkedKingX + x < boardSize) {
						if (!checkKingCheck(c, checkedKingY + y, checkedKingX + x)) {
							if (board[checkedKingY + y][checkedKingX + x].pieceExists()) {
								if (c != board[checkedKingY+y][checkedKingX+x].getPiece().getColor()) {
									return false;
								}
							} else {
								return false;
							}
						}
					}
				}
			}
		}
		for (int y = 0; y < boardSize; y++) {
			for (int x = 0; x < boardSize; x++) {
				if (board[y][x].getPiece().getColor() != c) {
					if (board[y][x].canTake(y, x, checkedKingY, checkedKingX)) {
						if (board[y][x].getPiece().canHori() && isHoriMove(y, x, checkedKingY, checkedKingX) && !canMoveHori(y, x, checkedKingY, checkedKingX)) {
							continue;
						}
						if (board[y][x].getPiece().canDiag() && isDiagMove(y, x, checkedKingY, checkedKingX) && !canMoveDiag(y, x, checkedKingY, checkedKingX)) {
							continue;
						}
						checkingY = y;
						checkingX = x;
						System.out.println(y + ", " + x);
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

			//assign higher and lower val so that later loops can check from higher value to lower value instead of having to change depending on situation
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
				if (checkingY > checkedKingY) {
					// tempKingX = checkingX;
					// tempKingY = checkingY;
					// checkingX = usedKingX;
					// checkingY = usedKingY;
				}
				scenario = 2;
			}
		}

		int currentStatic = 0, currentMoving = 0;
		for (int y = 0; y < boardSize; y++) {
			for (int x = 0; x < boardSize; x++) {
				if (board[y][x].getPiece().getColor() == c) {
					//if you can take the checking piece, then it is not checkmate (returns false)
					if (board[y][x].canTake(y, x, checkingY, checkingX) && !createsACheckMove(y, x, checkingY, checkingX)) {
						if (board[y][x].getPiece().canHori() && isHoriMove(y, x, checkingY, checkingX) && !canMoveHori(y, x, checkingY, checkingX)) {
							continue;
						}
						if (board[y][x].getPiece().canDiag() && isDiagMove(y, x, checkingY, checkingX) && !canMoveDiag(y, x, checkingY, checkingX)) {
							continue;
						}
						return false;
					}
					if (scenario == 1) {
						System.out.println("system 1");
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
									if (board[y][x].getPiece().canHori() && isHoriMove(y, x, y+p.getY(i), x+p.getX(i)) && !canMoveHori(y, x, y+p.getY(i), x+p.getX(i))) {
										continue;
									}
									if (board[y][x].getPiece().canDiag() && isDiagMove(y, x, y+p.getY(i), x+p.getX(i)) && !canMoveDiag(y, x, y+p.getY(i), x+p.getX(i))) {
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
						boolean b = checkingX < tempKingX;
						for (int i = 0; i < listLength; i++) {
							for (int t = lowerVal; t < higherVal; t++) {
								if (b) {
									if (y+p.getY(i) > checkingY && y+p.getY(i) < tempKingY) {
										int temp = tempKingY - (y+p.getY(i));
										if (x+temp == x+p.getX(i)) {
											if (!createsACheckMove(y, x, y+p.getY(i), x+p.getX(i))) {
												if (board[y][x].getPiece().canHori() && isHoriMove(y, x, y+p.getY(i), x+p.getX(i)) && !canMoveHori(y, x, y+p.getY(i), x+p.getX(i))) {
													System.out.println("checkmate test1");
													continue;
												}
												if (board[y][x].getPiece().canDiag() && isDiagMove(y, x, y+p.getY(i), x+p.getX(i)) && !canMoveDiag(y, x, y+p.getY(i), x+p.getX(i))) {
													System.out.println("checkmate test2");
													continue;
												}
												System.out.println("gets here44");
												return false;
											}
											System.out.println("checkmate test3");
											continue;
										}
									}
								} else {
									if (y+p.getY(i) > checkingY && y+p.getY(i) < tempKingY) {
										int temp = tempKingY - (y+p.getY(i));
										if (x-temp == x+p.getX(i)) {
											if (!createsACheckMove(y, x, y+p.getY(i), x+p.getX(i))) {
												if (board[y][x].getPiece().canHori() && isHoriMove(y, x, y+p.getY(i), x+p.getX(i)) && !canMoveHori(y, x, y+p.getY(i), x+p.getX(i))) {
													continue;
												}
												if (board[y][x].getPiece().canDiag() && isDiagMove(y, x, y+p.getY(i), x+p.getX(i)) && !canMoveDiag(y, x, y+p.getY(i), x+p.getX(i))) {
													continue;
												}
												System.out.println("gets here55");
												return false;
											}
											continue;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println("scenario 0");
		return true;
	}
	
	public boolean isStalemated(boolean c) {
		for (int y = 0; y < boardSize; y++) {
			for (int x = 0; x < boardSize; x++) {
				Piece p = board[y][x].getPiece();
				if (p.getColor() != c) {
					for (int i = 0 ; i < p.getLength(); i++) {
						if (y+p.getY(i) >= 0 && y+p.getY(i) < boardSize && x+p.getX(i) >= 0 && x+p.getX(i) < boardSize) {
							if (board[y+p.getY(i)][x+p.getX(i)].pieceExists()) {
								if (board[y+p.getY(i)][x+p.getX(i)].getPiece().getColor() == c && !createsACheckTake(y, x, y+p.getY(i), x+p.getX(i)) && board[y][x].getPiece().canTake(y, x, y+p.getY(i), x+p.getX(i))) {
									if (board[y][x].getPiece().canHori() && isHoriMove(y, x, y+p.getY(i), x+p.getX(i)) && !canMoveHori(y, x, y+p.getY(i), x+p.getX(i))) {
										continue;
									}
									if (board[y][x].getPiece().canDiag() && isDiagMove(y, x, y+p.getY(i), x+p.getX(i)) && !canMoveDiag(y, x, y+p.getY(i), x+p.getX(i))) {
										continue;
									}
									return false;
								}
								//can take own pieces
							} else {
								if (!createsACheckMove(y, x, y+p.getY(i), x+p.getX(i))) {
									if (p.canHori() && isHoriMove(y, x, y+p.getY(i), x+p.getX(i)) && canMoveHori(y, x, y+p.getY(i), x+p.getX(i))) {
										return false;
									}
									if (p.canDiag() && isDiagMove(y, x, y+p.getY(i), x+p.getX(i)) && canMoveDiag(y, x, y+p.getY(i), x+p.getX(i))) {
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
		return y == 0 || y == boardSize-1;
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
		for (int y = 0; y < boardSize; y++) {
			for (int x = 0; x < boardSize; x++) {
				if(board[y][x].canTake(y, x, newY, newX) && !(board[y][x].getPiece().getColor() == c)) {
					if (board[y][x].getPiece().canDiag() && isDiagMove(y, x, newY, newX) && !canMoveDiag(y, x, newY, newX)) {
						continue;
					}
					if (board[y][x].getPiece().canHori() && isHoriMove(y, x, newY, newX) && !canMoveHori(y, x, newY, newX)) {
						continue;
					}
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
		if (y1 == y2) {
		if (x1 > x2) {
			int temp = x1;
			x1 = x2;
			x2 = temp;
		}
		for (int i = x1+1; i < x2; i++) {
			if (board[y1][i].pieceExists()) {
				return false;
			}
		}
		} else if (x1 == x2) {
		if (y1 > y2) {
			int temp = y1;
			y1 = y2;
			y2 = temp;
		}
		for (int i = y1+1; i < y2; i++) {
			if (board[i][x1].pieceExists()) {
			return false;
			}
		}
		}
		return true;
	}

	public boolean canMoveDiag(int y1, int x1, int y2, int x2) {
		if (Math.abs(((1.0*y2)-y1)/((1.0*x2)-x1)) == 1) {
			if (y1 > y2) {
				int temp = y1;
				y1 = y2;
				y2 = temp;
				int temp2 = x1;
				x1 = x2;
				x2 = temp2;
			}
			for (int i = 1; i < y2-y1; i++) {
				if (x2 > x1) {
					if (board[y1+i][x1+i].pieceExists()) {
						return false;
					}
				} else {
					if (board[y1+i][x1-i].pieceExists()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean isHoriMove(int y1, int x1, int y2, int x2) {
		return y1==y2 || x1==x2;
	}
	
	public boolean isDiagMove(int y1, int x1, int y2, int x2) {
		return Math.abs(((1.0*y2)-y1)/((1.0*x2)-x1)) == 1;
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
			rx = boardSize-1;
			nrx = x2-1;
		}
		board[y1][nrx].setPiece(board[y1][rx].getPiece());
		board[y1][rx].removePiece();
		board[y1][nrx].p.changeMoveStatus(true);
	}

	public boolean movePiece(int y1, int x1, int y2, int x2) {
		adjustKing(y1, x1, y2, x2);
		if (board[y1][x1].isValidMove(y1, x1, y2, x2)) {
			board[y2][x2].setPiece(board[y1][x1].getPiece());
			board[y1][x1].removePiece();
			board[y2][x2].p.changeMoveStatus(true);
			return true;
		} else {
			System.out.println("invalid move");
		return false;
		}
	}
	
	public boolean takePiece(int y1, int x1, int y2, int x2) {
		adjustKing(y1, x1, y2, x2);
		if (board[y1][x1].getPiece().canTake(y1, x1, y2, x2)) {
			board[y2][x2].setPiece(board[y1][x1].getPiece());
			board[y1][x1].removePiece();
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
			takePiece(y1, x1, y2, x2);
		} else {
			movePiece(y1, x1, y2, x2);
		}
		if (checkKingCheck(board[y2][x2].getPiece().getColor())) {
			checked = true;
		}
		board[y1][x1].setPiece(board[y2][x2].getPiece());
		board[y2][x2].setPiece(p);
		board[y1][x1].getPiece().changeMoveStatus(firstMove);
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
		for (int i = 0; i < boardSize; i++) {
			p = new Pawn(true);
			board[1][i].setPiece(p);
		}
		board[0][0].setPiece(r);
		board[0][boardSize-1].setPiece(r);
		board[0][1].setPiece(n);
		board[0][boardSize-1-1].setPiece(n);
		board[0][2].setPiece(b);
		board[0][boardSize-2-1].setPiece(b);
		board[0][4].setPiece(k);
		wKingX = 4;
		wKingY = 0;
		board[0][boardSize-5].setPiece(q);
		//below is used to test stalemate. uncomment out below
		// board[7][2].setPiece(q);
		// board[3][7].setPiece(new Pawn(true));
	}
	
	public void makePiecesBot() {
		Piece p;
		Piece n = new Knight(false);
		Piece b = new Bishop(false);
		Piece r = new Rook(false);
		Piece q = new Queen(false);
		Piece k = new King(false);
		for (int i = 0; i < boardSize; i++) {
			p = new Pawn(false);
			board[boardSize-1-1][i].setPiece(p);
		}
		board[boardSize-1][0].setPiece(r);
		board[boardSize-1][boardSize-1].setPiece(r);
		board[boardSize-1][1].setPiece(n);
		board[boardSize-1][boardSize-1-1].setPiece(n);
		board[boardSize-1][2].setPiece(b);
		board[boardSize-1][boardSize-2-1].setPiece(b);
		board[boardSize-1][3].setPiece(q);
		board[boardSize-1][boardSize-3-1].setPiece(k);
		bKingY = boardSize-1;
		bKingX = boardSize-3-1;
		//below is used to test stalemate. uncomment out below, comment out above, and move white queen with 7 2 5 4 to stalemate.
		// board[7][7].setPiece(r);
		// board[7][6].setPiece(n);
		// board[7][5].setPiece(b);
		// board[6][7].setPiece(q);
		// board[6][6].setPiece(new Pawn(false));
		// board[6][4].setPiece(new Pawn(false));
		// board[5][7].setPiece(r);
		// board[5][6].setPiece(k);
		// bKingY = 5;
		// bKingX = 6;
		// board[5][5].setPiece(new Pawn(false));
		// board[4][7].setPiece(new Pawn(false));
	}
	}