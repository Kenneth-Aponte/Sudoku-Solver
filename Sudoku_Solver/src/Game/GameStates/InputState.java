package Game.GameStates;


import Display.UI.UIManager;
import Main.Handler;
import java.awt.*;


public class InputState extends State {

    private UIManager uiManager;

    public InputState(Handler handler) {
        super(handler);
        refresh();
    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,0,handler.getWidth(),handler.getHeight());
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
        
        uiManager.Render(g);

    }


    @Override
    public void refresh() {
    	uiManager = new UIManager(handler);
    }
}
