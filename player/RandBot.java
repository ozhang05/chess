package player;

import java.util.*;
import java.awt.Point;

import framework.Board;
import pieces.Piece;

public class RandBot {
    ArrayList<Point> moveList = new ArrayList<Point>();
    ArrayList<Integer> moveList2 = new ArrayList<Integer>();

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

    public Point getMove(Board b) {
        int random = new Random().nextInt(moveList.size());
        return moveList.get(random);
    }
}
