package futurescapes.minesweeper;


import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

import futurescapes.minesweeper.MineButton;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class Minesweeper extends JFrame implements Runnable {
	private static final long serialVersionUID = 2L;
	private int numBombs;
	public MineButton[][] board;
	public int rows;
	public int cols;
	private Random randnums = new Random();
	private boolean firstMove = true;
	private int timeCount = 0;
	private int numFlagged = 0;
	private String difficulty;
	private Minesweeper_Scores scores;
	
	private JPanel main = new JPanel(new BorderLayout());
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JPanel topLevel = new JPanel(new BorderLayout());
	
	//ImageIcons
	private static final ImageIcon smiley = new ImageIcon(Minesweeper.class.getResource("/Smiley.png"));
	private static final ImageIcon smileyDead = new ImageIcon(Minesweeper.class.getResource("/Smiley_Dead.png"));
	private static final ImageIcon smileySunglasses = new ImageIcon(Minesweeper.class.getResource("/Smiley_Sunglasses.png"));
	private static final ImageIcon negative = new ImageIcon(Minesweeper.class.getResource("/time-.gif"));
	private static final ImageIcon zero = new ImageIcon(Minesweeper.class.getResource("/time0.gif"));
	private static final ImageIcon one = new ImageIcon(Minesweeper.class.getResource("/time1.gif"));
	private static final ImageIcon two = new ImageIcon(Minesweeper.class.getResource("/time2.gif"));
	private static final ImageIcon three = new ImageIcon(Minesweeper.class.getResource("/time3.gif"));
	private static final ImageIcon four = new ImageIcon(Minesweeper.class.getResource("/time4.gif"));
	private static final ImageIcon five = new ImageIcon(Minesweeper.class.getResource("/time5.gif"));
	private static final ImageIcon six = new ImageIcon(Minesweeper.class.getResource("/time6.gif"));
	private static final ImageIcon seven = new ImageIcon(Minesweeper.class.getResource("/time7.gif"));
	private static final ImageIcon eight = new ImageIcon(Minesweeper.class.getResource("/time8.gif"));
	private static final ImageIcon nine = new ImageIcon(Minesweeper.class.getResource("/time9.gif"));
	private ImageIcon[] numbers = {zero, one, two, three, four, five, six, seven, eight, nine};

	//Menu bar
	private JMenuBar menu = new JMenuBar();
	private JMenu game = new JMenu("Game");
	private JMenuItem newGame = new JMenuItem("New");
	private JMenuItem easy = new JMenuItem("Beginner (11x11, 12 bombs)");
	private JMenuItem medium = new JMenuItem("Intermediate (16x16, 40 bombs)");
	private JMenuItem expert = new JMenuItem("Expert (16x30, 99 bombs)");
	private JMenuItem custom = new JMenuItem("Custom");
	private JMenuItem exit = new JMenuItem("Exit");
	private JMenu about = new JMenu("About");
	private JMenuItem aboutButton = new JMenuItem("About");
	private JMenu help = new JMenu("Help");
	private JMenuItem helpButton = new JMenuItem("Help");
	private JSeparator sep2 = new JSeparator();
	private JSeparator sep1 = new JSeparator();
	
	//Top Panel
	private Timer minesweeperTimer = new Timer();
	private JPanel topPanel = new JPanel(new FlowLayout());
	private JPanel bombsLeft = new JPanel(new FlowLayout());
	private JLabel bombsHundreds = new JLabel(numbers[(numBombs/100) % 10]); //add Icon Later
	private JLabel bombsTens = new JLabel(numbers[(numBombs/10) % 10]); //add Icon Later
	private JLabel bombsOnes = new JLabel(numbers[numBombs % 10]); //add Icon Later
	private JPanel smileyPanel = new JPanel();
	private JButton smileyButton = new JButton(smiley);
	private JPanel time = new JPanel(new FlowLayout());
	private JLabel timeHundreds = new JLabel(zero); //add Icon Later
	private JLabel timeTens = new JLabel(zero); //add Icon Later
	private JLabel timeOnes = new JLabel(zero); //add Icon Later
	
	//Minefield
	private JPanel mineField = new JPanel();
	
	public Minesweeper(String name, Minesweeper_Scores s) {
		super(name);
		setResizable(false);
		scores = s;
	}
	class MinesweeperTimerTask extends TimerTask{
		int hundreds = 0;
		int tens = 0;
		int ones = 0;
		public void run(){
			timeCount++;
			if(timeCount<1000){
				ones = timeCount % 10;
				tens = (timeCount / 10) % 10;
				hundreds = (timeCount / 100) % 10;
				timeHundreds.setIcon(numbers[hundreds]);
				timeTens.setIcon(numbers[tens]);
				timeOnes.setIcon(numbers[ones]);
			} else {
				timeHundreds.setIcon(negative);
				timeTens.setIcon(negative);
				timeOnes.setIcon(negative);
			}	
		}
	}
	
	private void addComponentsToPane(){
		this.setLayout(new BorderLayout());
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				startNewGame();
			}
		});
		easy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				startNewGame(11,11,2);
				difficulty = "Easy";
			}
		});
		medium.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				startNewGame(16,16,10);
				difficulty = "Medium";
			}
		});
		expert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				startNewGame(16,30,99);
				difficulty = "Hard";
			}
		});
		
		custom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int r = 0, c = 0, b = 0;
				JPanel rows = new JPanel(new BorderLayout());
				JLabel rowsLabel = new JLabel("Rows");
				JTextField rowsText = new JTextField(2);
				rows.add(rowsLabel, BorderLayout.WEST);
				rows.add(rowsText, BorderLayout.EAST);
				
				JPanel cols = new JPanel(new BorderLayout());
				JLabel colsLabel = new JLabel("cols");
				JTextField colsText = new JTextField(2);
				cols.add(colsLabel, BorderLayout.WEST);
				cols.add(colsText, BorderLayout.EAST);
				
				JPanel bombs = new JPanel(new BorderLayout());
				JLabel bombsLabel = new JLabel("bombs");
				JTextField bombsText = new JTextField(2);
				bombs.add(bombsLabel, BorderLayout.WEST);
				bombs.add(bombsText, BorderLayout.EAST);
				
				JPanel message = new JPanel();
				JPanel both = new JPanel(new BorderLayout());
				JPanel enter = new JPanel(new FlowLayout());
				message.add(new JLabel("Please enter the number of rows, columns, and bombs."), BorderLayout.NORTH);
				enter.add(rows);
				enter.add(cols);
				enter.add(bombs);
				both.add(message, BorderLayout.NORTH);
				both.add(enter, BorderLayout.CENTER);
				while(true){
					try{
						if(JOptionPane.showOptionDialog(null, both,
									"Custom", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, smiley, null, null) == 0){
							r = Integer.parseInt(rowsText.getText());
							c = Integer.parseInt(colsText.getText());
							b = Integer.parseInt(bombsText.getText());
							startNewGame(r, c, b);
						}	
						break;
					} catch(NumberFormatException nfe){
						JOptionPane.showConfirmDialog(null, "Oops!  Please enter an integer for all three values!", "Oops!", JOptionPane.DEFAULT_OPTION);
						continue;
					}
				}	
				difficulty = "Custom";
			}
		});
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		game.add(newGame);
		game.add(sep1);
		game.add(easy);
		game.add(medium);
		game.add(expert);
		game.add(custom);
		game.add(sep2);
		game.add(exit);
		
		aboutButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,"Minesweeper is based off of the original Windows version of the game created by Microsoft in 1990.\nThis version created by Nathan Richman in 2013.\n\nThank you for playing ","", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		about.add(aboutButton);
		helpButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String url = "<html><a target = \"_blank\" href= \"http://www.freeminesweeper.org/help/minehelpinstructions.html\">Minesweeper Instructions.</a></html>";
				JOptionPane.showMessageDialog(null,"Full instructions for how to play minecraft can be found here:\n\n"+url,"", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		help.add(helpButton);
		menu.add(game);
		menu.add(about);
		menu.add(help);
		this.setJMenuBar(menu);
		
		smileyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				startNewGame();
			}
		});
		smileyButton.setSize(smiley.getIconWidth(), smiley.getIconHeight());
		smileyButton.setBorder(null);
		smileyButton.setToolTipText("Start new game");
		smileyPanel.add(smileyButton);
		bombsLeft.add(bombsHundreds, BorderLayout.WEST);
		bombsLeft.add(bombsTens, BorderLayout.CENTER);
		bombsLeft.add(bombsOnes, BorderLayout.EAST);
		time.add(timeHundreds);
		time.add(timeTens);
		time.add(timeOnes);
		topPanel.add(time);
		topPanel.add(smileyPanel);
		topPanel.add(bombsLeft);
		main.add(topPanel, BorderLayout.CENTER);
		main.add(new JSeparator(), BorderLayout.SOUTH);
		
		board = new MineButton[rows][cols];
		mineField.setLayout(new GridLayout(rows,cols));
		for(int i = 0; i<rows; i++){
			for(int j = 0; j<cols; j++){
				board[i][j] = new MineButton(i,j, this);
				MineButton temp = board[i][j];
				temp.setBorder(null);
				temp.addMouseListener(new MouseAdapter(){
						public void mousePressed(MouseEvent e){
							MineButton current = (MineButton)e.getSource();
							if(firstMove == true){
								minesweeperTimer.schedule(new MinesweeperTimerTask(), 0, 1000);
								firstMove = false;
							}
							if(!current.isDug()){
								if(SwingUtilities.isLeftMouseButton(e)){
									cascade(current);
									updateFlagged();
									checkForWin();
								} else
									current.dig(true);
									updateFlagged();
									checkForWin();
							}	
						}
					});
				mineField.add(temp);
				}
			}
		tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
		tabbedPane.setBackground(new Color(207,225,223));
		topLevel.add(mineField, BorderLayout.CENTER);
		topLevel.add(main, BorderLayout.NORTH);
		tabbedPane.add("Game",topLevel);
		scores.setBounds(this.getBounds());
		tabbedPane.add("scores", scores);
		this.add(tabbedPane);

	}
	public void run(){
		rows = 16;
		cols = 16;
		numBombs = 40;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addComponentsToPane();
		placeMines();
		firstMove = true;	
		this.pack();
		this.repaint();
		this.setVisible(true);
	}
	
	private void resetGame(){
		//Timers
		minesweeperTimer.cancel();
		minesweeperTimer = new Timer();
		timeHundreds.setIcon(zero);
		timeTens.setIcon(zero);
		timeOnes.setIcon(zero);

		//Minefield
		mineField.removeAll();
		smileyButton.setIcon(smiley);
		timeCount = 0;
	}
	private void startNewGame(int r, int c, int b){
		resetGame();
		numBombs = b;
		cols = c;
		rows = r;
		this.addComponentsToPane();
		placeMines();
		updateFlagged();
		firstMove = true;
		this.pack();
		this.repaint();
	}
	private void startNewGame(){
		resetGame();
		this.addComponentsToPane();
		placeMines();
		updateFlagged();
		firstMove = true;
		this.pack();
		this.repaint();
	}
	private void placeMines(){
		randnums = new Random();
		int bombs = numBombs;
		while(bombs>0){
			int row = randnums.nextInt(rows);
			int col = randnums.nextInt(cols);
			board[row][col].setMine(true);
			bombs--;
		}
	}
	private void updateFlagged(){
		numFlagged=0;
		for(int i = 0; i<rows; i++){
			for(int j= 0; j<cols; j++){
				if(board[i][j].isFlagged())
					numFlagged++;
			}
		}
		Integer numLeft = numBombs-numFlagged;
		if(numLeft<1000 && numLeft>-100){
			int onesLeft = numLeft % 10;
			int tensLeft = (numLeft / 10) % 10;
			int hundredsLeft = (numLeft / 100) % 10;
			bombsHundreds.setIcon(numbers[hundredsLeft]);
			bombsTens.setIcon(numbers[tensLeft]);
			bombsOnes.setIcon(numbers[onesLeft]);
		} else {
			bombsHundreds.setIcon(negative);
			bombsTens.setIcon(negative);
			bombsOnes.setIcon(negative);
		}
	}
	public void checkForWin(){
		boolean won = true;
		for(int i = 0; i<rows; i++){
			for(int j = 0; j<cols; j++){
				if(!board[i][j].isDug() && !board[i][j].isMine())
					won = false;
			}
		}
		if(won){
			String winMessage = "You won! \n \n Your time was: "+timeCount;
			String highScoreMessage = "Cogratulations! Your time "+timeCount+" is a new High Score!";
			smileyButton.setIcon(smileySunglasses);
			minesweeperTimer.cancel();
			if(Minesweeper_Scores.addNewScore(difficulty,timeCount))
				JOptionPane.showConfirmDialog(this, highScoreMessage, "New High Score!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, smileySunglasses);
			else
				JOptionPane.showConfirmDialog(this, winMessage, "You won!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, smileySunglasses);
			startNewGame();
		}
	}
	public void lose(){
		smileyButton.setIcon(smileyDead);
		minesweeperTimer.cancel();
		JOptionPane.showConfirmDialog(this, "You Lose!", "You Lose!", JOptionPane.DEFAULT_OPTION);
		for(int i = 0; i<rows; i++){
			for(int j = 0; j<cols; j++){
				if(board[i][j].isMine())
					board[i][j].setIcon(MineButton.cellMine);
			}
		}	
	}
	/*need to find how to make this work for a single button ie.  b.getX() and b.getY() cant be randomly called*/
	public void cascade(MineButton b){
		ArrayList<MineButton> queue = new ArrayList<MineButton>();
		ArrayList<MineButton> removed = new ArrayList<MineButton>();
		queue.add(b);
		while(queue.size()>0){
			MineButton temp = queue.get(queue.size()-1);
			removed.add(temp);
			if(temp.dig(false)){
				ArrayList<MineButton> tmpArray = temp.getAdjacentSquares();
				if(temp.countMines() < 1){
					for(MineButton button: tmpArray){
						if(!queue.contains(button) && !removed.contains(button))
							queue.add(button);
					}
				}
			} else {
				lose();
				return;
			}
			
			queue.remove(temp);
		}	
	}
}