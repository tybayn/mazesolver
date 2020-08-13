# Maze Solver and Generator

This is a terminal based maze solver and generator.

An extension of a University project, this Java project will both recursively solve and recursively generate ASCII mazes. The basic requirement was to solve mazes, but I went a step further to implement a recursive division algorithm which are then tested by the solving portion of the project to ensure they can be solved.

This program works best either in NetBeans IDE or on a Linux Terminal since there is colored output. It may not appear as intended on Windows.

## Structure
To ensure the software works and compiles correctly, be sure to maintain the following file structure for the project:

```bash
src
|  manifest.txt
|
└──mazesolver
   |  Creature.java
   |  Maze.java
   |  MazeSolver.java
```

## Compilation
To develop this I used openJDK command line compiler. To compile the project to a stand-alone (non-executable) .jar file, make sure there is a 'manifest.txt' file in the src/ directory that contains:

```bash
Main-Class: mazesolver.MazeSolver
```

Once that file is in place, I recommend running the following from the same src/ directory:

```bash
javac mazesolver/*.java
jar cmvf manifest.txt MazeSolver.jar mazesolver/*.class
```

## Running the Software
Once the software has been compiled down to a .jar file, you can run it from the command line by typing:

```bash
java -jar MazeSolver.jar
```

## Maze Files
The maze files that are compatible with this software are laid out like the following:

```bash
20 10
1 0
6 19
xxxxxxxxxxxxxxxxxxxx
              x xx x
xx xxxxxxxxxxxx    x
x             xxx xx
xxxx xx xxx xxx x  x
x    xx xxx xxx xx x
xxx xx  x     x x   
xxx xxxxx xxxxx x xx
x    x            xx
xxxxxxxxxxxxxxxxxxxx
```
Where the first pair of numbers is the width and height of the maze (respectively), the second set is the entrance position, and the third set is the exit. 

After the numbers, the actual maze is drawn out using 'spaces' and 'X', where 'X' represents walls and 'spaces' represent paths.

These maze files should be stored as .txt files and in the same directory as the .jar file.

## Final Notes
This repo is posted without a license.    
All sources were created by Ty Bayn.  
Copyright 2020 Ty Bayn  
Date Created: Sept. 19, 2016  
Last Edit: May 24, 2017