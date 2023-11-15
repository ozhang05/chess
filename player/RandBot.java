package player;

import java.util.*;
import java.awt.Point;

import framework.Board;
import pieces.Piece;

public class RandBot {
    HashMap<Point, Point> moveList = new HashMap<Point, Point>();
    ArrayList<Point[]> moveList2 = new ArrayList<Point[]>();

    public void genMoveList(Board b) {
        boolean c = b.getTurnCount()%2 == 0;
        for (int y = 0; y < Board.BOARDSIZE; y++) {
            for (int x = 0; x < Board.BOARDSIZE; x++) {
                Piece p = b.board[y][x].getPiece();
                if (!b.board[y][x].pieceExists()) {
                    continue;
                } else if (p.getColor() != c) {
                    continue;
                } else {
                    for (int i = 0; i < p.getLength(); i++) {
                        if (y+p.getY(i) < 0 || y+p.getY(i) < Board.BOARDSIZE) {
                            continue;
                        }
                        if (x+p.getX(i) < 0 || x+p.getX(i) < Board.BOARDSIZE) {
                            continue;
                        }
                        if (b.board[y+p.getY(i)][x+p.getX(i)].pieceExists()) {
                            if (b.board[y+p.getY(i)][x+p.getX(i)].getPiece().getColor() == b.board[y][x].getPiece().getColor()) {
                                continue;
                            }
                            if (!b.board[y][x].getPiece().canTake(y, x, y+p.getY(i), x+p.getX(i))) {
                                continue;
                            }
                            Point[] temp = {new Point(x, y), new Point(x+p.getX(i), y+p.getY(i))};
                            moveList2.add(temp);
                        } else {
                            
                        }
                        if (b.isValidMove(y, x, b.board[y][x].getPiece().getY(i), b.board[y][x].getPiece().getX(i))) {
                            //int temp = y >> 2;
                        }
                        
                    }
                }
            }
        }
    }

    public Point[] getMove(Board b) {
        int random = new Random().nextInt(moveList.size());
        return moveList2.get(random);
    }
}
