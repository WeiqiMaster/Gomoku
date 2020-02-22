package gomoku;

import java.util.ArrayList;

/* The Player class*/
public class Player
{
	private int score;// the score property
	private String name;// the name property
	ArrayList<Point> myChessPieces;// the arraylist that stores all the chess pieces of this player (Point).
	private int chessCount;// the chessCount property. When it reaches 5 or greater, the player wins that game.

	//Constructor with player's name
	public Player(String name)
	{
		// Initialize properties of this player
		this.score = 0;
		this.name = name;
		this.myChessPieces = new ArrayList<>();
		this.chessCount = 1;
	}

	//second constructor without player's name
	public Player(int id)
	{
		// Initialize properties of this player
		this.score = 0;
		this.name = "player" + Integer.toString(id);
		this.myChessPieces = new ArrayList<>();
		this.chessCount = 1;
	}

	//get this player's score
	public int getScore()
	{
		return this.score;
	}

	//get this player's score
	public void addScore(int s)
	{
		this.score = this.score + s;
	}

	// get the chessCount
	public int getChessCount()
	{
		return this.chessCount;
	}

	// reset the properties if this player.
	public void reset()
	{
		this.chessCount = 1;
		this.myChessPieces.clear();
	}

	// reset the chessCount to 1.
	public void resetChessCount()
	{
		this.chessCount = 1;
	}

	//get this player's score
	public String getName()
	{
		return this.name;
	}

	// check if two chess pieces are connected. Only used in the countChess method.
	private boolean checkPosition(int index1, int index2, int xSkew, int ySkew)
	{
		return (this.myChessPieces.get(index1).getX() == this.myChessPieces.get(index2).getX() + xSkew)
				&& (this.myChessPieces.get(index1).getY() == this.myChessPieces.get(index2).getY() + ySkew);
	}

	// count the  chess pieces by directions. 1-8 represents 8 different directions. Recursions are used in this function
	public void countChess(int direction, int indexOfChess)
	{
		if (direction == 0)
		{
			for (int i = 0; i < myChessPieces.size(); i++)
			{
				if (checkPosition(indexOfChess, i, 1, 1))
				{// '1' represents left top
					chessCount++;
					countChess(1, i);//recursion using the left top direction and current chess piece.
					countChess(8, indexOfChess);//recursion using the right bottom direction and current chess piece.
					break;
				} else if (checkPosition(indexOfChess, i, -1, -1))
				{// right bottom
					chessCount++;
					countChess(8, i);// recursion using the right bottom direction and current chess piece.
					break;
				}

				if (checkPosition(indexOfChess, i, 0, 1))
				{// top
					chessCount++;
					countChess(2, i);
					countChess(7, indexOfChess);
					break;
				} else if (checkPosition(indexOfChess, i, 0, -1))
				{// bottom
					chessCount++;
					countChess(7, i);
					break;
				}

				if (checkPosition(indexOfChess, i, -1, 1))
				{//right top
					chessCount++;
					countChess(3, i);
					countChess(6, indexOfChess);
					break;
				} else if (checkPosition(indexOfChess, i, 1, -1))
				{// left bottom
					chessCount++;
					countChess(6, i);
					break;
				}

				if (checkPosition(indexOfChess, i, 1, 0))
				{// left
					chessCount++;
					countChess(4, i);
					countChess(5, indexOfChess);
					break;
				} else if (checkPosition(indexOfChess, i, -1, 0))
				{// right
					chessCount++;
					countChess(5, i);
					break;
				}

			}
		} else if (direction == 1)
		{// left top
			for (int j = 0; j < myChessPieces.size(); j++)
			{
				if (checkPosition(indexOfChess, j, 1, 1))
				{
					chessCount++;
					countChess(1, j); //recursion using the current direction and current chess piece.
					break;
				}
			}
		} else if (direction == 2)
		{// top
			for (int j = 0; j < myChessPieces.size(); j++)
			{
				if (checkPosition(indexOfChess, j, 0, 1))
				{
					chessCount++;
					countChess(2, j);
					break;
				}
			}
		} else if (direction == 3)
		{//right top
			for (int j = 0; j < myChessPieces.size(); j++)
			{
				if (checkPosition(indexOfChess, j, -1, 1))
				{
					chessCount++;
					countChess(3, j);
					break;
				}
			}
		} else if (direction == 4)
		{// left
			for (int j = 0; j < myChessPieces.size(); j++)
			{
				if (checkPosition(indexOfChess, j, 1, 0))
				{
					chessCount++;
					countChess(4, j);
					break;
				}
			}
		} else if (direction == 5)
		{// right
			for (int j = 0; j < myChessPieces.size(); j++)
			{
				if (checkPosition(indexOfChess, j, -1, 0))
				{
					chessCount++;
					countChess(5, j);
					break;
				}
			}
		} else if (direction == 6)
		{// left bottom
			for (int j = 0; j < myChessPieces.size(); j++)
			{
				if (checkPosition(indexOfChess, j, 1, -1))
				{
					chessCount++;
					countChess(6, j);
					break;
				}
			}
		} else if (direction == 7)
		{// bottom
			for (int j = 0; j < myChessPieces.size(); j++)
			{
				if (checkPosition(indexOfChess, j, 0, -1))
				{
					chessCount++;
					countChess(7, j);
					break;
				}
			}
		} else if (direction == 8)
		{// right bottom
			for (int j = 0; j < myChessPieces.size(); j++)
			{
				if (checkPosition(indexOfChess, j, -1, -1))
				{
					chessCount++;
					countChess(8, j);
					break;
				}
			}
		}
	}
}
