import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

// P12 Assignment
// Author: Nicholas Cunningham
// Date:   Jul 28, 2018
// Class:  CS163
// Email:  nic1571@cs.colostate.edu

public class Maze implements IMaze{

	public static void main(String[] args) {
		Maze maze = new Maze();
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("What is the maze filename? ");
		String filename = keyboard.next();
		
		char[][] file = maze.readFile(filename);
		
		System.out.println(maze.printMaze(file));
		System.out.println(Arrays.toString(maze.findStart(file)));
		System.out.println(maze.findPath(file, maze.findStart(file)));
		System.out.println();
		System.out.println(maze.printMaze(file));

		keyboard.close();
	}

	/* Precondition - filename set to file containing map of the maze.
	 * Postcondition - two dimensional char array holding a map of the maze
	 * Postcondition - returns null if file not found
	 */
	public char[][] readFile(String filename) {
		Scanner file;
		try {
			file = new Scanner(new File(filename));
			
			int mazeRows = file.nextInt();
			int mazeCols = file.nextInt();
			
			file.nextLine();
			
			char[][] maze = new char[mazeRows][mazeCols];
						
			for (int i = 0; i < mazeRows; i++) {
				String tempLine = file.nextLine();
				for (int j = 0; j < mazeCols; j++) {
					maze[i][j] = tempLine.charAt(j);
				}
			}
			
			file.close();
			return maze;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* Precondition - maze array initialized to a valid maze
	 * Postcondition - array containing row, column of location of S 
	 * 		returned.  Ex. if S is in maze[1][2]. the return 
	 * 		array is {1, 2}
	 * Postcondition - returns null if no S found
	 */
	public int[] findStart(char[][] maze) {
		int[] start = new int[2];
		
		for (int i = 0; i < maze.length - 1; i++) {
			for (int j = 0; j < maze[i].length - 1; j++) {
				if (maze[i][j] == 'S') {
					start[0] = i;
					start[1] = j;
				}
			}
		}
		return start;
	}

	/* Precondition - maze array initialize to a valid maze
	 * Precondition - StartPosition contains row, column of location of S 
	 * 		Ex. if S is in maze[1][2], the startPosition 
	 * 		 array is {1, 2}
	 * Postcondition - returns a String composed of the appropriate
	 *     characters from  'U', 'R', 'D', 'L', and the final 'G', 
	 *     indicating the solution path.
	 * Postcondition - successful path marked with '.' characters in maze
	 * 		 array from 'S' to the final 'G', indicating the 
	 * 		 solution path.
	 */
	public String findPath(char[][] maze, int[] startPosition) {
		return recPath(maze, startPosition[0], startPosition[1]);
	}
	
	
	/*
     * Precondition - maze array initialized to a valid maze
     * Postcondition - returns the path from the location r,c to the goal
     * Postcondition - '.' set from location r,c in the maze to the goal
     * Requirement - Implemented as a recursive method that finds a path
     *               from position (row,col) to the goal position,
     *               marked by 'G'
     */
	private String recPath(char[][] maze, int r, int c) {
		if (r < 0 || r > maze.length - 1) return "";
		if (c < 0 || c > maze[0].length - 1) return "";
		if (maze[r][c] == '#' || maze[r][c] == '.') return "";
		if (maze[r][c] == 'G') return "G";
		
		maze[r][c] = '.';
		
		String path = recPath(maze, r - 1, c);
		if (!path.isEmpty())
			return 'U' + path; //Up
		
		path = recPath(maze, r, c + 1);
		if (!path.isEmpty())
			return 'R' + path; //Right
		
		path = recPath(maze, r + 1, c);
		if (!path.isEmpty())
			return 'D' + path; //Down
		
		path = recPath(maze, r, c - 1);
		if (!path.isEmpty())
			return 'L' + path; //Left
		
		maze[r][c] = ' ';
		
		return "";
	}
    

	/*
	 * Postcondition - returns a String representation of the map character array
	 * 	(complete with end of line characters).
	 */
	public String printMaze(char[][] maze) {
		String mazePrint = "";
		
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				mazePrint += maze[i][j];
			}
			mazePrint += "\n";
		}
		
		return mazePrint;
	}
	
}
