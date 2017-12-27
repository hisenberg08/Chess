package com.project.javachessengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.event.*;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener{
	
	static int x=0,y=0;
	static int squareSize=32;
	static int mouseX, mouseY, newMouseX, newMouseY;
	public void paintComponent(Graphics g ){
		super.paintComponent(g);
		this.setBackground(Color.yellow);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		for (int i=0;i<64;i+=2) {
            g.setColor(new Color(255,200,100));
            g.fillRect((i%8+(i/8)%2)*squareSize, (i/8)*squareSize, squareSize, squareSize);
            g.setColor(new Color(150,50,30));
            g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, ((i+1)/8)*squareSize, squareSize, squareSize);
        }
        Image chessPiecesImage;
        chessPiecesImage=new ImageIcon("ChessPieces.png").getImage();
        for (int i=0;i<64;i++) {
            int j=-1,k=-1;
            switch (AlphaBetaChess.chessBoard[i/8][i%8]) {
                case "P": j=5; k=0;
                    break;
                case "p": j=5; k=1;
                    break;
                case "R": j=2; k=0;
                    break;
                case "r": j=2; k=1;
                    break;
                case "K": j=4; k=0;
                    break;
                case "k": j=4; k=1;
                    break;
                case "B": j=3; k=0;
                    break;
                case "b": j=3; k=1;
                    break;
                case "Q": j=1; k=0;
                    break;
                case "q": j=1; k=1;
                    break;
                case "A": j=0; k=0;
                    break;
                case "a": j=0; k=1;
                    break;
            }
            if (j!=-1 && k!=-1) {
                g.drawImage(chessPiecesImage, (i%8)*squareSize, (i/8)*squareSize, (i%8+1)*squareSize, (i/8+1)*squareSize, j*64, k*64, (j+1)*64, (k+1)*64, this);
            }
        }
	
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		x = e.getX();
		y = e.getY();
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	 if (e.getX()<8*squareSize &&e.getY()<8*squareSize) {
            //if inside the board
            mouseX=e.getX();
            mouseY=e.getY();
            repaint();
        }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		 if (e.getX()<8*squareSize &&e.getY()<8*squareSize) {
	            //if inside the board
	            newMouseX=e.getX();
	            newMouseY=e.getY();
	            if (e.getButton()==MouseEvent.BUTTON1) {
	                String dragMove;
	                if (newMouseY/squareSize==0 && mouseY/squareSize==1 && "P".equals(AlphaBetaChess.chessBoard[mouseY/squareSize][mouseX/squareSize])) {
	                    //pawn promotion
	                    dragMove=""+mouseX/squareSize+newMouseX/squareSize+AlphaBetaChess.chessBoard[newMouseY/squareSize][newMouseX/squareSize]+"QP";
	                } else {
	                    //regular move
	                    dragMove=""+mouseY/squareSize+mouseX/squareSize+newMouseY/squareSize+newMouseX/squareSize+AlphaBetaChess.chessBoard[newMouseY/squareSize][newMouseX/squareSize];
	                }
	                String userPosibilities=AlphaBetaChess.possibleMoves();
	                if (userPosibilities.replaceAll(dragMove, "").length()<userPosibilities.length()) {
	                    //if valid move
	                    AlphaBetaChess.makeMove(dragMove);
	                    AlphaBetaChess.flipBoard();
	                    AlphaBetaChess.makeMove(AlphaBetaChess.alphaBeta(AlphaBetaChess.globalDepth, 1000000, -1000000, "", 0));
	                    AlphaBetaChess.flipBoard();
	                    repaint();
	                }
	            }
	      }
	}
}
