/**
 * Gomoku
 * Author: Peter Pan
 * August 18, 2018
 */
package gomoku;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

/* 
 * The main frame of the game, also the starting class(has static void main)
 */
public class StartChessJFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private ChessBoard chessBoard;// create a chess board
	private Panel toolbar;// create a toolbar
	private Button startButton;// create a start button
	private Button backButton;// create a withdraw button
	private Button exitButton;// create a exit button
	private Button scoreBoard;// create a score board button
	private Button getRank;// create a get rank button

	public StartChessJFrame()
	{
		setTitle("Gomoku");// set title
		chessBoard = new ChessBoard();// instantiate the chess board
		MyItemListener lis = new MyItemListener();// instantiate MyItemListener
		toolbar = new Panel();// instantiate the panel at the bottom of the GUI.
		startButton = new Button("Restart");
		backButton = new Button("Withdraw");
		exitButton = new Button("Exit");
		scoreBoard=new Button("Score Board");
		getRank=new Button("Get Rank");// instantiate five buttons
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));// set the layout of the toolbar using FlowLayout.
		toolbar.add(backButton);
		toolbar.add(startButton);
		toolbar.add(exitButton);
		toolbar.add(scoreBoard); 
		toolbar.add(getRank); // add five buttons to the toolbar
		startButton.addActionListener(lis);
		backButton.addActionListener(lis);
		exitButton.addActionListener(lis);
		scoreBoard.addActionListener(lis);
		getRank.addActionListener(lis);// add listeners to three buttons.
		add(toolbar, BorderLayout.SOUTH);// add the toolbar to bottom south of the JFrame.
		add(chessBoard);// add the chess board to the JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// set close opertion
		pack();// adaptive size
	}

	private class MyItemListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object obj = e.getSource();// get the source of the event (user's action).
			if (obj == startButton)
			{
				System.out.println("Restarting...");// restart a new game
				chessBoard.restartGame();
			} else if (obj == exitButton)
			{
				System.exit(0);// Exit
			} else if (obj == backButton)
			{
				System.out.println("Withdrawing...");// withdraw
				chessBoard.withDraw();
			} else if (obj==scoreBoard)
			{
				chessBoard.showScoreBoard();// show the score board.
			} else if (obj==getRank)
			{
				chessBoard.getRank();// ask user to input the player's information and return his rank.
			}
		}
	}

	// main method. Entrance of the program
	public static void main(String[] args)
	{
		StartChessJFrame f = new StartChessJFrame();// instantiate the main frame.
		f.setVisible(true);// show the main frame
	}
}