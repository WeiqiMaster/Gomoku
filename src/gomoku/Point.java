package gomoku;

import java.awt.*;

/* The Point class (for the chess pieces)*/
public class Point
{
	private int x;//x coordinate
	private int y;// y coordinate
	private Color color;//color of the chess piece
	public static int DIAMETER = 30;// diameter of the chess piece

	//Constructor
	public Point(int x, int y, Color color)
	{
		this.x = x;
		this.y = y;
		this.color = color;
	}

	//get the X coordinate
	public int getX()
	{
		return x;
	}

	// get the y coordinate
	public int getY()
	{
		return y;
	}

	//get the color
	public Color getColor()
	{
		return color;
	}
	
	public String toString()
	{// used for debug
		return this.getX()+","+this.getY();
	}
}
