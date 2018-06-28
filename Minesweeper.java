/**
 * WHO: @author Delaney Ugelstad
 * WHEN: November 19, 2017
 * WHAT: Minesweeper.java
 */
import java.util.*;
import javafoundations.*;

/**
 * Represents a game of Minesweeper.
 */
public class Minesweeper{
  private Cell[][] grid;
  private int numBombs;
  private int minesLeft;
  private int numUnrevealed;
  private int height;
  private int width;
  private boolean isGameWon;
  private boolean isGameLost;
  private CircularArrayQueue<String> turn;
  private int players;
  private int p1Flags;
  private int p2Flags;
  
  /**
   *Creates an instance of a new minesweeper game for a given number of 
   * players. Sets up a new game board of a given width, height, and number 
   * of mines, randomly assigns bombs to their locations, and sets the 
   * values that all cells will display once they are revealed.
   */
  public Minesweeper(int width, int height, int numBombs, int players){
    this.width = width;
    this.height = height;
    this.numBombs = numBombs;
    this.players = players; //the GUI restricts this to two players                                                 
    grid = new Cell[width][height];
    minesLeft = numBombs; //starts out as the number of bombs
    numUnrevealed = height * width; //starts out as the number of total cells
    isGameWon = false;
    isGameLost = false;
    p1Flags = 0;
    p2Flags = 0;
    
    //place the players into a queue
    turn = new CircularArrayQueue<String>();
    for (int i=1; i < players + 1; i++){
      turn.enqueue("Player " + i);
    }
    
    
    //place cells in the grid 
    for (int row=0; row < grid.length; row++){
      for (int col=0; col < grid[row].length; col++){
        Cell currentCell = new Cell();
        grid[row][col] = currentCell;
     //grid[row][col].reveal();         //uncomment to display cells for testing
      }
    }
    
    //place the mines in random cells and increment the value of the
    //surrounding cells
    Random rand = new Random();
    int mines = 0;
    while (mines < numBombs){ //while there are more bombs to be placed:
      int y = rand.nextInt(width); //create random y index
      int x = rand.nextInt(height); //create random x index
      if(grid[y][x].getValue() != -1){ //if it's not already a bomb
        grid[y][x].setValue(-1); //create the bomb at (x,y)
     //grid[y][x].reveal();         //uncomment to display the bombs for testing
        mines++; //increment the number of bombs created
        
        //increment the values of the cells surrounding the bomb:
        for (int i=y-1; i <= y+1 && i < grid.length; i++){
          for (int j=x-1; j <= x+1 && j < grid[y].length; j++){
            if(j>=0 && i>=0  //if x and y values are both positive
                 && grid[i][j].getValue() != -1){ //if its not a bomb
              grid[i][j].incrementValue();
            }
          }
        } 
      }
    }
  }
  
  
  /**
   * Getter method that returns the player whose turn it is currently
   * @return the player whose turn it is.
   */
  public String getCurrentPlayer(){
    return turn.first();
  }
  
  /**
   * Changes the turn of the active player
   */
  public void changePlayers(){
    String lastPlayer = turn.dequeue();
    turn.enqueue(lastPlayer);
  }
  
  /**
   * Changes the flag status of the cell at the specified location. If the cell
   * is currently unflagged, the cell is flagged, and if the cell is already
   * flagged, the cell is unflagged.
   * @param x The index of the row of the cell.
   * @param y The index of the column of the cell.
   */
  public void flag(int x, int y){
    //only unrevealed cells can be flagged and the game can't be over
    if(!grid[x][y].isRevealed() && !isGameOver()){ 
      //if the player incorrectly flags a cell that is not a mine, they lose.
      if (grid[x][y].getValue() != -1){
        isGameLost = true;
        endGame();
      }
      //if the player correctly flagged a cell, flag it, increase the number 
      //of cells that person has flagged decrement the mines left
      //We check isGameOver again to make sure an incorrectly flagged 
      //cell does not get labeled "correct" after the game is over
      if(!grid[x][y].isFlagged1() && !grid[x][y].isFlagged2() && !isGameOver()){ 
        if (getCurrentPlayer().equals("Player 1")){
          grid[x][y].flag1();
          p1Flags++;
        }
        if (getCurrentPlayer().equals("Player 2")){
          grid[x][y].flag2();
          p2Flags++;
        }
        minesLeft--;
      }
    }
  }
  
  /**
   * Getter method that returns the number of cells Player 1 has flagged.
   * @return the number of cells that Player 1 has flagged.
   */
  public int getP1Flags(){
    return p1Flags;
  }
  
  /**
   * Getter method that returns the number of cells Player 2 has flagged.
   * @return the number of cells that Player 2 has flagged.
   */
  public int getP2Flags(){
    return p2Flags;
  }
  
  
  /**
   * Reveals the value of the specified cell to the user. If the cell contains a
   * positive non-zero value, it is displayed. If it contains blank cell
   * (value = 0), then all ajacent cells are also revealed. This process
   * continues until the surrounding area is bordered by cells with numbers.
   * @param x The index of the row of the cell.
   * @param y The index of the column of the cell.
   */
  public void reveal(int x, int y){
    
    //When a player clicks on a cell that contains a number:
    if(x >= 0 && y >= 0 && x < width && y < height && grid[x][y].getValue() > 0){
      grid[x][y].reveal();
      numUnrevealed--;
    }
    
    //When a player clicks on a blank cell all neighboring cells are revealed
    //automatically:
    else{
      for(int i = y-1; i <= y+1 && i < height; i++){
        for(int j = x-1; j <= x+1 && j < width; j++){
          //if it's a valid cell that hasn't been revealed and isn't a bomb:
          if(j < width && j >= 0 && i < height && i >= 0 &&
             !grid[j][i].isRevealed() && grid[j][i].getValue() != -1){                      
            grid[j][i].reveal();
            //only decrement numUnrevealed for the blank cells because it is
            //also decremented in the base case for other cells
            if(grid[j][i].getValue() == 0){ 
              numUnrevealed--; 
            }
            reveal(j,i);
          }
        }
      }
    }
  }
  
  
  /**
   * Represents what would happen if a player clicked on a cell at the given
   * location. If the cell contains a bomb, the game is over and the player
   * loses. If all cells that don't contain mins have been revealed, the game
   * is over and the player has won. Otherwise, it reveals the appropriate
   * cells.
   * @param x The index of the row of the cell that is clicked.
   * @param y The index of the column of the cell that is clicked.
   */
  public void click(int x, int y){
    //if you click on something that is already revealed or flagged, nothing
    //will happen
    if(!grid[x][y].isRevealed() && !grid[x][y].isFlagged1() && !grid[x][y].isFlagged1() && !isGameOver()){
      //When a player clicks on a mine:
      if(grid[x][y].getValue() == -1){
        isGameLost = true;
        endGame();
      }
      //switch players
      else{
        reveal(x,y);
        changePlayers();
      }
      //When all non-bombs have been revealed:
      if(isGameWon()){  
        endGame();
        isGameWon = true;
      }
    }
  }
  
  
  /**
   * Determines if the game is over. This happens when either when a cell with
   * a bomb is revealed, or when  all other cells have been revealed except the
   * bombs.
   * @return Returns true if the game is over, and false otherwise.
   */
  public boolean isGameOver(){
    return (isGameWon || isGameLost);
  }
    
  
  /**
   * Ends the game. If a bomb was revealed, the player loses, and all the bombs
   * are revealed. if the all cells except the mines are revealed, the
   * game is won and all the bombs are revealed as well.
   */
  public void endGame(){
    for(int i=0; i < height; i++){
      for(int j=0; j < width; j++){
        if (grid[j][i].getValue()== -1)
          grid[j][i].reveal();
      }
    }
  }
  
  /**
   * Determines whether the game has been lost.
   * @return Returns true if the game has been lost (i.e. a player clicked on
   * a bomb or incorrectly flagged a cell), and false otherwise
   */
  public boolean isGameLost(){
    return isGameLost;
  }
  
  /**
   * Determines whether the game has been won.
   * @return Returns true if the game has been lost (i.e. All non-bomb cells 
   * have been revealed).
   */
  public boolean isGameWon(){
    return (numUnrevealed == numBombs);
  }
  
  
  /**
   * Returns the winner of the game if the game is over.
   * @return The winner of the game.
   */
  public String winner(){
    
    String winner = "No one";
    if (isGameWon() == true){
      if (p1Flags > p2Flags)
        winner = "Player 1";
      if (p2Flags > p1Flags)
        winner = "Player 2";
    }
    if (isGameLost()==true && players==2){
      if (getCurrentPlayer().equals("Player 1"))
        winner = "Player 2";
      else
        winner = "Player 1";
    }
    return winner;
  }
  
  
  /**
   * Returns a string representation of a minesweeper board game.
   * @return a string representation of a minesweeper board game.
   */
  public String toString(){
    String s = "";
    s += getCurrentPlayer() + "\n";
    
    for (int row=0; row < width; row++){
      for (int col=0; col < height; col++){
        s += grid[row][col] + " ";
      }
      s += "\n";
    }
    s += ("Mines left: " + minesLeft + "\nnumUnrevealed: " + numUnrevealed +
          "\nnumBombs: " + numBombs + "\np1Flags: " + p1Flags + 
          "\np2Flags: " + p2Flags);                                             //i dont think this is working
    return s;
  }
  
  
  /**
   * Getter method that returns the grid (a 2D array of cell objects)
   * @return The grid that represents the game board
   */
  public Cell[][] getGrid(){
    return grid;
  }
  
  
  /**
   * Main method for preliminary testing.
   * @param args Not used.
   */
  public static void main(String[] args){
    Minesweeper game = new Minesweeper(4, 5, 0, 1);
    System.out.println("flag an incorrect cell:");
    game.flag(0,2); //mines left will be -1
    System.out.println(game);
    
    
    game.reveal(3,3); //reveals all cells
    System.out.println(game);
    
    //cell.setValue() to set the bombs
  }
}



