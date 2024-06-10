package main;

import pieces.Knight;
import pieces.Piece;
import pieces.Bishop;
import pieces.Queen;
import pieces.King;
import pieces.Pawn;
import pieces.Rook;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    public int titleSize = 80;

    int cols = 8;
    int rows = 8;

    ArrayList<Piece> pieceList = new ArrayList<>();
    public Piece selectedPiece;
    Input input = new Input(this);
    boolean whiteTurn = true;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                g2d.setColor((c + r) % 2 == 0 ? new Color(227, 198, 181) : new Color(157, 105, 53));
                g2d.fillRect(c * titleSize, r * titleSize, titleSize, titleSize);
            }

        /*
        if (selectedPiece != null)
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                if (isValidMove(new Move(this, selectedPiece, c, r))) {
                    g2d.setColor(new Color(0, 255, 0, 100));
                    g2d.fillRect(c * titleSize, r * titleSize, titleSize, titleSize);
                }
            }
        **/

        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }
    }

    public Piece getPiece(int col, int row) {
        for (Piece piece : pieceList) {
            if (piece.row == row && piece.col == col) {
                return piece;
            }
        }
        return null;
    }

    public boolean isValidMove(Move move, Piece selectedPiece) {
        if (move.newRow < 0 || move.newRow >= rows || move.newCol < 0 || move.newCol >= cols) {
            return false;
        }

        if (move.newCol == move.oldCol && move.newRow == move.oldRow) {
            return false;
        }

        if (selectedPiece.isWhite != whiteTurn) {
            return false;
        }

        whiteTurn = !whiteTurn;
        setTitle(whiteTurn);
        return !sameTeam(move.piece, move.capture);
    }

    public void makeMove(Move move) {
        move.piece.row = move.newRow;
        move.piece.col = move.newCol;
        move.piece.xPos = move.newCol * titleSize;
        move.piece.yPos = move.newRow * titleSize;

        capture(move);
    }

    public void capture(Move move) {
        pieceList.remove(move.capture);
    }

    public boolean sameTeam(Piece piece1, Piece piece2) {
        if (piece1 == null || piece2 == null) {
            return false;
        }
        return piece1.isWhite == piece2.isWhite;
    }

    public void addPiece() {
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));

        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));

        pieceList.add(new Queen(this, 3, 0, false));
        pieceList.add(new Queen(this, 3, 7, true));

        pieceList.add(new King(this, 4, 0, false));
        pieceList.add(new King(this, 4, 7, true));

        for (int i = 0; i < cols; i++) {
            pieceList.add(new Pawn(this, i, 1, false));
            pieceList.add(new Pawn(this, i, 6, true));
        }

        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));
        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));
    }

    public Board() {
        this.setPreferredSize(new Dimension(cols * titleSize, rows * titleSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        addPiece();
    }

    public void setTitle(boolean whiteTurn) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (whiteTurn) {
            frame.setTitle("White's Turn !");
        } else {
            frame.setTitle("Black's Turn !");
        }
    }
 }
