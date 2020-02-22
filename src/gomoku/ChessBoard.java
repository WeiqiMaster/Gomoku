package gomoku;

import javax.swing.*;

import gomoku.Point;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/* Gomoku-chess board*/
public class ChessBoard extends JPanel implements MouseListener
{
	private static final long serialVersionUID = 1L;
	public final int MARGIN = 30;// the margin of the chess board.
	public final int GRID_SPAN = 35;// the span of the grid
	public final int ROWS = 15;// number of rows
	public final int COLS = 15;// number of columns
	int xIndex, yIndex;// the coordinate of current chess piece.
	int chessCount = 0;// indicates how many chess pieces are there on the chess board.
	Point[] chessList = new Point[(ROWS + 1) * (COLS + 1)];// instantiate a new chessList which can have 16*16 chess pieces in it.
	public Player player1, player2;// static member player1, player2.
	private boolean whoseTurn = true;// starts with the first player (Black)
	ArrayList<String> playerName = new ArrayList<>();// arraylist that stores all the players' name
	ArrayList<Integer> playerScore = new ArrayList<>();// arraylist that stores all the players' score

	/* Constructor */
	public ChessBoard()
	{
		setBackground(Color.LIGHT_GRAY);// set background
		addMouseListener(this);// add mouseListener.
		try
		{
			FileReader fr = new FileReader("Registration.txt");
			BufferedReader br = new BufferedReader(fr);
			String name1 = br.readLine();
			if (name1 == null)
			{// when there is nothing in the Registration.txt.
				// Use the constructor without name.
				player1 = new Player(1);
				player2 = new Player(2);
			} else
			{// when there are some letters in the Registration.txt
				// Use the constructor with player's name.
				player1 = new Player(name1);
				player2 = new Player(br.readLine());
			}
			playerName.add(player1.getName());
			playerName.add(player2.getName());
			playerScore.add(player1.getScore());
			playerScore.add(player2.getScore());// add two players' info to arraylists playerName and playerScore
			getScoreBoard();// add players' info of the local score board to arraylists playerName and playerScore

			br.close();//close bufferedReader.

		} catch (Exception e)
		{//being invoked when users do not finish the registration
			JOptionPane.showMessageDialog(null, "ERROR: Can not found Players' name.\nDefault names will be used.",
					"ERROR", JOptionPane.ERROR_MESSAGE);
		}
		addMouseMotionListener(new MouseMotionListener()
		{// anonymous inner class

			// change the shape of the cursor when it moved on the chess board.
			@Override
			public void mouseMoved(MouseEvent e)
			{
				int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;// get the coordinates of the mouse.
				if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || findChess(x1, y1))
				{// when the cursor is out of the chess board or there is already a chess piece where the cursor points at.
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// default cursor shape.
				} else
				{
					setCursor(new Cursor(Cursor.HAND_CURSOR));// set the shape of the cursor to hand.
				}
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
			}
		});
	}

	/* Draw */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);// draw the panel (chess board).
		for (int i = 0; i <= ROWS; i++)
		{// draw rows
			g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLS * GRID_SPAN, MARGIN + i * GRID_SPAN);
		}
		for (int i = 0; i <= COLS; i++)
		{// draw columns
			g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + ROWS * GRID_SPAN);
		}

		// draw the chess pieces
		for (int i = 0; i < chessCount; i++)
		{
			//get the coordinates of the chess piece
			int xPos = chessList[i].getX() * GRID_SPAN + MARGIN;
			int yPos = chessList[i].getY() * GRID_SPAN + MARGIN;
			g.setColor(chessList[i].getColor());// get and set the color of this chess piece.
			g.fillOval(xPos - Point.DIAMETER / 2, yPos - Point.DIAMETER / 2, Point.DIAMETER, Point.DIAMETER);
			if (i == chessCount - 1)
			{
				g.setColor(Color.red);// mark the last chess piece as red
				// draw the chess piece.
				g.drawRect(xPos - Point.DIAMETER / 2, yPos - Point.DIAMETER / 2, Point.DIAMETER, Point.DIAMETER);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{// invoke when the mouse click the button
		// get the coordinates of the chess current piece through the position of the mouse.
		xIndex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		yIndex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		// can not press if the mouse is out of the chess board.
		if (xIndex < 0 || xIndex > ROWS || yIndex < 0 || yIndex > COLS)
			return;
		if (findChess(xIndex, yIndex))// can not press if there is already a chess there.
			return;

		Point ch = new Point(xIndex, yIndex, whoseTurn ? Color.black : Color.white);
		chessList[chessCount++] = ch;// add 1 to the chessCount, and add the new point to the array chessList.
		repaint();// repaint the chess board: call the function paintComponent(Graphics g)
		if (whoseTurn)
		{
			player1.myChessPieces.add(ch);// add the new point "ch" to player1's chess list
			player1.resetChessCount();
			player1.countChess(0, player1.myChessPieces.size() - 1);// recount the chessCount to see if it reaches 5.
			if (player1.getChessCount() >= 5)
			{// player1 wins
				JOptionPane.showMessageDialog(null, player1.getName() + " (Black) Wins!", "Congratualations",
						JOptionPane.INFORMATION_MESSAGE);
				player1.addScore(10);// add 10 to player1's score.
				appendScore(player1);
				appendScore(player2);// add two players' score to the score board.
			} else if (player1.getChessCount() == 3)
			{
				player1.addScore(1);// add 1 to player1's score.
			} else if (player1.getChessCount() == 4)
			{
				player1.addScore(2);// add 2 to player1's score.
			}
		} else
		{
			player2.myChessPieces.add(ch);// add the new point "ch" to player2's chess list
			player2.resetChessCount();
			player2.countChess(0, player2.myChessPieces.size() - 1);// recount the chessCount to see if it reaches 5.
			if (player2.getChessCount() >= 5)
			{// player2 wins
				JOptionPane.showMessageDialog(null, player2.getName() + " (White) Wins!", "Congratualations",
						JOptionPane.INFORMATION_MESSAGE);
				player2.addScore(10);// add 10 to player2's score.
				appendScore(player1);
				appendScore(player2);// add two players' score to the score board.
			} else if (player2.getChessCount() == 3)
			{
				player2.addScore(1);// add 1 to player2's score.
			} else if (player2.getChessCount() == 4)
			{
				player2.addScore(2);// add 2 to player2's score.
			}
		}

		whoseTurn = !whoseTurn;
	}

	// the following 4 methods implement the inherited abstract method in the
	// MouseListener because I implements the interface MouseListener,
	// but they do not have any actual function.
	@Override
	public void mouseClicked(MouseEvent e)
	{// being invoked when mouse is clicked on the chess board
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{// being invoked when mouse is released on the chess board
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{// being invoked when mouse enters
	}

	@Override
	public void mouseExited(MouseEvent e)
	{// being invoked when mouse exits
	}

	private boolean findChess(int x, int y)
	{
		for (Point c : chessList)
		{
			if (c != null && c.getX() == x && c.getY() == y)
				return true;
		}
		return false;
	}

	// change the dimension of the chess board (make it adaptive)
	public Dimension getPreferredSize()
	{
		return new Dimension(MARGIN * 2 + GRID_SPAN * COLS, MARGIN * 2 + GRID_SPAN * ROWS);
	}

	//restart the game
	public void restartGame()
	{
		Object[] options =
		{ "YES", "CANCEL" };
		if (JOptionPane.showOptionDialog(null, "Are you sure to start a new game", "Warning",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]) != JOptionPane.YES_OPTION)
		{
			System.exit(0);
		}

		for (int i = 0; i < chessList.length; i++)
		{//clear the chessList
			chessList[i] = null;
		}

		/* recover the value of some variables */
		whoseTurn = true;// reset turn.
		chessCount = 0;// reset chessCount
		player1.reset();
		player2.reset();// reset two players' properties
		repaint();//repaint the panel
	}

	// withdraw the last step
	public void withDraw()
	{
		chessCount--;// minus 1 from the chessCount
		chessList[chessCount] = null;// Remove the last chess piece from the chesslist
		if (whoseTurn)// Remove the last chess piece from the arraylist "myChessPieces"
		{
			player2.myChessPieces.remove(player2.myChessPieces.size() - 1);
		} else
		{
			player1.myChessPieces.remove(player1.myChessPieces.size() - 1);
		}
		whoseTurn = !whoseTurn;// change turn
		repaint();// redraw the chess board
	}

	//show the score board to the user.
	public void showScoreBoard()
	{
		quickSort(playerScore, 0, playerScore.size() - 1, playerName);// sort the scores using quick sort.
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < playerScore.size(); i++)
		{// using StringBuffer to connect each player's name and score into one string.
			sBuffer.append(playerName.get(i) + ": " + playerScore.get(i) + "\n");
		}
		// output the score board.
		JOptionPane.showMessageDialog(null, sBuffer.toString(), "Score Board", JOptionPane.INFORMATION_MESSAGE);
	}

	public void getRank()
	{
		// asks the user for the input
		String input = JOptionPane.showInputDialog(null,
				"Please input player's name and score with a comma to separate them.");
		if (input == null)
		{// close the window if the user clicks "cancel".
			return;
		}
		String[] input2 = input.split(",");
		String name = input2[0];// split the name and score
		int score = Integer.parseInt(input2[1]);// convert
		int rank = binarySearch(playerScore, 0, playerScore.size() - 1, score);// find the position of the score using binary search.
		if (rank == -1)
		{
			JOptionPane.showMessageDialog(null, "Sorry, the player with this score is not found in the data base",
					"Get Rank", JOptionPane.INFORMATION_MESSAGE);// no such player
		} else
		{
			JOptionPane.showMessageDialog(null, "This player (" + name + ") is at " + (rank + 1) + " rank", "Get Rank",
					JOptionPane.INFORMATION_MESSAGE);// output the rank
		}
	}

	// the algorithm for quick sort
	private void quickSort(ArrayList<Integer> score, int leftMark, int rightMark, ArrayList<String> name)
	{
		int left = leftMark;
		int right = rightMark;
		int tempScore;
		String tempName;
		int pivot = left;

		while (true)
		{
			for (; right > 0; right--)
			{
				if (score.get(right) < score.get(pivot))
				{// swap pivot with the element that is smaller than it
					tempScore = score.get(pivot);
					tempName = name.get(pivot);

					score.set(pivot, score.get(right));
					name.set(pivot, name.get(right));

					score.set(right, tempScore);
					name.set(right, tempName);
					pivot = right;// move the pivot
					break;
				} else if (right == pivot)
				{// stop when there the right mark reaches pivot
					break;
				}
			}

			for (; left <= pivot; left++)
			{// swap pivot with the element that is greater than it
				if (score.get(left) > score.get(pivot))
				{
					tempScore = score.get(pivot);
					tempName = name.get(pivot);

					score.set(pivot, score.get(left));
					name.set(pivot, name.get(left));

					score.set(left, tempScore);
					name.set(left, tempName);
					pivot = left;// move the pivot
					break;
				} else if (left == pivot)
				{// stop when there the left mark reaches pivot. The element in the pivot position is sorted now
					break;
				}
			}

			if (right == left)
			{// get out of the loop when the right mark and the left mark are equal.
				break;
			}
		}
		if (left > leftMark)
		{// quick sort the left side of the pivot
			quickSort(score, leftMark, left - 1, name);
		}
		if (right < rightMark)
		{// quick sort the right side of the pivot.
			quickSort(score, right + 1, rightMark, name);
		}
	}

	private int binarySearch(ArrayList<Integer> a, int low, int high, int searchValue)
	{
		int mid = (low + high) / 2;

		if (low == high)
		{// when there is only one element left
			if (low == searchValue)
			{
				return low;
			} else
			{// the search value is not found
				return -1;
			}
		} else
		{
			if (searchValue > a.get(mid))
			{//binary search the  right side of middle.
				return binarySearch(a, mid + 1, high, searchValue);
			} else if (searchValue < a.get(mid))
			{// binary search the left side of middle.
				return binarySearch(a, low, mid - 1, searchValue);
			} else
			{// the middle is equal to the search value
				return mid;
			}
		}
	}

	// Get the score board from a local .txt file
	private void getScoreBoard()
	{
		try
		{
			String line;
			FileReader fr2 = new FileReader("ScoreBoard.txt");
			BufferedReader br2 = new BufferedReader(fr2);// instantiate bufferedReader
			do
			{
				line = br2.readLine();// read next line in the ScoreBoard.txt
				if (line != null)
				{// split the name and score into two arraylists.
					String[] info = line.split("\\s");
					playerName.add(info[0]);
					playerScore.add(Integer.parseInt(info[1]));
				}
			} while (line != null);
			br2.close();
			// show welcome dialog
			JOptionPane
					.showMessageDialog(
							null,
							"The goal of this game is to order unbroken row of five signs horizontally, vertically, or diagonally. "
									+ "\nPlayers play by clicking with mouse on any empty field of the board. Then it is turn of the other player. "
									+ "\nAnd then it is the other player's turn again and so on."
									+ "\n\nPlayer1(Black) is " + player1.getName() + "\nPlayer2(White) is "
									+ player2.getName(), "Welcome to Gomoku", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e)
		{
			// TODO: handle exception
		}

	}

	// append current players' score to the score board.
	private void appendScore(Player p)
	{//add two player's scores to the score board.
		for (int i = 0; i < playerName.size(); i++)
		{
			if (playerName.get(i).equals(p.getName()))
			{
				playerScore.set(i, p.getScore());// set the score
			}
		}
	}

}