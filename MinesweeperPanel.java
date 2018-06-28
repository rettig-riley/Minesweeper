/**
 * 
 * names
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MinesweeperPanel extends JPanel{ //different panels for easy, medium and hard

  //instance variables
//  private JButton back, flag1, flag2, newGame;
//  private JLabel level, timeLeft;
//  private JButton[][] cells;
//  Timer timer;
//  JPanel top, bottom;
  JPanel options;
  
  
  public MinesweeperPanel(){
//   top = new JPanel();
//   BoxLayout layout = new BoxLayout ( BoxLayout.Y_AXIS);
//   top.setLayout (layout);
////   top.setBackground(Color.blue);
////   
////   back = new JButton("Back");
////   level = new JLabel("Easy"); // this should be able to change
////   timer = new Timer();
////   
////   bottom = new JPanel();
////   BoxLayout layout = new BoxLayout (controls, BoxLayout.Y_AXIS);
////   bottom.setLayout (layout);
////   bottom.setBackground(Color.Blue);
   
//   add(top);
//   add(bottom);
    
    options = new OptionsPanel();
    add(options);
  }
    
    private class Listener implements ActionListener
  {
    public void actionPerformed (ActionEvent event)
    {
      
      
    }
  }
   
  
}