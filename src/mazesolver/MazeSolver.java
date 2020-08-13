/* 
 * Tyler Bayn © 2016
 * Last Modified: 05/24/2017
 * This program reads a maze from a file and solves it
 */
package mazesolver;
import java.util.Scanner; //Allows for keyboard input
import java.util.InputMismatchException; //Catches input data type validity
import java.io.*; //File input

//Class that contains the main method
public class MazeSolver {

    /* Void Main: creates a creature, loads a maze, and shows a solution to the maze
     * Input: None
     * Output: Standard interactions with user
     * Returns: None
     */
    public static void main(String[] args) {
        
        //Greeting and purpose message
        System.out.println("Greetings, this program will find the solution for a maze.");
        
        //Set up scanner for keyboard input and refreshes it
        Scanner keyboard = new Scanner(System.in);
        
        //Boolean variable that tells the loop if the input was successful
        Boolean success;
        
        //Integer for holding user decision
        int choice;
        char save;
        
        //Create creature, maze, and filename variables
        Maze maze = new Maze();
        Creature creature = new Creature();
        Creature testCreature;
        String fileName;
        String line = " ";
        int width, height;
        
         //Loop that runs again if exception is caught
        do{
            choice = -1;
            success = true;
            try{
                 
                 do{
                    //Show menu and get user choice
                    showMenu();
                    System.out.print("\nYour Choice: ");
                    choice = keyboard.nextInt();
                                      
                    switch(choice){
//CASE 1 SOLVE MAZE ***********
                        case 1: //Solve a maze
                            //Display Current Mazes
                            showFiles();
                            
                            //Get file name for maze
                            System.out.print("\nInput file name: ");
                            fileName = keyboard.next();
                            
                            //Load maze from file
                            maze.load(fileName);
                            
                            //Get creature to start of maze
                            creature.enterMaze(maze);
                            
                            //Display the maze before it is solved
                            System.out.print("\nThis is the maze in " + fileName);
                            maze.display();
                            
                            //Determine direction to go based on entrance location
                            if(creature.getPositionH() == maze.getHeight() - 1)
                                goNorth(maze, creature);
                            else if(creature.getPositionW() == maze.getWidth() - 1)
                                goWest(maze, creature);
                            else if(creature.getPositionH() == 0)
                                goSouth(maze, creature);
                            else if(creature.getPositionW() == 0)
                                goEast(maze, creature);
                            else if(creature.getPositionW() > 0 && creature.getPositionW() < maze.getWidth() - 1 && creature.getPositionH() > 0 && creature.getPositionH() < maze.getHeight() - 1)
                                startLabyrinth(maze, creature);
                            else
                                throw new Exception("Error: Invalid Starting Position!");
                            
                            //Display the solved maze
                            if(creature.getPositionH() == maze.getExitH() && creature.getPositionW() == maze.getExitW())
                                System.out.print("Solution:");
                            else
                                System.out.print("No Solution!");
                            maze.display();
                            
                            break;
                         
//CASE 2 DISPLAY MAZE**********
                        case 2: //Display a maze
                            //Display Current Mazes
                            showFiles();
                            
                            //Get file name for maze
                            System.out.print("\nInput file name: ");
                            fileName = keyboard.next();
                            
                             //Load maze from file
                            maze.load(fileName);
                            
                            //Display the maze
                            System.out.print("\nThis is the maze in " + fileName);
                            maze.display();
                            break;

//CASE 3 CREATE MAZE***********
                        case 3: //Create a maze
                            //Get maze width from user
                            System.out.print("\nInput new maze width: ");
                            width = keyboard.nextInt();

                            //Validate input
                            while(width < 3){
                                System.out.println("\nWidth must be greater than 3!");
                                System.out.print("Try again: ");
                                width = keyboard.nextInt();      
                            }

                            //Get maze height from user
                            System.out.print("Input new maze height: ");
                            height = keyboard.nextInt();

                            //Validate input
                            while(height < 3){
                                System.out.println("\nHeight must be greater than 3!");
                                System.out.print("Try again: ");
                                width = keyboard.nextInt();      
                            }
                            
                            //Create new, empty maze of specified size
                            Maze createMaze = new Maze(height, width);
                            
                            //Dipslay instructions
                            System.out.println("\nTo input a maze, type in the maze row by row using the following:");
                            System.out.println("  Walls = 'x'");
                            System.out.println("  Spaces/Hallways = ' '");
                            System.out.println("  Entrance = 'e'");
                            System.out.println("  Exit = 'E'");
                            System.out.println("\nAfter you input a row, press enter and begin the next row.");
                            System.out.println("Input maze (type cancel to exit maze creation):");
                            
                            //Clear keyboard buffer
                            keyboard.nextLine();
                            
                            //Get input from user to input into the maze
                            for(int i = 0; i < createMaze.getHeight(); i++){
                                line = keyboard.nextLine();
                                if(!line.contains("cancel"))
                                    createMaze.setLine(line, i);
                                else
                                    break;
                            }
                            
                            //Check for quit
                            if(line.contains("cancel")){
                                System.out.println("\nMaze creation cancelled!");
                                break;
                            }
                            
                            //Get the entrance and exit
                            createMaze.setEntranceExit();
                            testCreature = new Creature();
                            testCreature.enterMaze(createMaze);
                            
                            //See if maze is solvable
                            if(testCreature.getPositionH() == createMaze.getHeight() - 1)
                                goNorth(createMaze, testCreature);
                            else if(testCreature.getPositionW() == createMaze.getWidth() - 1)
                                goWest(createMaze, testCreature);
                            else if(testCreature.getPositionH() == 0)
                                goSouth(createMaze, testCreature);
                            else if(testCreature.getPositionW() == 0)
                                goEast(createMaze, testCreature);
                            else if(testCreature.getPositionW() > 0 && testCreature.getPositionW() < createMaze.getWidth() - 1 && testCreature.getPositionH() > 0 && testCreature.getPositionH() < createMaze.getHeight() - 1)
                                startLabyrinth(createMaze, testCreature);
                            else
                                throw new Exception("Error: Invalid Starting Position!");
                            
                            //Check if maze is solvable
                            if(testCreature.getPositionH() != createMaze.getExitH() || testCreature.getPositionW() != createMaze.getExitW())
                                System.out.println("\nThe maze is not solvable!");
                            else{
                                System.out.println("\nYour maze was successfully input!");
                            
                                //Display the maze
                                createMaze.clean();
                                createMaze.display();
                                
                                //Check if user wants to save the maze
                                System.out.print("Would you like to save this maze? <y/n>: ");
                                save = keyboard.next().charAt(0);;
                                
                                if(save == 'Y' || save == 'y'){
                                    
                                    //Get file name from user
                                    do{
                                        System.out.print("\nInput name of maze file to create: ");
                                        fileName = keyboard.next();
                                        
                                        //Check if valid type
                                        if(!fileName.contains(".txt"))
                                            System.out.println("File name must contain .txt as extension!");
                                        
                                        //Check if file already exists
                                        else if(new File(fileName).exists())
                                            System.out.println("A file with this name already exists!");
                                    } while(!fileName.contains(".txt") || new File(fileName).exists());

                                    //Export the new maze to a file
                                    createMaze.export(fileName);

                                    //Display name of new file
                                    System.out.println("Your new maze is located in " + fileName);
                                }
                                else
                                    System.out.println("Maze not saved!");
                            }
                            break;
                         
//CASE 4 GENERATE MAZE*********
                        case 4: //Generate a maze
                            //Create new Maze to hold values from user before file write
                            Maze newMaze = new Maze();

                            //Get maze width from user
                            System.out.print("\nInput new maze width: ");
                            width = keyboard.nextInt();

                            //Validate input
                            while(width < 3){
                                System.out.println("\nWidth must be greater than 3!");
                                System.out.print("Try again: ");
                                width = keyboard.nextInt();      
                            }

                            //Get maze height from user
                            System.out.print("Input new maze height: ");
                            height = keyboard.nextInt();

                            //Validate input
                            while(height < 3){
                                System.out.println("\nHeight must be greater than 3!");
                                System.out.print("Try again: ");
                                width = keyboard.nextInt();      
                            }
                            
                            //Check area of maze, if large, display disclaimer
                            if(width * height > 9999)
                                System.out.println("\nDue to size, maze may take a while to generate!");
                            
                            //Display loading header
                            System.out.println("\n____________________PROGRESS____________________");
                            
                            //Int that counts number of attempts to create a maze
                            //System allows 2000 attempts
                            int attempts = 0;
                            
                            //Tests if new maze can be solved
                            boolean solvable = true;
                            testCreature = new Creature();
                            do{
                                try{
                                    //Create new maze
                                    newMaze.create(height, width);
                            
                                    testCreature.enterMaze(newMaze);

                                     //Determine direction to go based on entrance location
                                    if(testCreature.getPositionH() == 0)
                                        solvable = goSouth(newMaze, testCreature);
                                    else if(testCreature.getPositionW() == 0)
                                        solvable = goEast(newMaze, testCreature);
                                    else
                                        throw new Exception("Error: Invalid Starting Position!");
                                }
                                
                                catch(Exception ex){
                                    solvable = false;
                                    attempts++;
                                    if (attempts % 50 == 0)
                                        System.out.print(">");
                                }
                                catch(StackOverflowError ex){
                                    solvable = false;
                                    attempts++;
                                    if (attempts % 50 == 0)
                                        System.out.print(">");
                                }
                                
                            }while(solvable == false && attempts < 2000);
                            
                            //Checks if attempt limit was reached
                            if(attempts > 1999){
                                System.out.println("Failure!");
                                System.out.println("\nError: Unable to Generate Maze Due to Size!");
                            }
                            
                            //If maze is successfully created
                            else {
                                while(attempts < 2000){
                                    System.out.print(">");
                                    attempts += 50;
                                }
                                
                                newMaze.clean();
                                System.out.println("Success!");
                                
                                //Check if user wants to see the generated maze
                                System.out.print("\nMaze generated! Would you like to view the maze? <y/n>: ");
                                save = keyboard.next().charAt(0);
                                if(save == 'Y' || save == 'y'){
                                    
                                    //Display the generated maze
                                    System.out.print("\nYour generated maze:");
                                    newMaze.display();
                                }
                                
                                //Check if user wants to save the maze
                                System.out.print("\nWould you like to save this maze? <y/n>: ");
                                save = keyboard.next().charAt(0);;
                                
                                if(save == 'Y' || save == 'y'){

                                    //Export the new maze to a file
                                    String newFile = newMaze.export();

                                    //Display name of new file
                                    System.out.println("Your new maze is located in " + newFile);
                                }
                                else
                                    System.out.println("Maze not saved!");
                            }
                            
                            break;
                            
//CASE 5 DELETE MAZE***********
                        case 5: //Delete a maze
                            showFiles();
                            System.out.print("\nInput name of maze file to delete: ");
                            fileName = keyboard.next();
                            
                            if(new File(fileName).delete() == true)
                                System.out.println("Maze deleted!");
                            else
                                System.out.println("Maze doesn't exist!");
                            break;
                            
//CASE 0 EXIT PROGRAM**********
                        case 0: //Exit Program
                            System.out.println("\nNever stop adventuring! Come again soon!\n");
                            System.exit(0);
                            break;
                            
                        default: //If input is out of range
                            throw new Exception("Error: Input out of range!");
                            
                    }

                }while(choice >= 0 && choice < 11);

            }

            //Catches if file is not found
            catch(FileNotFoundException ex){
                success = false;
                System.out.println("Error: File Not Found!");
            }
            //Catches if input is invalid type
            catch(InputMismatchException ex){
                keyboard.nextLine();
                success = false;
                System.out.println("Error: Invalid Data!");
                System.out.println("Try Again...\n");
            }
            //Catches Overflow Error
            catch(StackOverflowError ex){
                success = false;
                System.out.println("Error: Out of Memory! Maze too Large!");
            }
            //Catches File Read-In Error
            catch(Exception ex){
                success = false;
                System.out.println(ex);
            }
        }while(success == false);
    }  
    
    /* void showMenu: displays menu to the screen
     * Input: None
     * Output: None
     * Returns: None 
     */
    public static final void showMenu(){
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Solve a Maze");
        System.out.println("2. See a Maze");
        System.out.println("3. Create a Maze");
        System.out.println("4. Generate a Maze");
        System.out.println("5. Delete a Maze");
        System.out.println("0. Exit");
    }
    
    /* void showFiles: displays current files in the working directory
     * Input: None
     * Output: current memory files in the working directory
     * Returns: None
     */
    static public void showFiles(){
        //Get the current working directory
        String directory;
        File file = new File(".");
        directory = file.getAbsolutePath();
        file = new File(directory);
        File[] listOfFiles = file.listFiles();

        System.out.println("\nAvailable Mazes:");
        
        //Display all of the current memories in the working directory
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].toString().contains(".txt")) {
                System.out.println(listOfFiles[i].getName());
            } 
        }
    }
    
    /* boolean goNorth: attempts to move creature north, reports if successful
     * Input: Maze maze, Creature creature
     * Output: None
     * Returns: boolean success 
     */
    public static boolean goNorth(Maze maze, Creature creature){
                
        boolean success;
        
        //Checks if next location is a wall or a path already travelled
        if(!maze.isWall(creature.getPositionH() - 1, creature.getPositionW()) && !maze.isVisited(creature.getPositionH() - 1, creature.getPositionW())){
            
            //Move creature north one, and update maze map
            maze.mark(creature, maze.path);
            creature.moveNorth();
            maze.mark(creature, maze.current);
            
            //Checks if maze is solved
            if(maze.isExit(creature)){
               success = true;
            }
            
            //If maze still is not solved
            else{
                
                //Try going North again
                success = goNorth(maze, creature);
                if(!success){
                    
                    //Try going West
                    success = goWest(maze, creature);
                    if(!success){
                        
                        //Try going East
                        success = goEast(maze, creature);
                        
                        //If there is nowhere to go, backtrack one, and update maze map
                        if(!success){
                            maze.mark(creature,maze.visited);
                            creature.moveSouth();
                            maze.mark(creature, maze.current);
                        }
                    }
                }
            }
        }
        else {
            success = false;
        }
        
        return success;        
    }
    
    /* boolean goWest: attempts to move creature west, reports if successful
     * Input: Maze maze, Creature creature
     * Output: None
     * Returns: boolean success 
     */
    public static boolean goWest(Maze maze, Creature creature){
        
        boolean success;
        
        //Checks if next location is a wall or a path already travelled
        if(!maze.isWall(creature.getPositionH(), creature.getPositionW() - 1) && !maze.isVisited(creature.getPositionH(), creature.getPositionW() - 1)){
            
            //Move creature west one, and update maze map
            maze.mark(creature,maze.path);
            creature.moveWest();
            maze.mark(creature,maze.current);
            
            //Checks if maze is solved
            if(maze.isExit(creature)){
               success = true;
            }
            
            //If maze still is not solved
            else{
                
                //Try going west again
                success = goWest(maze, creature);
                if(!success){
                    
                    //Try going south
                    success = goSouth(maze, creature);
                    if(!success){
                        
                        //Try goind north
                        success = goNorth(maze, creature);
                        
                        //If there is nowhere to go, backtrack one, and update maze map
                        if(!success){
                            maze.mark(creature,maze.visited);
                            creature.moveEast();
                            maze.mark(creature,maze.current);
                        }
                    }
                }
            }
        }
        else {
            success = false;
        }
        
        return success;
    }
    
    /* boolean goSouth: attempts to move creature south, reports if successful
     * Input: Maze maze, Creature creature
     * Output: None
     * Returns: boolean success 
     */
    public static boolean goSouth(Maze maze, Creature creature){
        
        boolean success;
        
        //Checks if next location is a wall or a path already travelled
        if(!maze.isWall(creature.getPositionH() + 1, creature.getPositionW()) && !maze.isVisited(creature.getPositionH() + 1, creature.getPositionW())){
            
            //Move creature south one, and update maze map
            maze.mark(creature, maze.path);
            creature.moveSouth();
            maze.mark(creature, maze.current);
            
            //Checks if maze is solved
            if(maze.isExit(creature)){
               success = true;
            }
            
            //If maze still is not solved
            else{
                
                //Try going south again
                success = goSouth(maze, creature);
                if(!success){
                    
                    //Try going east
                    success = goEast(maze, creature);
                    if(!success){
                        
                        //Try going west
                        success = goWest(maze, creature);
                        
                        //If there is nowhere to go, backtrack one, and update maze map
                        if(!success){
                            maze.mark(creature, maze.visited);
                            creature.moveNorth();
                            maze.mark(creature, maze.current);
                        }
                    }
                }
            }
        }
        else {
            success = false;
        }
        
        return success;
    }
    
    /* boolean goEast: attempts to move creature east, reports if successful
     * Input: Maze maze, Creature creature
     * Output: None
     * Returns: boolean success 
     */
    public static boolean goEast(Maze maze, Creature creature){
        
        boolean success;
        
        //Checks if next location is a wall or a path already travelled
        if(!maze.isWall(creature.getPositionH(), creature.getPositionW() + 1) && !maze.isVisited(creature.getPositionH(), creature.getPositionW() + 1)){
            
            //Move creature east one, and update maze map
            maze.mark(creature, maze.path);
            creature.moveEast();
            maze.mark(creature, maze.current);
            
            //Checks if maze is solved
            if(maze.isExit(creature)){
               success = true;
            }
            
            //If maze still is not solved
            else{
                
                //Try going east again
                success = goEast(maze, creature);
                if(!success){
                    
                    //Try going north
                    success = goNorth(maze, creature);
                    if(!success){
                        
                        //Try going south
                        success = goSouth(maze, creature);
                        
                        //If there is nowhere to go, backtrack one, and update maze map
                        if(!success){
                            maze.mark(creature, maze.visited);
                            creature.moveWest();
                            maze.mark(creature, maze.current);
                        }
                    }
                }
            }
        }
        else {
            success = false;
        }
        
        return success;
    }
    
    /* void startLabyrinth: attempts to move creature from central starting position
     * Input: Maze maze, Creature creature
     * Output: None
     * Returns: None 
     */
    public static void startLabyrinth(Maze maze, Creature creature){
        boolean success;
        
        //Try going north
        success = goNorth(maze, creature);
        if(!success){
            //Try going west
                success = goWest(maze, creature);
            if(!success){

                //Try going east
                success = goEast(maze, creature);
                if(!success){

                    //Try going south
                    success = goSouth(maze, creature);
                }
            }
        }
    }
}
    
    
