package Main;


import java.awt.*;

/**
 * Created by AlexVR on 1/24/2020.
 * Modified by Kenneth Aponte on 5/25/2021.
 */

public class Launch {

    public static void main(String[] args) {
        GameSetUp game = new GameSetUp("Sudoku Solver", Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        game.start();
    }
}
