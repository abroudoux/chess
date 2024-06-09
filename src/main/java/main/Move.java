package main;

import pieces.Piece;

public class Move {
    int oldCol;
    int oldRow;
    int newCol;
    int newRow;

    Piece piece;
    Piece capture;

    public Move(Board board, Piece piece, int newRow, int newCol) {
        this.oldRow = piece.row;
        this.oldCol = piece.col;
        this.newRow = newRow;
        this.newCol = newCol;
        this.piece = piece;
        this.capture = board.getPiece(newRow, newCol);
    }
}
