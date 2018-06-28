/**
 * WHO: @author Delaney Ugelstad
 * WHEN: November 19, 2017
 * WHAT: PlayGame.java
 * 
 * KNOWN BUGS: If the user enters something other than an int for height, width,
 * or numBombs, then an error occurs. If someone enters something other than a 
 * string for flag or click, an error occurs. The game is also not made for more
 * than two players, although it is possible to play with more than two players.
 * These issues are handled by the GUI.
 */

import java.util.*;
public class PlayGame{
  
  public static void main(String[] args){
    Scanner scan = new Scanner(System.in);
    System.out.println("Please enter the width");
    int width = scan.nextInt(); 
    
    System.out.println("Please enter the height");
    int height = scan.nextInt();  
    
    System.out.println("Please enter the number of bombs");
    int numBombs = scan.nextInt();  
    
    System.out.println("Please enter the number of players");
    int numPlayers = scan.nextInt();  
    
    Minesweeper game = new Minesweeper(width, height, numBombs, numPlayers);
    System.out.println(game);
    
    String option;
    
    System.out.println("Would you like to flag or click?");
    option = scan.next();
    while (option.equals("flag") || option.equals("click")){
      System.out.println("Please enter the x coordinate");
      int x = scan.nextInt();
      System.out.println("Please enter the y coordinate");
      int y = scan.nextInt();
      
      if(option.equals("flag"))
        game.flag(y,x);
      if(option.equals("click"))
        game.click(y,x);
      
      System.out.println(game);
      
      if (game.isGameOver()){
        option = "gameOver";
        System.out.print("Game Over. " + game.winner() + " Wins.");
      }
      else{
        System.out.println("Would you like to flag or click?");
        option = scan.next();
      }
    }
    scan.close();
  }
}
