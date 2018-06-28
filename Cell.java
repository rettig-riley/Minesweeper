/**
 * WHO: @author Delaney Ugelstad
 * WHEN: November 19, 2017
 * WHAT: Cell.java
 */

import java.util.*;

/**
 * Represents a cell in the minesweeper game. Before the cell is revealed, it
 * displays a value of "-". Afterwards, it displays its value.
 */
public class Cell{
  
  //contains the two values that will be displayed to the user. The first
  //element is what the cell displays before it is revealed, and the second
  //element is the value that is revealed to the user once it is uncovered.
  private Stack<String> displayedValues;
  
  //  The value is equal to -1 if the cell contains a bomb, 0 if the cell
  //  contains nothing, and otherwise the integer corresponding to
  //  the number of bombs in adjacent cells (including diagonal adjacent cells)
  private int value;
  
  private boolean isRevealed;
  private boolean isFlagged1; //player 1's flag
  private boolean isFlagged2; //player 2's flag
  
  /**
   * Creates an instance of a Cell object.
   */
  public Cell(){
    displayedValues = new Stack<String>(); //initially empty
    value = 0;
    isFlagged1 = false;
    isFlagged2 = false;
    isRevealed = false;
    setDisplayedValues();
  }
  
  
  /**
   * Determines whether the cell is flagged by player 1.
   * @return Returns true if the cell is flagged by player 1, and false otherwise.
   */
  public boolean isFlagged1(){
    return isFlagged1;
  }
  
  /**
   * Determines whether the cell is flagged by player 2.
   * @return Returns true if the cell is flagged by player 2, and false otherwise.
   */
  public boolean isFlagged2(){
    return isFlagged2;
  }
  
  /**
   * Determines if the contents of the cell have been revealed to the user.
   * @return Returns true if the cell has been revealed, and false otherwise.
   */
  public boolean isRevealed(){
    return isRevealed;
  }
  
  /**
   * Changes the string representation of the cell that the user will see.
   * Before the cell is revealed, each cell appears as "-". After reveal() is
   * called, the cell appears as a value.
   */
  public void reveal(){ //pops the 0th index
    isRevealed = true;
  }
  
  /**
   * Getter method that returns the value of what is hidden beneath the cell.
   * The value is equal to -1 if the cell contains a bomb, 0 if the cell
   * contains nothing, and otherwise the integer corresponding to
   * the number of bombs in adjacent cells (including diagonal adjacent cells).
   */
  public int getValue(){
    return value;
  }
  
  /**
   * If the cell is not flagged, it flags the cell with player 1's flag (because 
   * player 1 thinks it contains a bomb) by setting isFlagged1 to true. If the 
   * cell is already flagged, it unflags the cell.
   */
  public void flag1(){
    if(isFlagged1 == false && isFlagged2 == false)
      isFlagged1 = true;
  }
  
  /**
   * If the cell is not flagged, it flags the cell with player 2's flag (because 
   * player 2 thinks it contains a bomb) by setting isFlagged2 to true. If the 
   * cell is already flagged, it unflags the cell.
   */
  public void flag2(){
    if(isFlagged2 == false && isFlagged1 == false)
      isFlagged2 = true;
  }
  
  /**
   * Setter method that sets the value of the cell
   * @param myValue The new integer that the cell's value is set to.
   */
  public void setValue(int myValue){
    this.value = myValue;
    setDisplayedValues();
  }
  
  /**
   * Increments the cell's value by 1.This is used to update the 
   * values of the cells surrounding bombs.
   */
  public void incrementValue(){
    value++;
  }
  
  /**
   * Sets the the two options for values that the cell can display. The value
   * in the 0th index is what is displayed before the cell has been revealed,
   * and the value in index 1 is what is displayed after the cell has been
   * revealed.
   */
  public void setDisplayedValues(){
    displayedValues.addElement(Integer.toString(value));
    displayedValues.addElement("_");
  }
  
  /**
   * Returns the string representation of a cell.
   * @return the string representation of a cell.
   */
  public String toString(){                                                                
    if(isRevealed()){
      if(value==-1)
        return "B";
      else
        return Integer.toString(value);
    }
    else{
      if(isFlagged1())
        return "F";
      if(isFlagged2())
        return "f";
      else
        return "_";
    }
  }
  
  /**
   * Main method for testing the Cell class.
   * @param args Not used.
   */
  public static void main(String[] args){
    Cell myCell = new Cell();
    System.out.println("A new cell was created.");
    
    System.out.println("\nThe cell is currently displayed to the user as: "
                         + myCell);
    
    System.out.println("\nTest getValue():");
    System.out.println(myCell.getValue());
    
    System.out.println("\nIs the content of the cell revealed to the user?");
    System.out.println(myCell.isRevealed());
    
    myCell.reveal();
    System.out.println("\nThe cell content has been revealed to the user.");
    System.out.println("The cell is currently displayed to the user as: "
                         + myCell);
    
    System.out.println("\nIs the content of the cell revealed to the user now?");
    System.out.println(myCell.isRevealed());
    
    myCell.setValue(2);
    System.out.println("\nThe value was set to 2.");
    
    System.out.println("\nTest getValue():");
    System.out.println(myCell.getValue());
    
    System.out.println("\nTest incrementValue():");
    myCell.incrementValue();
    System.out.println("The value is now: " + myCell.getValue());
    
    myCell.reveal();
    System.out.println("\nThe cell contents have been revealed to the user.");
    System.out.println("The cell is currently displayed to the user as: "
                         + myCell);
    
    System.out.println("\nIs the cell flagged by player 1? (FALSE)");
    System.out.println(myCell.isFlagged1());
    
    myCell.flag1();
    System.out.println("\nThe cell that has been flagged by player 1.");
    System.out.println("\nIs the cell flagged by player 1 now? (TRUE)");
    System.out.println(myCell.isFlagged1());
    System.out.println("\nIs the cell flagged by player 2? (FALSE)");
    System.out.println(myCell.isFlagged2());
    myCell.flag2();
    System.out.println("\nThe cell has been flagged by player 2. (TRUE)");
    System.out.println("\nIs the cell flagged by player 2 now?");
    System.out.println(myCell.isFlagged2());
    
    
    Cell cell2 = new Cell();
    
    System.out.println("\nSet displayed values for this new cell before it has "
                         + "been called within another method:");
    
    cell2.setDisplayedValues();
    System.out.println("\nThe dislpayed values have been set.");
    System.out.println("The cell is currently displayed to the user as: "
                         + cell2);
    
    cell2.flag1();
    System.out.println("is the cell flagged:" + myCell.isFlagged2());
    System.out.println("The cell is currently displayed to the user as: "
                         + cell2);
    
    cell2.reveal();
    System.out.println("\nThe cell contents have been revealed to the user.");
    System.out.println("The cell is currently displayed to the user as: "
                         + cell2);
    
//flag2 needs to be testing
  }
}


