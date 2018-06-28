/**
 * WHO: @author Dagmawit Assefa, Riley Retting
 * WHAT: MinesweeperGUI.java
 * WHEN: 11/30/2017
 */

import javax.swing.JFrame;

/**
 * Insert comments here
 */
public class MinesweeperGUI{
  protected static JFrame frame;
  
  public String toString(){
    return "Frame";
  }
  
  /**
   * Insert comments here
   */
  public static void main (String[] args) {
    frame = new JFrame ("Minesweeper");
    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add (new OptionsPanel());
    frame.pack();
    frame.setVisible(true);
  }
}
