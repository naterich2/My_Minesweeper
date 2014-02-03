package futurescapes.minesweeper;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;

public class Minesweeper_Scores extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static HashMap<String,ArrayList<Integer>> scoresMap = new HashMap<String,ArrayList<Integer>>();

	
	//JFrame stuff
	private JTabbedPane tabbed = new JTabbedPane();
	private JPanel tab1 = new JPanel();
	private JPanel tab2 = new JPanel();

	private JTextArea easyText = new JTextArea();
	private JScrollPane easy = new JScrollPane(easyText);
	private JTextArea mediumText = new JTextArea();
	private JScrollPane medium = new JScrollPane(mediumText);
	private JTextArea hardText = new JTextArea();
	private JScrollPane hard = new JScrollPane(hardText);
	
	
	
	/**
	 * 
	 * @param d the difficulty of the game when won
	 * @param time the time score of the player
	 * @return true if the score is a high score, false if it isn't
	 */
	public static boolean addNewScore(String d, int time){
		boolean highScore = false;
		if(scoresMap.get(d) != null){
			ArrayList<Integer> tmp = scoresMap.get(d);
			if(tmp.size()>0){
				for(int i = 0; i<tmp.size(); i++){
					if(Integer.valueOf(time).compareTo(tmp.get(i))<0){
						tmp.add(i,new Integer(time));
						if(i == 0) highScore = true;
						break;
					}
				}
			} else{
				tmp.add(new Integer(time));
				highScore = true;
			}
		}
			
		return highScore;
	}

	
	private void createScoresText(){	
		if((scoresMap.get("Easy").size()) > 0){
			for(int i = 0; i<scoresMap.get("Easy").size(); i++){
				easyText.append(i+".)  "+(new Integer(scoresMap.get("Easy").get(i))).toString()+"\n");
			}
		}	
		if((scoresMap.get("Medium").size())>0){
			for(int i = 0; i<scoresMap.get("Medium").size(); i++){
				mediumText.append(i+".)  "+(new Integer(scoresMap.get("Medium").get(i))).toString()+"\n");
			}
		}
		if((scoresMap.get("Hard").size())>0){
			for(int i = 0; i<scoresMap.get("Hard").size(); i++){
				hardText.append(i+".)  "+(new Integer(scoresMap.get("Medium").get(i))).toString()+"\n");
			}
		}	
		tab1.add(easy, BorderLayout.WEST);
		tab1.add(medium, BorderLayout.CENTER);
		tab1.add(hard, BorderLayout.EAST);
	}
	
	public void addComponentsToPane(){
		tabbed.setTabPlacement(JTabbedPane.TOP);
		createScoresText();
		
		tabbed.add("Scores", tab2);
		tabbed.add("Statistics", tab1);
		this.add(tabbed);
		this.repaint();
	}
	
	public Minesweeper_Scores(){
		if(scoresMap.get("Easy") == null) scoresMap.put("Easy", new ArrayList<Integer>());
		if(scoresMap.get("Medium") == null) scoresMap.put("Medium", new ArrayList<Integer>());
		if(scoresMap.get("Hard") == null) scoresMap.put("Hard", new ArrayList<Integer>());
		tab1.setLayout(new BorderLayout());
		addComponentsToPane();
		
	}
}
