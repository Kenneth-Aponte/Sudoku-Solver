package Game.GameStates;

import Main.Handler;
import Sudoku.entities.Block;
import Extras.Solver;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
/*
 * 
 * Created by Kenneth R. Aponte
 * 
 */

public class InputState extends State {
	
	public ArrayList<Block> inputBlocks = new ArrayList<>();
	public Block[][][] solvedBlocks= new Block[3][3][9];//a 3d array that stores 9 (every grid) 2d arrays (3 by 3, every pos)
	//right and left exceptions are for the movement of the selector in the grid (fully initialized in the constructor)
	public ArrayList<Integer> rightExceptions = new ArrayList<>();
	public ArrayList<Integer> leftExceptions = new ArrayList<>();
	public int prevScreenH,prevScreenW;
	
    public InputState(Handler handler) {
        super(handler);
        addBlocksToGrid();
        prevScreenH = handler.getHeight();
        prevScreenW = handler.getHeight();
        for(int i = 8; i <= 80;i+=9 ) {
        	rightExceptions.add(i);
        }
        for(int i = 0;i <= 72;i+=9) {
        	leftExceptions.add(i);
        }
    }

    
    @Override
    public void tick() {
    	Block selectedBlock = inputBlocks.get(0);//since, by a precondition there is ALWAYS a selected block, one can assume its the first one in the list at first and then change it to the corresponding one in the loop.
    	for(Block currBlock: inputBlocks) {
    		if(currBlock.isSelected) {
    			selectedBlock = currBlock;
    			break;//only one, no reason for the loop to keep looking
    		}
    	}
    	
    	//this ensures that whenever the screen is resized the proportions of the blocks and grid to the screen stay the same
    	if(handler.getHeight() != prevScreenH || handler.getWidth() != prevScreenW) {
    		ensureGridBlockProportions();
    		prevScreenH = handler.getHeight();
    		prevScreenW = handler.getWidth();
    	}
    	
    	
    	
    	
    	//adjusting the value on the block (redundant though it's the most efficient way without using buttons or a text box of some sort)
    	int valueToAdd = -1;
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_1) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD1)) {
    		valueToAdd=1;
    	}
    	else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_2) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD2)) {
    		valueToAdd=2;
    	}
    	else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_3) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD3)) {
    		valueToAdd=3;
    	}
    	else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_4) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD4)) {
    		valueToAdd=4;
    	}
    	else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_5) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD5)) {
    		valueToAdd=5;
    	}
    	else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_6) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD6)) {
    		valueToAdd=6;
    	}
    	else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_7) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD7)) {
    		valueToAdd=7;
    	}
    	else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_8) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD8)) {
    		valueToAdd=8;
    	}
    	else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_9) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD9)) {
    		valueToAdd=9;
    	}
    	else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_0) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_NUMPAD0)) {
    		valueToAdd=0;
    	}
    	
    	
    	//adds the value, if input was received
    	if(valueToAdd >= 0 && selectedBlock != null) {
    		selectedBlock.addInput(valueToAdd);
    	}
    	
    	//movement
    	int indexOfSelectedBlock = inputBlocks.indexOf(selectedBlock);
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)) {
    		if(!leftExceptions.contains(indexOfSelectedBlock)) {
    			selectedBlock.isSelected = false;
    			selectedBlock = inputBlocks.get(--indexOfSelectedBlock);
    			selectedBlock.isSelected = true;
    		}
    	}
    	else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)) {
    		if(!rightExceptions.contains(indexOfSelectedBlock)) {
    			selectedBlock.isSelected = false;
    			selectedBlock = inputBlocks.get(++indexOfSelectedBlock);
    			selectedBlock.isSelected = true;
    		}
    	}
    	else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)) {
        	if(!(indexOfSelectedBlock >= 0 && indexOfSelectedBlock <= 8)) {
        		selectedBlock.isSelected = false;
        		indexOfSelectedBlock-=9;
    			selectedBlock = inputBlocks.get(indexOfSelectedBlock);
    			selectedBlock.isSelected = true;
        	}
    	}
    	else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)) {
    		if(!(indexOfSelectedBlock >= 72 && indexOfSelectedBlock <= 80)) {
        		selectedBlock.isSelected = false;
        		indexOfSelectedBlock+=9;
    			selectedBlock = inputBlocks.get(indexOfSelectedBlock);
    			selectedBlock.isSelected = true;
        	}
    	}
    	
    	
    	//moves to solving state
    	if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
    		
    		solvedBlocks = Solver.solve(inputBlocks);
    	}
    	
    }

    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);//bg color
        g.fillRect(0,0,handler.getWidth(),handler.getHeight());//background
        g.setColor(Color.black);
        
        //Main Grid------------------------------------------------------------------
        //horizontal
        int centerW = handler.getWidth()/2, centerH = handler.getHeight()/2;
        g.drawLine(centerW-handler.getHeight()/12-handler.getHeight()/6, centerH-handler.getHeight()/12, centerW+handler.getHeight()/12+handler.getHeight()/6, centerH-handler.getHeight()/12);
        g.drawLine(centerW-handler.getHeight()/12-handler.getHeight()/6, centerH+handler.getHeight()/12, centerW+handler.getHeight()/12+handler.getHeight()/6, centerH+handler.getHeight()/12);
        
        g.setColor(Color.lightGray);
        int yValue = centerH - (handler.getHeight()/12 + handler.getHeight()/6);
        for(int i = 1; i <= 8;i++) {
        	yValue+= handler.getHeight()/18;
        	if(!(i % 3 == 0)) {
        		g.drawLine(centerW-handler.getHeight()/12-handler.getHeight()/6, yValue, centerW+handler.getHeight()/12+handler.getHeight()/6, yValue);
        	}
        }
        
        //vertical
        g.setColor(Color.black);
        g.drawLine(centerW-handler.getHeight()/12, centerH-handler.getHeight()/12-handler.getHeight()/6, centerW-handler.getHeight()/12, centerH+handler.getHeight()/12+handler.getHeight()/6);
        g.drawLine(centerW+handler.getHeight()/12, centerH-handler.getHeight()/12-handler.getHeight()/6, centerW+handler.getHeight()/12, centerH+handler.getHeight()/12+handler.getHeight()/6);
        
        g.setColor(Color.lightGray);
        int xValue = centerW - (handler.getHeight()/12+ handler.getHeight()/6);
        for(int i = 1; i <= 8;i++) {
        	xValue+= handler.getHeight()/18;
        	if(!(i % 3 == 0)) {
        		g.drawLine(xValue, centerH-handler.getHeight()/12-handler.getHeight()/6, xValue, centerH+handler.getHeight()/12+handler.getHeight()/6);
        	}
        }
        //----------------------------------------------------------------------
        
        
        //blocks
        for(Block curr: inputBlocks) {
        	curr.render(g);
        }
    }
    
    public void addBlocksToGrid() {
    	int xValue = handler.getWidth()/2 - handler.getHeight()/12 - handler.getHeight()/6;
    	int yValue = handler.getHeight()/2 - handler.getHeight()/12 - handler.getHeight()/6;
    	int sizeOfEveryBlock = handler.getHeight()/18;
    	for(int i = 1;i <= 81;i++) {//starts at 1 as its easier to keep track of (the math for the if inside)
    		inputBlocks.add(new Block(xValue,yValue,handler));
    		if(i == 1) {
    			inputBlocks.get(0).isSelected = true;
    		}
    		xValue+=sizeOfEveryBlock;
    		if(i % 9 == 0) {
    			yValue+=sizeOfEveryBlock;
    			xValue = handler.getWidth()/2 - handler.getHeight()/12 - handler.getHeight()/6;
    		}
    	}
    }
    
    public void ensureGridBlockProportions() {
    	int xValue = handler.getWidth()/2 - handler.getHeight()/12 - handler.getHeight()/6;
    	int yValue = handler.getHeight()/2 - handler.getHeight()/12 - handler.getHeight()/6;
    	int sizeOfEveryBlock = handler.getHeight()/18;
    	int indexInArrayList = 1;
    	for(Block curr: inputBlocks) {
    		curr.width = curr.height = sizeOfEveryBlock;
    		
    		curr.x = xValue;
    		curr.y = yValue;
    		xValue+=sizeOfEveryBlock;
    		if(indexInArrayList % 9 == 0) {
    			yValue+=sizeOfEveryBlock;
    			xValue = handler.getWidth()/2 - handler.getHeight()/12 - handler.getHeight()/6;
    		}
    		indexInArrayList++;
    	}
    }
    
    @Override
    public void refresh() {
    }
}
