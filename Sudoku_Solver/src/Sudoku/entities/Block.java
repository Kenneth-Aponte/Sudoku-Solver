package Sudoku.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Main.Handler;

public class Block {
	public boolean isSelected = false;
	public Handler handler;
	public int x,y,width,height;
	public int value = 0;
	
	public Block(int x, int y, Handler handler) {
		this.x = x;
		this.y = y;
		width = handler.getHeight()/18;
		height = width;
		this.handler = handler;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.lightGray);
		if(isSelected) {
			g.fillRect(x, y, width, height);
		}
		if(value > 0) {
			if(isSelected) {
				g.setColor(Color.white);
			}
			else {
				g.setColor(Color.black);
			}
			g.setFont(new Font("Calibri", 0, width));
			g.drawString(String.valueOf(value), x+width/7, y+height-height/7);
		}
	}
	
	public void addInput(int input) {//a setter for value
		value = input;
	}
}
