/* 
 * Tyler Bayn © 2016
 * Last Modified: 05/24/2017
 * This class contains the maze
 */
package mazesolver;
import java.util.Scanner; //Allows for keyboard input
import java.io.*; //File input
import java.util.Random; //Random Number Generator

//Maze class
public class Maze {
    
    /***************************Data Members*****************************/
    private int width;
    private int height;
    private int[] entrance;
    private int[] exit;
    private char[][] map;
    
    /****************************Maze Parts******************************/
    //Public for use in driver
    public final char  visited = '.',
                       path = '•',
                       current = 'o';
    
    //Private for use in creation
    private final char wall = 'x',
                       door = 's',
                       space = ' ';
    
    /***************************Constructors*****************************/
    /* Default Constructor: initializes all variables to 0
     * Input: None
     * Output: None
     * Returns: None 
     */
    public Maze(){
        width = 0;
        height = 0;
        entrance = new int[2];
        exit = new int[2];
    }
    
    /* Explicit Constructor: loads maze file
     * Input: String fileName
     * Output: None
     * Returns: None 
     */
    public Maze(String fileName) throws FileNotFoundException, Exception{
        
        entrance = new int[2];
        exit = new int[2];
        load(fileName);
        
    }
    
    /* Explicit Constructor: creates empty maze
     * Input: int h , int w
     * Output: None
     * Returns: None 
     */
    public Maze(int h, int w) throws Exception{
        
        entrance = new int[2];
        exit = new int[2];
        height = h;
        width = w;
        map = new char[height][width];
        
    }
    
    /*******************************Methods*******************************/
    /* int setHeight: sets the height
     * Input: int num
     * Output: None
     * Returns: None
     */
    public void setHeight(int num){
        height = num;
    }
    
    /* int setWidth: sets the width
     * Input: int num
     * Output: None
     * Returns: None
     */
    public void setWidth(int num){
        width = num;
    }
    
    /* int getHeight: returns the height of the maze
     * Input: None
     * Output: None
     * Returns: height
     */
    public final int getHeight(){
        return height;
    }
    
    /* int getWidth: returns the width of the maze
     * Input: None
     * Output: None
     * Returns: width
     */
    public final int getWidth(){
        return width;
    }
    
    /* int getEntranceH: returns the entrance height position
     * Input: None
     * Output: None
     * Returns: entrance[0]
     */
    public final int getEntranceH(){
        return entrance[0];
    }
    
     /* int getEntranceW: returns the entrance width position
     * Input: None
     * Output: None
     * Returns: entrance[1]
     */
    public final int getEntranceW(){
        return entrance[1];
    }
    
   /* int getEntranceH: returns the entrance height position
     * Input: None
     * Output: None
     * Returns: exit[0]
     */
    public final int getExitH(){
        return exit[0];
    }
    
     /* int getEntranceW: returns the entrance width position
     * Input: None
     * Output: None
     * Returns: exit[1]
     */
    public final int getExitW(){
        return exit[1];
    }
    
    /* void setLine: sets a line in the maze to string
     * Input: String line, int row
     * Output: None
     * Returns: None 
     */
    public void setLine(String line, int row){
        for(int i = 0; i < width; i++)
            map[row][i] = line.charAt(i);
    }
    
    /* void setEntranceExit: used in maze creation, set maze entrance and exit
     * Input: None
     * Output: None
     * Returns: None 
     */
    public void setEntranceExit(){
        entrance[0] = 0;
        entrance[1] = 0;
        exit[0] = 0;
        exit[1] = 0;
        
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(map[i][j] == 'e'){
                    entrance[0] = i;
                    entrance[1] = j;
                    map[i][j] = ' ';
                }
                if(map[i][j] == 'E'){
                    exit[0] = i;
                    exit[1] = j;
                    map[i][j] = ' ';
                }
            }
        }
    }
    
    /* void load: reads in the maze from a file into the variables
     * Input: String fileName
     * Output: None
     * Returns: None 
     */
    public void load(String fileName) throws FileNotFoundException, Exception{
        
        //Create scanner and for file input
        Scanner fileInput;
        File inFile = new File(fileName);
        
        //String used to capture data from file
        String line;
       
        //Set scanner to correct file
        fileInput = new Scanner(inFile);
        
        //Gather variable data from appropriatly formatted maze file
        width = fileInput.nextInt(); //Width of maze
        height = fileInput.nextInt(); //Height of maze
        entrance[0] = fileInput.nextInt(); //Height of maze entrance
        entrance[1] = fileInput.nextInt(); //Width of maze entrance
        exit[0]= fileInput.nextInt(); //Height of maze exit
        exit[1] = fileInput.nextInt(); //Width of maze exit

        //Create 2D array to hold the actual maze
        map = new char[height][width];
        
        //Clear scanner for maze input
        line = fileInput.nextLine();
        
        //Load the maze, line by line, into the array
        for(int j = 0; j < height; j++){
            line = fileInput.nextLine();
            for(int i = 0; i < width; i++){
                map[j][i] = line.charAt(i);
            }
        }      
    }
    
    /* void place: places creature in maze
     * Input: creature
     * Output: None
     * Returns: None
     */
    public void place(Creature creature){
        map[creature.getPositionH()][creature.getPositionW()] = current;
    }
    
    /* void mark: marks spots in maze as visited, open, or current
     * Input: creature, char
     * Output: None
     * Returns: None
     */
    public void mark(Creature creature, char type){
        
        map[creature.getPositionH()][creature.getPositionW()] = type;
    }
    
    /* void create: creates a new maze array with given values
     * Input: int h, int w
     * Output: None
     * Returns: None 
     */
    public void create(int h, int w){
        width = w;
        height = h;
        
        //Create 2D array to hold the actual maze
        map = new char[height][width];
        
        //Fill in the walls of the maze
       for(int i = 0; i < width; i++){
           map[0][i] = wall;
           map[height - 1][i] = wall;
       }
       
       for(int j = 0; j < height; j++){
           map[j][0] = wall;
           map[j][width - 1] = wall;
       }
       
       //Fill in the center with spaces
       for(int j = 1; j < height - 1; j++){
           for(int i = 1; i < width - 1; i++){
               map[j][i] = space;
           }
       }
        
        //Create array that holds quadrant[H min][H max][W min][w max]
        int[] quadrant = {0, height - 1, 0, width - 1};
        
        //Start using a recursive division method to create a maze
        splitMazeH(quadrant);
        
        //Cleans maze of extra characters
        clean();
        
        //Creates entrance
        boolean created = false;
        int j = 1;
        do{
            if(map[j][1] == space){
                map[j][0] = space;
                entrance[0] = j;
                entrance[1] = 0;
                created = true;
            }
            else
                j++;
        }while(created == false);
        
        int i = 1;
        while(created == false){
            if(map[1][i] == space){
                map[0][i] = space;
                entrance[0] = 0;
                entrance[1] = i;
                created = true;
            }
            else
                i++;
        }
        
        //Creates exit
        created = false;
        j = height - 2;
        do{
            if(map[j][width - 2] == space){
                map[j][width - 1] = space;
                exit[0] = j;
                exit[1] = width - 1;
                created = true;
            }
            else
                j--;
        }while(created == false);
        
        i = width - 2;
        while(created == false){
            if(map[height - 2][i] == space){
                map[height - 1][i] = space;
                exit[0] = height - 1;
                exit[1] = i;
                created = true;
            }
            else
                i--;
        }
    }
    
    /* String export: exports new maze to a txt file
     * Input: None
     * Output: None
     * Returns: String new filename 
     */
    public String export() throws Exception{
        
        //Create string to hold new file name
        String fileName = null;
        
        //Create new file
        File exportFile;
        int fileNumber = 1;
        do{
            fileName = "genMaze" + fileNumber + ".txt";
            exportFile = new File(fileName);
            
            if(exportFile.exists())
                fileNumber++;
               
        }while(exportFile.exists());
        
        //Write size and positions to the file.
        FileWriter writer = new FileWriter(exportFile);
        writer.write(width + " " + height + "\r\n");
        writer.write(entrance[0] + " " + entrance[1] + "\r\n");
        writer.write(exit[0] + " " + exit[1] + "\r\n");
        for(int j = 0; j < height; j++){
            for(int i = 0; i < width; i++){
                writer.write(map[j][i]);
            }
            writer.write("\r\n");
        }
        writer.close();
        
        return fileName;
    }
    
    /* String export: exports created maze to a txt file
     * Input: String fileName
     * Output: None
     * Returns: None 
     */
    public void export(String fileName) throws Exception{

        //Create new file
        File exportFile = new File(fileName);
        
        //Write size and positions to the file.
        FileWriter writer = new FileWriter(exportFile);
        writer.write(width + " " + height + "\r\n");
        writer.write(entrance[0] + " " + entrance[1] + "\r\n");
        writer.write(exit[0] + " " + exit[1] + "\r\n");
        for(int j = 0; j < height; j++){
            for(int i = 0; i < width; i++){
                writer.write(map[j][i]);
            }
            writer.write("\r\n");
        }
        writer.close();
    }
    
    /* void display: displays the maze to the screen
     * Input: None
     * Output: The maze
     * Returns: None 
     */
    public final void display(){
        System.out.println();
        
        //For loop that prints array
        //Changes colors for easy recognition of path
        for(int j = 0; j < height; j++){
            for(int i = 0; i < width; i++){
                
                //If path or creature location, show blue
                if(map[j][i] == path || map[j][i] == current)
                    System.out.print("\033[34m" + map[j][i] + space);
                
                //If varied path, show red
                else if(map[j][i] == visited)
                    System.out.print("\033[31m" + map[j][i] + space); 
                
                //If wall, display black
                else
                    System.out.print("\033[0m" + map[j][i] + space);
            }
            System.out.println();
        }
        System.out.println();
    }
 
    /* boolean isWall: checks to see if location is a wall
     * Input: int H, int W
     * Output: None
     * Returns: boolean value 
     */
    public final boolean isWall(int H, int W){
        
        //If the location on map is X, it is a wall
        if(map[H][W] == wall)
            return true;
        
        return false;
    }
    
    /* boolean isVisited: checks for loactions that have already been visited
     * Input: int H, int W
     * Output: None
     * Returns: boolean value 
     */
    public final boolean isVisited(int H, int W){
        
        //If the location on the map is *, it has been visited
        if(map[H][W] == visited)
            return true;
        
        return false;
    }
    
    /* boolean isExit: checks if creature is at the exit
     * Input: Creature creature
     * Output: None
     * Returns: boolean value 
     */
    public final boolean isExit(Creature creature){
        
        //The position of the creature is the same as the maze exit, return true
        if(creature.getPositionH() == exit[0] && creature.getPositionW() == exit[1])
            return true;
        
        return false;
    }
    
    /* void clean: removes any marks on maze maps other than walls
     * Input: None
     * Output: None
     * Returns: None 
     */
    public void clean(){
        for(int j = 0; j < height; j++){
            for(int i = 0; i < width; i++){
                if(map[j][i] != wall)
                    map[j][i] = space;
            }
        }
    }
   
    /* void splitMazeH: splits maze into two parts vertically
     * Input: int[] quadrant (holds min and max height and width)
     * Output: None
     * Returns: None
     */
    public void splitMazeH(int[] quadrant){
        
        //Check if 2x2 square
        if(quadrant[1] - quadrant[0] == 3 && quadrant[3] - quadrant[2] == 3){
            
            if(map[quadrant[0] + 1][quadrant[2]] == wall && map[quadrant[0]][quadrant[2] + 1] == wall){
                map[quadrant[0] + 1][quadrant[2] + 1] = wall;
            }
            else if(map[quadrant[0] + 1][quadrant[3]] == wall && map[quadrant[0]][quadrant[3] - 1] == wall){
                map[quadrant[0] + 1][quadrant[3] - 1] = wall;
            }
            else if(map[quadrant[1] - 1][quadrant[2]] == wall && map[quadrant[1]][quadrant[2] + 1] == wall){
                map[quadrant[1] - 1][quadrant[2] + 1] = wall;
            }
            else if(map[quadrant[1] - 1][quadrant[3]] == wall && map[quadrant[1]][quadrant[3] - 1] == wall){
                map[quadrant[1] - 1][quadrant[3] - 1] = wall;
            }
        }
        
        //Check if 2 wide hallway
        else if(quadrant[3] - quadrant[2] == 3){
              
            for(int j = quadrant[0] + 1; j < quadrant[1]; j++){    
                
                if(map[j-1][quadrant[3] - 1] != door){
                    if(map[j][quadrant[3]] != door){
                        if(map[j+1][quadrant[3] - 1] != door){
                            map[j][quadrant[3] - 1] = wall;                    
                        }
                    }
                }
            }           
        }
            
        //Check if 3x3 square
        else if(quadrant[1] - quadrant[0] == 4 && quadrant[3] - quadrant[2] == 4){
            
            map[quadrant[0] + 2][quadrant[2] + 2] = wall;
            
            if(map[quadrant[0]][quadrant[2] + 2] == wall){
                map[quadrant[0] + 1][quadrant[2] + 2] = wall;
                map[quadrant[0] + 3][quadrant[2] + 2] = door;
            }
            else if(map[quadrant[0] + 2][quadrant[2]] == wall){
                map[quadrant[0] + 2][quadrant[2] + 1] = wall;
                map[quadrant[0] + 2][quadrant[2] + 3] = door;
            }
            else if(map[quadrant[1]][quadrant[2] + 2] == wall){
                map[quadrant[1] - 1][quadrant[2] + 2] = wall;
                map[quadrant[1] - 3][quadrant[2] + 2] = door;
            }
            else if(map[quadrant[0] + 2][quadrant[3]] == wall){
                map[quadrant[0] + 2][quadrant[3] - 1] = wall;
                map[quadrant[0] + 2][quadrant[3] - 3] = door;
            }   
        }
        
        //Check if 3 wide hallway
        else if(quadrant[3] - quadrant[2] == 4 && (map[quadrant[0]][quadrant[2] + 2] == door || map[quadrant[1]][quadrant[2] + 2] == door)){
            
            for(int i = quadrant[0] + 2; i < quadrant[1] - 1; i++){
                map[i][quadrant[2] + 2] = wall;
            }
            
            //Checks if block was placed
            boolean placed;
            int i = quadrant[0] + 2;
            
            do{
                placed = false;
                if(map[i][quadrant[2]] == wall){
                    map[i][quadrant[2] + 1] = wall;
                    placed = true;
                }
                
                i++; 
            }while(placed == false);
        }
        
        //Check if 4 wide hallway
        else if((quadrant[3] - quadrant[2] == 5)
                && ((map[quadrant[0]][quadrant[2] + 2] == door && map[quadrant[1]][quadrant[2] + 3] == door) 
                || (map[quadrant[0]][quadrant[2] + 3] == door && map[quadrant[1]][quadrant[2] + 2] == door))){
        
            splitMazeW(quadrant);
        }
        
        //Check if space between walls is greater than 1
        else if(quadrant[3] - quadrant[2] > 2 && quadrant[1] - quadrant[0] > 2){
            
            //Create int variables for line and space
            int line, space;
            
            //Generate random values
            Random rand; 
            
            //Generate line and passageway
            do{               
                rand = new Random();
                line =  rand.nextInt(quadrant[3] - quadrant[2] - 3) + (quadrant[2] + 2);
                space = rand.nextInt(quadrant[1] - quadrant[0] - 1) + (quadrant[0] + 1);    
            }while(map[quadrant[0]][line] == door || map[quadrant[1]][line] == door);

            //Draw line with space
            for(int j = quadrant[0]; j < quadrant[1]; j++){
                if(j != space)
                    map[j][line] = wall;
                else
                    map[j][line] = door;
            }

            //New quad to hold values
            int[] quadNew = new int[4];
            
            //Set quadrant to correct values
            quadNew[0] = quadrant[0];
            quadNew[1] = quadrant[1];
            quadNew[2] = quadrant[2];
            quadNew[3] = line;
            splitMazeW(quadNew);
            
            //Set quadrant to divide other half
            quadNew[0] = quadrant[0];
            quadNew[1] = quadrant[1];
            quadNew[2] = line;
            quadNew[3] = quadrant[3];
            splitMazeW(quadNew);
        }
    }
    
    /* void splitMazeW: splits maze into two parts horizontally
     * Input: int[] quadrant (holds min and max height and width)
     * Output: None
     * Returns: None
     */
    public  void splitMazeW(int[] quadrant){
        
        //Check if 2x2 square
        if(quadrant[1] - quadrant[0] == 3 && quadrant[3] - quadrant[2] == 3){
            
            if(map[quadrant[0] + 1][quadrant[2]] == wall && map[quadrant[0]][quadrant[2] + 1] == wall){
                map[quadrant[0] + 1][quadrant[2] + 1] = wall;
            }
            else if(map[quadrant[0] + 1][quadrant[3]] == wall && map[quadrant[0]][quadrant[3] - 1] == wall){
                map[quadrant[0] + 1][quadrant[3] - 1] = wall;
            }
            else if(map[quadrant[1] - 1][quadrant[2]] == wall && map[quadrant[1]][quadrant[2] + 1] == wall){
                map[quadrant[1] - 1][quadrant[2] + 1] = wall;
            }
            else if(map[quadrant[1] - 1][quadrant[3]] == wall && map[quadrant[1]][quadrant[3] - 1] == wall){
                map[quadrant[1] - 1][quadrant[3] - 1] = wall;
            }
        }
        
         //Check if 2 wide hallway
        else if(quadrant[1] - quadrant[0] == 3){
              
            for(int i = quadrant[2] + 1; i < quadrant[3]; i++){    
                
                if(map[quadrant[1] - 1][i-1] != door){
                    if(map[quadrant[1]][i] != door){
                        if(map[quadrant[1] - 1][i+1] != door){
                            map[quadrant[1] - 1][i] = wall;                    
                        }
                    }
                }
            }           
        }
        
        //Check if 3x3 square
        else if(quadrant[1] - quadrant[0] == 4 && quadrant[3] - quadrant[2] == 4){
            
            map[quadrant[0] + 2][quadrant[2] + 2] = wall;
            
            if(map[quadrant[0]][quadrant[2] + 2] == wall){
                map[quadrant[0] + 1][quadrant[2] + 2] = wall;
                map[quadrant[0] + 3][quadrant[2] + 2] = door;
            }
            else if(map[quadrant[0] + 2][quadrant[2]] == wall){
                map[quadrant[0] + 2][quadrant[2] + 1] = wall;
                map[quadrant[0] + 2][quadrant[2] + 3] = door;
            }
            else if(map[quadrant[1]][quadrant[2] + 2] == wall){
                map[quadrant[1] - 1][quadrant[2] + 2] = wall;
                map[quadrant[1] - 3][quadrant[2] + 2] = door;
            }
            else if(map[quadrant[0] + 2][quadrant[3]] == wall){
                map[quadrant[0] + 2][quadrant[3] - 1] = wall;
                map[quadrant[0] + 2][quadrant[3] - 3] = door;
            }   
        }
        
        //Check if 3 wide hallway
        else if(quadrant[1] - quadrant[0] == 4 && (map[quadrant[0] + 2][quadrant[2]] == door || map[quadrant[0] + 2][quadrant[3]] == door)){
            
            for(int i = quadrant[2] + 2; i < quadrant[3] - 1; i++){
                map[quadrant[0] + 2][i] = wall;
            }
            
            //Checks if block was placed
            boolean placed;
            int i = quadrant[2] + 2;
            
            do{
                placed = false;
                if(map[quadrant[0]][i] == wall){
                    map[quadrant[0] + 1][i] = wall;
                    placed = true;
                }
                
                i++; 
            }while(placed == false);
        }
        
        //Check if 4 wide hallway
        else if((quadrant[1] - quadrant[0] == 5)
                && ((map[quadrant[0] + 2][quadrant[2]] == door && map[quadrant[0] + 3][quadrant[3]] == door) 
                || (map[quadrant[0] + 3][quadrant[2]] == door && map[quadrant[0] + 2][quadrant[3]] == door))){
        
            splitMazeH(quadrant);
        }
        
        //Check if space between walls is greater than 1
        else if(quadrant[1] - quadrant[0] > 2 && quadrant[3] - quadrant[2] > 2){

           //Create int variables for line and space
            int line, space;
            
            //Generate random values
            Random rand; 
            
            //Generates line and passageway
            do{               
                rand = new Random();   
                line =  rand.nextInt(quadrant[1] - quadrant[0] - 3) + (quadrant[0] + 2);
                space = rand.nextInt(quadrant[3] - quadrant[2] - 1) + (quadrant[2] + 1);                                  
                
            }while(map[line][quadrant[2]] == door || map[line][quadrant[3]] == door);

            //Draw line with space
            for(int i = quadrant[2]; i < quadrant[3]; i++){
                if(i != space)
                    map[line][i] = wall;
                else
                    map[line][i] = door;
            }

            //New quad to hold values
            int[] quadNew = new int[4];
            
            //Set quadrant to correct values
            quadNew[0] = quadrant[0];
            quadNew[1] = line;
            quadNew[2] = quadrant[2];
            quadNew[3] = quadrant[3];
            splitMazeH(quadNew);
            
            //Set quadrant to divide other half
            quadNew[0] = line;
            quadNew[1] = quadrant[1];
            quadNew[2] = quadrant[2];
            quadNew[3] = quadrant[3];
            splitMazeH(quadNew);
        
        }
    } 
}

