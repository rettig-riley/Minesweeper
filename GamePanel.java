/**
 * WHO: @author Riley Rettig
 * WHAT: GamePanel.java
 * WHEN: 11/30/17
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
/**
 * Panel where Minesweeper game will be displayed
 */
public class GamePanel extends JPanel{
  //private variables for images used
  private ImageIcon hidden = new ImageIcon(this.getClass().getResource("/Images/hidden_cell.png"));
  private ImageIcon empty = new ImageIcon(this.getClass().getResource("/Images/empty_cell.png"));
  private ImageIcon bombRevealed = new ImageIcon(this.getClass().getResource("/Images/bomb_revealed.png"));
  private ImageIcon redFlag = new ImageIcon(this.getClass().getResource("/Images/red_flag.png"));
  private ImageIcon blueFlag = new ImageIcon(this.getClass().getResource("/Images/blue_flag.png"));
  private ImageIcon redCrossed = new ImageIcon(this.getClass().getResource("/Images/red_flag_crossed.png"));
  private ImageIcon blueCrossed = new ImageIcon(this.getClass().getResource("/Images/blue_flag_crossed.png"));
  
  private Minesweeper game;
  private Cell[][] grid;
  private Vector<JButton> buttonGrid; //vector is used to hold buttons so we can use the indexOf method
  private JButton back, newGame;
  private JLabel player,minesLeft,player1Mines,player2Mines,time;
  private javax.swing.Timer timer;
  private int height,width,numBombs;
  private int players;
  private int seconds = 0;
  private int minutes = 0;
  /**
   * Creates a new gamePanel
   * @param height   Height of grid
   * @param width    Width of grid
   * @param numBombs Number of bombs
   * @param players  Number of player
   */
  public GamePanel(int height, int width, int numBombs, int players){
    this.height = height;
    this.width = width;
    this.numBombs = numBombs;
    this.players = players;
    
    setLayout(new BorderLayout());
    
    //create all the components
    back = new JButton("Back");
    player = new JLabel("");
    minesLeft = new JLabel ("Mines left: " + numBombs);
    newGame = new JButton ("New Game");
    player1Mines = new JLabel("Player 1 Flags: 0");
    player2Mines = new JLabel(""); //Player 2's label doesn't show up unless its a 2 player game
    time = new JLabel("  00:00  ");
    timer = new javax.swing.Timer(1000,new TimerListener());
    GridPanel grid = new GridPanel();
    
    back.addActionListener(new BackListener());
    newGame.addActionListener(new NewGameListener());
    
    JPanel north = new JPanel();
    north.add(back);
    north.add(player);
    north.add(time);
    north.add(newGame);
    
    JPanel south = new JPanel();
    south.setLayout(new GridLayout(3,1));
    south.add(minesLeft);
    south.add(player1Mines);
    south.add(player2Mines);
    
    add(north, BorderLayout.NORTH);
    add(south, BorderLayout.SOUTH);
    add(grid, BorderLayout.CENTER);
  }
  
  /**
   * Panel that holds the buttons that will correspond to the cells in a Minesweeper game
   */
  public class GridPanel extends JPanel{
    /**
     * Creates a new GridPanel 
     */
    public GridPanel()
    {
      setLayout(new GridLayout(width,height));
      game = new Minesweeper (width,height,numBombs,players);
      grid = game.getGrid();
      player.setText(game.getCurrentPlayer());
      buttonGrid = new Vector<JButton>(); //vector 
      for (int i = 0; i< width*height; i++){
        buttonGrid.add(new JButton());
        //make button invisible
        buttonGrid.get(i).setBorderPainted(false);
        buttonGrid.get(i).setFocusPainted(false);
        buttonGrid.get(i).setContentAreaFilled(false);
        //give button hidden Icon
        buttonGrid.get(i).setIcon(hidden);
        //add listeners
        buttonGrid.get(i).addActionListener(new ButtonListener());
        buttonGrid.get(i).addMouseListener(new FlagListener());
        //add to panel
        add(buttonGrid.get(i));
      }
    }
  }
  
  /**
   * Method to update the GridPanel according to the status of the Mineweeper game
   */
  private void update(){
    for(int i = 0; i<grid.length; i++){//for every row
      for(int j = 0; j<grid[i].length;j++){//for every cell in each row
        if (grid[i][j].toString().equals("_")){
          buttonGrid.get(width*i+j).setIcon(hidden);
        }
        else if (grid[i][j].toString().equals("0")){
          buttonGrid.get(width*i+j).setIcon(empty);
        }
        else if (grid[i][j].toString().equals("B")){
          buttonGrid.get(width*i+j).setIcon(bombRevealed);
        }
        //F = player1's flags are red
        else if (grid[i][j].toString().equals("F")){ 
          buttonGrid.get(width*i+j).setIcon(redFlag);
        }
        //f = player2's flags are blue
        else if (grid[i][j].toString().equals("f")){ 
          buttonGrid.get(width*i+j).setIcon(blueFlag);
        }
        //otherwise it is a number
        else {
          JButton button = buttonGrid.get(width*i+j);
          button.setIcon(new ImageIcon(this.getClass().getResource("/Images/number_" + grid[i][j].toString() + ".png")));
        }
      }
    }
  }
  
  /**
   * When the game ends the timer stops, and a message dialog box comes up to tell user the results of the game
   */
  private void endGame(){
    timer.stop();
    String winMessage = "";
    String loseMessage = "";
    
    if(players==2){
      winMessage = "Congratulations, you avoided all the bombs! " + game.winner() + " flagged the most bombs!";
      loseMessage = "Game over.\n" + game.winner() + " wins!" ;
    }
    else{
      winMessage = "Congratulations, you avoided all the bombs! ";
      loseMessage = "Game over.";
    }
    //If the game is won in either single or two-player mode, the time is added
    winMessage +="\n Time: " + minutes + " minutes and " + seconds%60 + " seconds"; 
    
    if (game.isGameWon())
      JOptionPane.showMessageDialog (null, winMessage);
    else
      JOptionPane.showMessageDialog (null, loseMessage);
  }
  
  /**
   * MouseListener that is used to tell when a button is right-clicked in order to call the flag() method
   */
  private class FlagListener implements MouseListener
  {
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mousePressed(MouseEvent e) 
    {
      if (SwingUtilities.isRightMouseButton(e))
      {
        JButton a = (JButton)e.getSource();
        int num = buttonGrid.indexOf(a);
        int row = num/width;
        int column = num%width;
        game.flag(row,column);
        update();
        
        //displays a red flag with a cross if player 1 flags the wrong cell
        if (game.getCurrentPlayer().equals("Player 1") && game.isGameOver()){
          buttonGrid.get(width*row+column).setIcon(redCrossed);
          endGame();
        }
        //displays a blue flag with a cross if player 2 flags the wrong cell
        if (game.getCurrentPlayer().equals("Player 2") && game.isGameOver()){
          buttonGrid.get(width*row+column).setIcon(blueCrossed);
          endGame();
        }
        //update labels
        player1Mines.setText("Player 1 Flags: " + Integer.toString(game.getP1Flags()));
        if(players==2){
          player2Mines.setText("Player 2 Flags: " + Integer.toString(game.getP2Flags()));
        }
        minesLeft.setText("Mines left: " + (numBombs-game.getP1Flags()-game.getP2Flags()));
      }
    }
  }
  
  /**
   * Listener for every button in buttonGrid
   */
  private class ButtonListener implements ActionListener{
    /**
     * When a button is clicked, the equivalent cell in the minesweeper game will be clicked.
     * If it is the first button of a game clicked, the timer will start. 
     */
    public void actionPerformed(ActionEvent event){
      if (!timer.isRunning()){
        timer.start();
      }
      JButton a = (JButton)event.getSource();
      int num = buttonGrid.indexOf(a);
      int row = num/width;
      int column = num%width;
      game.click(row,column);
      update();
      player.setText(game.getCurrentPlayer()); //change label to show which player's turn it is
      
      //if a bomb is clicked, that one one turns red
      if (grid[row][column].toString().equals("B")){
        buttonGrid.get(width*row+column).setIcon(new ImageIcon(this.getClass().getResource("/Images/bomb_clicked.png")));
      }
      if (game.isGameOver()){
        endGame();
      }
    }
  }
  
  /**
   * Listener for the New Game Button
   */
  private class NewGameListener implements ActionListener{
    /**
     * When the New Game button is pressed, the timer resets and a new game of same dimensions is created
     */
    public void actionPerformed(ActionEvent event){
      timer.stop();
      seconds = 0;
      minutes = 0;
      time.setText("    00:00    ");
      game = new Minesweeper(width,height,numBombs,players);
      grid = game.getGrid();
      update();
    }
  }
  
  /**
   * Listener for the Back button
   */
  private class BackListener implements ActionListener{ 
    /**
     * When back button is pressed, return to options panel
     */
    public void actionPerformed(ActionEvent event){
      OptionsPanel options = new OptionsPanel();
      MinesweeperGUI.frame.getContentPane().removeAll();
      MinesweeperGUI.frame.getContentPane().add(options);
      MinesweeperGUI.frame.validate();
      MinesweeperGUI.frame.repaint();
      MinesweeperGUI.frame.pack();
    }
  }
  
  /**
   * Listener for timer
   */
  private class TimerListener implements ActionListener{
    /**
     * After every second, the label corresponding to the timer will update
     */
    public void actionPerformed (ActionEvent event){
      seconds++;
      minutes = (int) (seconds/60);
      if (minutes < 10){
        if ((seconds%60)<10){
          time.setText("  0" + minutes + ":0" + seconds%60 + "  ");
        }
        else{
          time.setText("  0" + minutes + ":" + seconds%60+ "  ");
        }
      }
      else{
        if ((seconds%60)<10){
          time.setText( "  " + minutes + ":0" + seconds%60 + "  ");
        }
        else{
          time.setText("  " + minutes + ":" + seconds%60+"  ");
        }
      }
    }
  }
}



