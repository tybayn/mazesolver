/* 
 * Tyler Bayn © 2016
 * Last Modified: 10/03/2016
 * This class contains the creature which explores the maze
 */
package mazesolver;

//Creature class
public class Creature {
    
    /***************************Data Members*****************************/
    private int[] position;
    
    /***************************Constructors*****************************/
    /* Default Constructor: creates an int array with two spots for
     * creature position
     * Input: None
     * Output: None
     * Returns: None 
     */
    public Creature(){
        position = new int[2];
    }
    
    /* Explicit Constructor: creates an in array with two spots for
     * creature position and loads creature into the maze
     * Input: Maze maze
     * Output: None
     * Returns: None 
     */
    public Creature(Maze maze){
        position = new int[2];
        
        //Puts the creature at the entrance of the maze
        enterMaze(maze);
    }
    
    /*******************************Methods*******************************/
    /* int getPositionH: returns the height position
     * Input: None
     * Output: None
     * Returns: position[0]
     */
    public final int getPositionH(){
        return position[0];
    }
    
     /* int getPositionW: returns the width position
     * Input: None
     * Output: None
     * Returns: position[1]
     */
    public final int getPositionW(){
        return position[1];
    }
    
    /* void enterMaze: puts the creature at the entrance of the maze
     * Input: Maze maze
     * Output: None
     * Returns: None 
     */
    public void enterMaze(Maze maze){
        position[0] = maze.getEntranceH();
        position[1] = maze.getEntranceW();
        
        //Place the creature in the maze
        maze.place(this);
    } 
    
    /* void moveNorth: moves the creature one block north
     * Input: Maze maze
     * Output: None
     * Returns: None 
     */
    public void moveNorth(){
        position[0]--;
    }
    
    /* void moveWest: moves the creature one block west
     * Input: Maze maze
     * Output: None
     * Returns: None 
     */
    public void moveWest(){
        position[1]--;
    }
    
    /* void moveSouth: moves the creature one block south
     * Input: Maze maze
     * Output: None
     * Returns: None 
     */
    public void moveSouth(){
        position[0]++;
    }
    
    /* void moveEast: moves the creature one block east
     * Input: Maze maze
     * Output: None
     * Returns: None 
     */
    public void moveEast(){
        position[1]++;
    }
}
