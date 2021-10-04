package Extras;

import java.util.ArrayList;
import java.util.HashMap;

import Sudoku.entities.*;

public class Solver {
	static Block[][][] numBlocks;
	public static boolean solved = false, done = false;//once done is true, no more solutions can be found with algs and will have to rely on trying every possibility.
	public static ArrayList<Block> numBlocks1;
	
	
	public static Block[][][] solve(ArrayList<Block> blocksToSolve) {
		
		/*
		 * Essentially what we're going to do is, look at all the possibilities for a given sudoku square(there are 81 of them), after that
		 * we will solve for squares that contain only one pos solution and numbers that have a frequency of one per grid.
		 * More algorithms can then be added to solve the rest...
		 *  
		 */
		numBlocks = getInputBlocksAs3DArray(blocksToSolve);
//		printAllValuesInGrid(numBlocks);
		while(!done) {
			adjustAllPossibleSolutions();
			if(findNumbersAloneInGrid() || findNumbersWithOnlyOnePosSol()) {
				continue;
			}
			done = true;
		}

		solved = true;
		return numBlocks;
	}
	
	//adjusts the solutions for every square
	public static void adjustAllPossibleSolutions() {
		for(int g = 0; g < 9;g++) {
			for(int y = 0; y < 3; y++) {
				for(int x = 0; x < 3; x++) {
					findPosSolutionsFor(numBlocks[x][y][g], g,x,y);
				}
			}
		}
		
	}
	
	
	//adjusts the solutions for a single square
	public static void findPosSolutionsFor(Block targetBlock, int g, int targetX, int targetY) {
		if(targetBlock.value != 0) {
			targetBlock.posSolutions.clear();
		}
		
		//this checks for all the nums in the same grid
		removeFromSameGrid(targetBlock, g, targetX, targetY);
		
		//this checks for all the numbers horizontally and removes the ones that it encounters
		removeHorizontally(targetBlock, g, targetX, targetY);
		
		//this checks for all the numbers vertically and removes the ones that it encounters
		removeVertically(targetBlock, g, targetX, targetY);
		
		//uncomment for debugging
//		for(Integer num: targetBlock.posSolutions) {
//			System.out.println(num);
//		}
	}
	
	//removes numbers that are in the same horizontal line for a given block as these can't be a solution
	public static void removeHorizontally(Block targetBlock, int g, int targetX, int targetY) {
		int x = 0;
		int y = targetY;
		int currG = g;
		
		if(g % 3 == 0) {//left column of grids
			//already at the beginning, nothing to do
			
		}else if((g+1) % 3 == 0) {//right column of grids
			currG-=2;
			
		}else {//center column of grids
			currG--;
		
		}
		
		
		for(int i = 1 ;i <= 9;i++) {
			if(numBlocks[x][y][currG].value != 0) {
				targetBlock.posSolutions.remove((Integer)numBlocks[x][y][currG].value);
			}
			x++;
			
			if(i % 3 == 0) {
				currG++;
				x = 0;
			}		
		}
		
	}
	
	
	//removes numbers that are in the same vertical line for a given block as these can't be a solution
	public static void removeVertically(Block targetBlock, int g, int targetX, int targetY) {
		int x = targetX;
		int y = 0;
		int currG = g;
		
		if(g < 3) {//upper column of grids
			//already at the beginning, nothing to do
			
		}else if(g > 5) {//bottom column of grids
			currG-=6;
			
		}else {//center column of grids
			currG-=3;
		
		}
		
		
		for(int i = 1 ;i <= 9;i++) {
			if(numBlocks[x][y][currG].value != 0) {
				targetBlock.posSolutions.remove((Integer)numBlocks[x][y][currG].value);
			}
			y++;
			
			if(i % 3 == 0 ) {
				currG+=3;
				y = 0;
			}		
		}
		
	}
	
	
	//removes numbers that are in the same grid for a given block as these can't be a solution
	public static void removeFromSameGrid(Block targetBlock, int g, int targetX, int targetY) {
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				if(numBlocks[x][y][g].value != 0) {
					targetBlock.posSolutions.remove((Integer)numBlocks[x][y][g].value);
				}
			}
		}
	}
	
	
	//finds blocks which only have a single pos solution-- that is the solution for the given block
	public static boolean findNumbersWithOnlyOnePosSol() {
		boolean foundOne = false;
		for(Block[][] grid: numBlocks) {
			for(Block[] blockY: grid) {
				for(Block blockX:blockY) {
					if(blockX.posSolutions.size() == 1) {
						blockX.value = blockX.posSolutions.get(0);
						foundOne = true;
					}
				}
			}
		}
		return foundOne;
	}
	
	/*
	 * travels through every grid and checks if a number only has a frequency of 1,
	 * if that is the case, then it searches for the block that contains that number and sets the square to that value.
	 */
	public static boolean findNumbersAloneInGrid() {
		boolean foundOne = false;
		for(int g = 0; g < 9; g++) {
			HashMap<Integer, Integer> nums = new HashMap<>();
			for(int i = 1;i<=9;i++) {
				nums.put(i, 0);
			}
			for(int y = 0; y< 3; y++) {
				for(int x = 0; x < 3; x++) {
					for(Integer posSol: numBlocks[x][y][g].posSolutions) {
						nums.put(posSol, nums.get(posSol)+1);
					}
				}
			}
			for(int key = 1; key <= 9 ; key++) {
//				System.out.println(nums.get(key));
				if(nums.get(key) == 1) {//only found once in that particular grid
					for(int y = 0; y < 3; y++) {
						for(int x = 0; x < 3; x++) {
							if(numBlocks[x][y][g].posSolutions.contains(key)) {
								numBlocks[x][y][g].value = key;
								foundOne = true;							}
						}
					}
				}
			}
		}
		return foundOne;
	}
	
	
	//changes from an ArrayList to a 3DArray as its easier to work with for frequencies and other things
	public static Block[][][] getInputBlocksAs3DArray(ArrayList<Block> inputBlocks) {
		Block[][][] numBlocks = new Block[3][3][9];
		int x = 0;
		int y = 0;
		int g = 0;
		int i = 1;
		
		for(Block cur: inputBlocks) {
			numBlocks[x][y][g] = cur;
//			 System.out.println("i: " + i + " ,x: " + (x+1)+ " ,y: " + (y+1) + " ,g: " + (g+1));
			x++;
			if(i % 3 == 0) {
				x=0;
				g++;
				if(i % 9 == 0) {
					if(y == 2) {
						y = 0;
					}
					else {
						y++;
						g-=3;
					}			
				}
			}
			i++;
		}
		return numBlocks;
	}
	
	
	//prints all the values in a given grid debugging purposes
	public static void printAllValuesInGrid(Block[][][] blocks){
		for(int g = 0; g < 9;g++) {
			System.out.println("Showing Grid " + (g+1));
			for(int y = 0; y < 3; y++) {
				for(int x = 0; x < 3;x++) {
					System.out.print(blocks[x][y][g].value);
				}
				System.out.println();
			}			
		}
	}


}
