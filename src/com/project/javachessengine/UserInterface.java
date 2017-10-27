package com.project.javachessengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.event.*;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener{
	
	static int x=0,y=0;
	public void paintComponent(Graphics g ){
		super.paintComponent(g);
		this.setBackground(Color.yellow);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		g.setColor(Color.BLUE);
		g.fillRect(x-20, y-20, 40, 40);
		g.setColor(Color.RED);
		g.fillRect(40, 20, 80, 50);
		//g.drawString("vaibhav", x, y);
		Image chessPieceImage = new ImageIcon("ChessPieces.png").getImage();
		g.drawImage(chessPieceImage, x, y,x+64,x+64,0,0,64,64,this);
	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
