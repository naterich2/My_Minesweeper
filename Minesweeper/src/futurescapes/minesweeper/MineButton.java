package futurescapes.minesweeper;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Icon;

public class MineButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean hasMine;
	private int mineCount;
	private ImageIcon icon;
	private int x;
	private int y;
	private boolean dug= false;
	
	private final static ImageIcon cellDug = new ImageIcon(MineButton.class.getResource("/Cell_Dug.png"));
	@SuppressWarnings("unused")
	private final static ImageIcon cellFlaggedWrong = new ImageIcon(MineButton.class.getResource("/Cell_Flagged_Wrong.png"));
	private final static ImageIcon cellFlagged = new ImageIcon(MineButton.class.getResource("/Cell_Flagged.png"));
	private final static ImageIcon cellMineWrong = new ImageIcon(MineButton.class.getResource("/Cell_Mine_Wrong.png"));
	private final static ImageIcon cellMine = new ImageIcon(MineButton.class.getResource("/Cell_Mine.png"));
	private final static ImageIcon cellNormal = new ImageIcon(MineButton.class.getResource("/Cell_Normal.png"));
	private final static ImageIcon cellQuestion = new ImageIcon(MineButton.class.getResource("/Cell_Question.png"));
	
	private final static ImageIcon one = new ImageIcon(MineButton.class.getResource("/open1.gif"));
	private final static ImageIcon two = new ImageIcon(MineButton.class.getResource("/open2.gif"));
	private final static ImageIcon three = new ImageIcon(MineButton.class.getResource("/open3.gif"));
	private final static ImageIcon four = new ImageIcon(MineButton.class.getResource("/open4.gif"));
	private final static ImageIcon five = new ImageIcon(MineButton.class.getResource("/open5.gif"));
	private final static ImageIcon six = new ImageIcon(MineButton.class.getResource("/open6.gif"));
	private final static ImageIcon seven = new ImageIcon(MineButton.class.getResource("/open7.gif"));
	private final static ImageIcon eight = new ImageIcon(MineButton.class.getResource("/open8.gif"));
	
	public MineButton(int i, int j) {
		super();
		this.setIcon(cellNormal);
		icon = cellNormal;
		hasMine = false;
		mineCount = 0;
		this.setSize(cellNormal.getIconWidth(), cellNormal.getIconHeight());
		x = j;
		y = i;
	}
	
	//getter methods
	public boolean isMine(){
		return hasMine;
	}	
	public int getCount(){
		return mineCount;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int width(){
		return icon.getIconWidth();
	}
	public int height(){
		return icon.getIconHeight();
	}
	public boolean isDug(){
		return dug;
	}
	public boolean isFlagged(){
		return icon.equals(cellFlagged);
	}
	
	//setter methods
	public void setMine(boolean b){
		hasMine = b;
	}
	@Override
	public void setIcon(Icon i){
		super.setIcon(i);
		icon = (ImageIcon) i;
	}
	public void setCount(int x){
		mineCount = x;
	}
	public void setMineOnLose(){
		this.setIcon(cellMine);
	}
	
	//dig a mine
	public boolean dig(boolean rightClick){
		if((icon.equals(cellFlagged) && rightClick) || (icon.equals(cellQuestion) && rightClick)){
			this.setIcon(cellNormal);
		} else if(icon.equals(cellFlagged)){
			this.setIcon(cellQuestion);
		} else if(icon.equals(cellQuestion)){
			this.setIcon(cellFlagged);
		} else {
			if (hasMine && !(icon.equals(cellFlagged) || icon.equals(cellQuestion))){	
				this.setIcon(cellMineWrong);
				return false;
			} else {
				switch(mineCount) {
					case 0:
						this.setIcon(cellDug);
						break;
					case 1:
						//setIcon to 1
						this.setIcon(one);
						break;
					case 2:
						//setIcon to 2
						this.setIcon(two);
						break;
					case 3:
						//setIcon to 3
						this.setIcon(three);
						break;
					case 4:
						//setIcon to 4
						this.setIcon(four);
						break;
					case 5:
						//setIcon to 5
						this.setIcon(five);
						break;
					case 6:
						//setIcon to 6
						this.setIcon(six);
						break;
					case 7:
						//setIcon to 7
						this.setIcon(seven);
						break;
					case 8:
						//setIcon to 8
						this.setIcon(eight);
						break;
				}
			}
		}
		dug = true;
		return true;
	}
}
