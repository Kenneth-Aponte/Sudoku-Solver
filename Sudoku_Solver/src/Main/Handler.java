package Main;

import Display.DisplayScreen;
import Game.GameStates.*;
import Input.KeyManager;
import Input.MouseManager;

import javax.sound.sampled.Clip;
import java.util.ArrayList;


/**
 * Created by AlexVR on 1/24/2020.
 * Modified by Kenneth Aponte on 5/25/2021.
 */

public class Handler {

    private GameSetUp game;
    private DisplayScreen screen;
    private boolean fullScreen = false;
    private State lastState;
    public static boolean DEBUG = false;

    public Handler(GameSetUp game){
        this.game = game;
    }

    public int getWidth(){
        return getDisplayScreen().getCanvas().getWidth();
    }

    public int getHeight(){
        return getDisplayScreen().getCanvas().getHeight();
    }

    public GameSetUp getGameProperties() {
        return game;
    }

    public void setGameProperties(GameSetUp game) {
        this.game = game;
    }

    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }

    public MouseManager getMouseManager(){
        return game.getMouseManager();
    }

    public DisplayScreen getDisplayScreen(){return screen;}

    public void setDisplayScreen(DisplayScreen screen){this.screen=screen;}

    public InputState getMenuState (){
        return (InputState) getGameProperties().menuState;
    }

    public void changeState(State state){
        State.setState(state);
    }

    public State getState(){
        return State.getState();
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public State getLastState() {
        return lastState;
    }

    public void setLastState(State lastState) {
        this.lastState = lastState;
    }
}
