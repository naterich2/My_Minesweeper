package futurescapes.minesweeper;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import futurescapes.minesweeper.Minesweeper;
public class Minesweeper_Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 try {
	            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
	        } catch (UnsupportedLookAndFeelException ex) {
	            ex.printStackTrace();
	        } catch (IllegalAccessException ex) {
	            ex.printStackTrace();
	        } catch (InstantiationException ex) {
	            ex.printStackTrace();
	        } catch (ClassNotFoundException ex) {
	            ex.printStackTrace();
	        }
	        /* Turn off metal's use of bold fonts */
	       // UIManager.put("swing.boldMetal", Boolean.FALSE);

		Thread minesweeper = new Thread(new Minesweeper("Minesweeper",new Minesweeper_Scores()));
		minesweeper.start();
	}

}
