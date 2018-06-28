/**
 * WHO: @author Dagmawit Assefa
 * WHAT: MinesweeperGUI.java
 * WHEN: 11/30/2017
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;

/**
 * Insert comments here
 */
public class OptionsPanel extends JPanel{
  //static variable
//  protected static JPanel thisPanel;
  
  //instance variables
  private JLabel title,players, difficulty, sizeLabel, percentageBombs, info;
  private JRadioButton onePlayer, twoPlayers;
  private JRadioButton easy, medium, hard, custom;
  private JButton start, instructions;
  
  private JPanel titlePanel, playersLabel, playersButtons, difficultyLabel, difficultyButtons, sizeSlider,
    bombSlider, startPanel;
  
//  private JButton start;
  
  private JSlider size, bombs;
  private int[] choices  = new int[3];
  //choices is an array of four integers that indicate [number of players, size, number ofBombs]
  
  public OptionsPanel(){
    
//    thisPanel = this;
    choices[0] = 1;
    choices[1] = 10;
    choices[2] = 10;
    
    setLayout (new GridLayout (9,1));
    
    titlePanel = new JPanel();
    playersLabel = new JPanel();
    playersButtons = new JPanel();
    difficultyLabel = new JPanel();
    difficultyButtons = new JPanel();
    sizeSlider = new JPanel();
    bombSlider = new JPanel();
    startPanel = new JPanel();
    
    info = new JLabel();
    title = new JLabel ("CREATE A NEW GAME");
    title.setFont (new Font ("Helvetica", Font.BOLD, 24));
    title.setAlignmentX (Component.LEFT_ALIGNMENT);
    titlePanel.add(title);
    titlePanel.add(title);
    
    players = new JLabel ("Players");
    players.setFont (new Font ("Helvetica", Font.BOLD, 17));
    playersLabel.add(players);
    
    onePlayer = new JRadioButton ("1 Player", true);
    twoPlayers = new JRadioButton ("2 Players");
    playersButtons.add(onePlayer);
    playersButtons.add(twoPlayers);
    
    ButtonGroup playersGroup = new ButtonGroup();
    playersGroup.add(onePlayer);
    playersGroup.add(twoPlayers);
    
    
    difficulty = new JLabel ("Difficulty");
    difficulty.setFont (new Font ("Helvetica", Font.BOLD, 17));
    difficultyLabel.add(difficulty);
    
    easy = new JRadioButton ("Easy", true);
    medium = new JRadioButton ("Medium");
    hard = new JRadioButton ("Hard");
    custom = new JRadioButton ("Custom");
    
    difficultyButtons.add(easy);
    difficultyButtons.add(medium);
    difficultyButtons.add(hard);
    difficultyButtons.add(custom);
    
    
    ButtonGroup difficultyGroup = new ButtonGroup();
    difficultyGroup.add(easy);
    difficultyGroup.add(medium);
    difficultyGroup.add(hard);
    difficultyGroup.add(custom);
    
    sizeLabel = new JLabel("                   Size: "); //is there a better way to do this?
    sizeLabel.setAlignmentX (Component.LEFT_ALIGNMENT);
    sizeSlider.add(sizeLabel);
    
    size = new JSlider (JSlider.HORIZONTAL, 1,21,16); //this should be improved
    size.setMajorTickSpacing (5);
    size.setMinorTickSpacing (1);
    size.setPaintTicks (true);
    size.setPaintLabels (true);
    size.setAlignmentX (Component.LEFT_ALIGNMENT);
    
    sizeSlider.add(size);
    
    percentageBombs = new JLabel("Perntage of Bombs: ");
    bombSlider.add(percentageBombs);
    
    bombs = new JSlider (JSlider.HORIZONTAL, 0,100, 15);
    bombs.setMajorTickSpacing (20);
    bombs.setMinorTickSpacing (5);
    bombs.setPaintTicks (true);
    bombs.setPaintLabels (true);
    bombs.setAlignmentX (Component.LEFT_ALIGNMENT);
    
    bombSlider.add(bombs);
    
    start = new JButton ("Start");
    instructions = new JButton ("Instructions");
    startPanel.add(instructions);
    startPanel.add(start);
    
    
    bombSlider.setVisible(false);
    sizeSlider.setVisible(false);
    
    add(titlePanel);
    add(playersLabel);
    add(playersButtons);
    add(difficultyLabel);
    add(difficultyButtons);
    add(sizeSlider);
    add(bombSlider);
    add(startPanel);
    
    
    ChoiceListener listener = new ChoiceListener();
    onePlayer.addActionListener(listener);
    twoPlayers.addActionListener(listener);
    easy.addActionListener(listener);
    medium.addActionListener(listener);
    hard.addActionListener(listener);
    custom.addActionListener(listener);
    start.addActionListener(listener);
    instructions.addActionListener(listener);
    
  }
  
  
  /**
   * Insert comments here
   */
  private class ChoiceListener implements ActionListener
  {
    public void actionPerformed (ActionEvent event)
    {
//      System.out.println("here");
      if (event.getSource() == onePlayer ) {
        choices[0] = 1;
        
        sizeSlider.setVisible(false);
        bombSlider.setVisible(false);
        System.out.println("one player");
//        if (choices.size() == 3){
//          OptionsPanel.thisPanel.add(startPanel);
//        }
      }
      if (event.getSource() == twoPlayers) {
        choices[0] =2;
        
        sizeSlider.setVisible(false);
        bombSlider.setVisible(false);
        System.out.println("two players");
//          if (choices.size() == 3){
//            OptionsPanel.thisPanel.add(startPanel);
//          }
      }
      else if (event.getSource() == easy ) {
        choices[1] = 10;
        choices[2] = 10;
        
        sizeSlider.setVisible(false);
        bombSlider.setVisible(false);
        System.out.println("easy");
        
//          if (choices.size() == 3){
//            OptionsPanel.thisPanel.add(startPanel);
//          }
      }
      else if ( event.getSource() == medium ) {
        choices[1] = 15;
        choices[2] = 40;
        
        sizeSlider.setVisible(false);
        bombSlider.setVisible(false);
        System.out.println("medium");
//          if (choices.size() == 3){
//            OptionsPanel.thisPanel.add(startPanel);
//          }
      }
      else if ( event.getSource() == hard) {
        choices[1] = 20;
        choices[2] = 60;
        sizeSlider.setVisible(false);
        bombSlider.setVisible(false);
        System.out.println("hard");
      }
      
      else if ( event.getSource() == custom) {
        
        sizeSlider.setVisible(true);
        bombSlider.setVisible(true);
        validate();
        repaint();
      }
      
      
      
      else if(event.getSource() == instructions){
        System.out.println("instructions");
        InstructionsPanel instructionsPanel = new InstructionsPanel();
        
        MinesweeperGUI.frame.getContentPane().removeAll();
        MinesweeperGUI.frame.getContentPane().add(instructionsPanel);
        MinesweeperGUI.frame.validate();
        MinesweeperGUI.frame.repaint();
        
        Dimension newSize = new Dimension(1100,640);
        MinesweeperGUI.frame.resize(newSize);
      }
      
      
      else if(event.getSource() == start){
        System.out.println("start");
        
        int numPlayers = choices[0];
        int pickedSize;
        int pickedBombs;
        
        if (custom.isSelected()){
          pickedSize = size.getValue();
          pickedBombs =(int) ((bombs.getValue()) * (Math.pow(pickedSize,2))/100);// in percentage
          System.out.println("custom");
          
          System.out.println("size" + pickedSize);
          System.out.println("bombs" + pickedBombs);
        }
        else{
          pickedSize = choices[1];
          pickedBombs = choices[2];
        }
        
        GamePanel gamePanel = new GamePanel(pickedSize, pickedSize, pickedBombs, numPlayers);
        Dimension newSize = new Dimension(100,500);
//        MinesweeperGUI.frame.setVisible(false);
        
        System.out.println("before removing");
        MinesweeperGUI.frame.getContentPane().removeAll();
        System.out.println("removed");
        MinesweeperGUI.frame.getContentPane().add(gamePanel);
        System.out.println("added");
//        MinesweeperGUI.frame.getContentPane().add(gamePanel);//Adding to content pane, not to Frame
        MinesweeperGUI.frame.validate();
        MinesweeperGUI.frame.repaint();
//        Dimension newSize = new Dimension(100,500);
        
        newSize.setSize(Math.max(pickedSize*31, 125), (pickedSize*31) + 130);
        MinesweeperGUI.frame.resize(newSize);
        
//        MinesweeperGUI.frame.pack();
        
      }
      
    }
  }
  
}
