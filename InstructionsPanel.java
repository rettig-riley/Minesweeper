/**
 * WHO: @author Dagmawit Assefa
 * WHAT: InstrucitonsPanel.java
 * WHEN: 12/13/17
 */

import java.util.Scanner;
import java.io.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * Insert comments here
 */
public class InstructionsPanel extends JPanel
{
  //-----------------------------------------------------------------
  //  Opens a file chooser dialog, reads the selected file and
  //  loads it into a text area.
  //-----------------------------------------------------------------
  
  JTextArea text;
  JButton back;
  String info = "";
  
  /**
   * Insert comments here
   */
  public InstructionsPanel(){
    text = new JTextArea(20,30);
    back = new JButton("Back");
    try {
      Scanner fileScan = new Scanner (new File("Instructions.txt"));
      while (fileScan.hasNext()) {
        info+= fileScan.nextLine() + "\n";
//        System.out.println(line);
      }
    } catch (IOException ex) {
      System.out.println(ex);
    } 
    
    back.addActionListener(new BackListener());
    text.setText (info);
    
    this.add(text);
    this.add(back);
    
  }
  
  /**
   * Insert comments here
   */
  private class BackListener implements ActionListener{ 
    public void actionPerformed(ActionEvent event){
      //mainPanel.goTo(new optionsPanel());
      //setVisible(false);
      //options.setVisible(true);
      OptionsPanel options = new OptionsPanel();
      System.out.println("before removing");
      MinesweeperGUI.frame.getContentPane().removeAll();
      System.out.println("removed");
      MinesweeperGUI.frame.getContentPane().add(options);
      System.out.println("added");
      MinesweeperGUI.frame.validate();
      MinesweeperGUI.frame.repaint();
      MinesweeperGUI.frame.pack();
    }
  }
}
