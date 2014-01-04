package futurescapes.minesweeper;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

import futurescapes.minesweeper.MineButton;
import javax.swing.*;
import javax.swing.text.html.HTML;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Minesweeper extends JFrame implements Runnable {
	private static final long serialVersionUID = 2L;
	private int numBombs;
	private MineButton[][] board;
	private int rows;
	private int cols;
	private int moves = 0;
	private Random randnums = new Random();
	private boolean firstMove = true;
	private int numFlagged = 0;
	
	private JPanel main = new JPanel(new BorderLayout());
	
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

	//Menu bar
	private JMenuBar menu = new JMenuBar();
	private JMenu game = new JMenu("Game");
	private JMenuItem newGame = new JMenuItem("New");
	private JSeparator sep1 = new JSeparator();
	private JMenuItem easy = new JMenuItem("Beginner (8x8, 10 bombs)");
	private JMenuItem medium = new JMenuItem("Intermediate (16x16, 40 bombs)");
	private JMenuItem expert = new JMenuItem("Expert (16x30, 99 bombs)");
	private JMenuItem custom = new JMenuItem("Custom");
	private JSeparator sep2 = new JSeparator();
	private JMenuItem exit = new JMenuItem("Exit");
	private JMenu about = new JMenu("About");
	private JMenuItem aboutButton = new JMenuItem("About");
	private JMenu help = new JMenu("Help");
	private JMenuItem helpButton = new JMenuItem("Help");
	
	//Top Panel
	private Timer minesweeperTimer;
	private JPanel topPanel = new JPanel(new BorderLayout());
	private JPanel bombsLeft = new JPanel(new BorderLayout());
	private JLabel bombsHundreds = new JLabel("0"); //add Icon Later
	private JLabel bombsTens = new JLabel("0"); //add Icon Later
	private JLabel bombsOnes = new JLabel("0"); //add Icon Later
	private JPanel smileyPanel = new JPanel();
	private JButton smileyButton = new JButton(smiley);
	private JPanel time = new JPanel(new BorderLayout());
	private JLabel timeHundreds = new JLabel(zero); //add Icon Later
	private JLabel timeTens = new JLabel(zero); //add Icon Later
	private JLabel timeOnes = new JLabel(zero); //add Icon Later
	
	//Minefield
	private JPanel mineField = new JPanel();
	
	public Minesweeper(String name) {
		super(name);
		setResizable(false);
		minesweeperTimer = new Timer();
	}
	class MinesweeperTimerTask extends TimerTask{
		int hundreds = 0;
		int tens = 0;
		int ones = 0;
		public void run(){
			if(ones<9) ones++;
			else{
				if(tens<9) tens++;
				else{
					hundreds++;
					tens = 0;
				}
				ones = 0;
			}
			if((hundreds)>9){
				timeHundreds.setIcon(negative);
				timeTens.setIcon(negative);
				timeOnes.setIcon(negative);
			}
			switch(hundreds){
				case 0: timeHundreds.setIcon(zero);
					break;
				case 1: timeHundreds.setIcon(one);
					break;
				case 2: timeHundreds.setIcon(two);
					break;
				case 3: timeHundreds.setIcon(three);
					break;
				case 4: timeHundreds.setIcon(four);
					break;
				case 5: timeHundreds.setIcon(five);
					break;
				case 6: timeHundreds.setIcon(six);
					break;
				case 7: timeHundreds.setIcon(seven);
					break;
				case 8: timeHundreds.setIcon(eight);
					break;
				case 9: timeHundreds.setIcon(nine);
					break;
				default:
					//timeHundreds.setIcon(negative);
			}
			switch(tens){
				case 0: timeTens.setIcon(zero);
					break;
				case 1: timeTens.setIcon(one);
					break;
				case 2: timeTens.setIcon(two);
					break;
				case 3: timeTens.setIcon(three);
					break;
				case 4: timeTens.setIcon(four);
					break;
				case 5: timeTens.setIcon(five);
					break;
				case 6: timeTens.setIcon(six);
					break;
				case 7: timeTens.setIcon(seven);
					break;
				case 8: timeTens.setIcon(eight);
					break;
				case 9: timeTens.setIcon(nine);
					break;
				default:
					//timeTens.setIcon(negative);
			}
			switch(ones){
				case 0: timeOnes.setIcon(zero);
					break;
				case 1: timeOnes.setIcon(one);
					break;
				case 2: timeOnes.setIcon(two);
					break;
				case 3: timeOnes.setIcon(three);
					break;
				case 4: timeOnes.setIcon(four);
					break;
				case 5: timeOnes.setIcon(five);
					break;
				case 6: timeOnes.setIcon(six);
					break;
				case 7: timeOnes.setIcon(seven);
					break;
				case 8: timeOnes.setIcon(eight);
					break;
				case 9: timeOnes.setIcon(nine);
					break;
				default:
					//timeOnes.setIcon(negative);
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
				startNewGame(8,8,10);
			}
		});
		medium.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				startNewGame(16,16,40);
			}
		});
		expert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				startNewGame(16,30,99);
			}
		});
		custom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int r = 0, c = 0, b = 0;
				JPanel rows = new JPanel();
				@SuppressWarnings("unused")
				JLabel rowsLabel = new JLabel("Rows");
				JTextField rowsText = new JTextField(2);
				
				JPanel cols = new JPanel();
				@SuppressWarnings("unused")
				JLabel colsLabel = new JLabel("cols");
				JTextField colsText = new JTextField(2);
				
				JPanel bombs = new JPanel();
				@SuppressWarnings("unused")
				JLabel bombsLabel = new JLabel("bombs");
				JTextField bombsText = new JTextField(2);
				JPanel[] panels = {rows, cols, bombs};
				while(true){
					try{
						if(JOptionPane.showOptionDialog(null, "Please enter the number of rows, columns, and bombs.",
								"Custom", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, smiley, panels, panels[1])==JOptionPane.OK_OPTION){
							r = Integer.parseInt(rowsText.getText());
							c = Integer.parseInt(colsText.getText());
							b = Integer.parseInt(bombsText.getText());
							startNewGame(r, c, b);
						}
						break;
					} catch(NumberFormatException nfe){
						JOptionPane.showConfirmDialog(null, "Oops!  Please enter an integer for all three values!", "Oops!", JOptionPane.OK_OPTION);
						continue;
					}
				}	
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
		System.out.println(smileyButton.getSize());
		smileyButton.setBorder(null);
		smileyPanel.add(smileyButton);
		bombsLeft.add(bombsHundreds, BorderLayout.WEST);
		bombsLeft.add(bombsTens, BorderLayout.CENTER);
		bombsLeft.add(bombsOnes, BorderLayout.EAST);
		time.add(timeHundreds, BorderLayout.WEST);
		time.add(timeTens, BorderLayout.CENTER);
		time.add(timeOnes, BorderLayout.EAST);
		topPanel.add(time, BorderLayout.EAST);
		topPanel.add(bombsLeft, BorderLayout.WEST);
		topPanel.add(smileyPanel, BorderLayout.CENTER);
		topPanel.setBackground(new Color(255,0,0));
		main.add(topPanel, BorderLayout.CENTER);
		
		board = new MineButton[rows][cols];
		mineField.setLayout(new GridLayout(rows,cols));
		for(int i = 0; i<rows; i++){
			for(int j = 0; j<cols; j++){
				board[i][j] = new MineButton(i,j);
				MineButton temp = board[i][j];
				temp.setBorder(null);
				System.out.println(board[i][j].getSize());
				temp.setOpaque(false);
				temp.addMouseListener(new MouseAdapter(){
						public void mousePressed(MouseEvent e){
							MineButton current = (MineButton)e.getSource();
							if(firstMove == true){
								minesweeperTimer.schedule(new MinesweeperTimerTask(), 0, 1000);
								firstMove = false;
							}
							if(!current.isDug()){
								if(SwingUtilities.isLeftMouseButton(e))
									cascade(current);
								else
									current.dig(true);
							}	
						}
					});
				mineField.add(temp);
				}
			}
		mineField.setSize(cols*17,rows*17);
		main.add(new JSeparator(), BorderLayout.SOUTH);
		this.add(mineField, BorderLayout.CENTER);
		this.add(main, BorderLayout.NORTH);
	}
	public void run(){
		rows = 16;
		cols = 16;
		numBombs = 10;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.startNewGame();
		this.pack();
		this.repaint();
		this.setVisible(true);
	}
	private void startNewGame(int r, int c, int b){
		numBombs = b;
		cols = c;
		rows = r;
		this.addComponentsToPane();
		placeMines();
		countMines();
		firstMove = true;
		resetTimers();
	}
	private void resetTimers(){
		minesweeperTimer.cancel();
		minesweeperTimer = new Timer();
		timeHundreds.setIcon(zero);
		timeTens.setIcon(zero);
		timeOnes.setIcon(zero);
	}
	private void startNewGame(){
		this.addComponentsToPane();
		placeMines();
		countMines();
		firstMove = true;
		resetTimers();
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
	//add statements to prevent out of bounds on sides != 0, ex board = new MineButton[4][4] prevent board[5][3];
	private void countMines(){
		for(int i = 0; i<rows-1; i++) {
			for(int j = 0; j<cols-1; j++){
				int count = 0;
				if(!(i == 0 || j == 0) && !(i == board.length || j == board[i].length)){
					for(int x = -1; x<=1; x++){
						for(int y = -1; y<=1; y++){
							if(board[i+x][j+y].isMine())
								count++;
						}
					}
				} else if(i == 0  && j>0) {
					for(int x = 0; x<=1; x++){
						for(int y = -1; y<=1; y++){
							if(board[i+x][j+y].isMine())
								count++;
						}
					}
				} else if(j==0 && i>0) {
					for(int x = -1; x<=1; x++){
						for(int y = 0; y<=1; y++){
							if(board[i+x][j+y].isMine())
								count++;
						}
					}
				} else if(i == board.length && j<board[i].length) {
					for(int x = -1; x<=0; x++){
						for(int y = -1; y<=1; y++){
							if(board[i+x][j+y].isMine())
								count++;
						}
					}
				} else if(j ==board[i].length && i <board.length) {
					for(int x = -1; x<=1; x++){
						for(int y = -1; y<=0; y++){
							if(board[i+x][j+y].isMine())
								count++;
						}
					}
				}
			board[i][j].setCount(count);
			}
		}	
	}
	/*need to find how to make this work for a single button ie.  b.getX() and b.getY() cant be randomly called*/
	public void cascade(MineButton b){
		ArrayList<MineButton> queue = new ArrayList<MineButton>();
		while(queue.size()>0){
			for(MineButton button: queue){
				if(!button.isDug())
					button.dig(false);
				queue.remove(button);
			}
			if(!(b.getX() == 0 || b.getY() == 0)){
				for(int x = -1; x<=1; x++){
					for(int y = -1; y<=1; y++){
						if(board[b.getX()+x][b.getY()+y].getCount()>0)
							queue.add(board[b.getX()+x][b.getY()+y]);
					}
				}
			} else if(b.getX() == 0 && b.getY()>0) {
				for(int x = 0; x<=1; x++){
					for(int y = -1; y<=1; y++){
						if(board[b.getX()+x][b.getY()+y].getCount()>0)
							queue.add(board[b.getX()+x][b.getY()+y]);
					}
				}
			} else if(b.getY() == 0 && b.getX()>0) {
				for(int x = -1; x<=1; x++){
					for(int y = 0; y<=1; y++){
						if(board[b.getX()+x][b.getY()+y].getCount()>0)
							queue.add(board[b.getX()+x][b.getY()+y]);
					}
				}
			} else if(b.getX() == board.length && b.getY()<board[b.getX()].length) {
				for(int x = -1; x<=0; x++){
					for(int y = -1; y<=1; y++){
						if(board[b.getX()+x][b.getY()+y].getCount()>0)
							queue.add(board[b.getX()+x][b.getY()+y]);
					}
				}
			} else if(b.getY() ==board[b.getX()].length && b.getX() <board.length) {
				for(int x = -1; x<=1; x++){
					for(int y = -1; y<=0; y++){
						if(board[b.getX()+x][b.getY()+y].getCount()>0)
							queue.add(board[b.getX()+x][b.getY()+y]);
					}
				}
			}
		}
	}
}