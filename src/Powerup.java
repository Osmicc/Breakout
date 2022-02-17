import acm.graphics.GRect;
import acm.util.RandomGenerator;

import java.awt.*;

public class Powerup extends GRect {

    // the parameters for the size of the powerup

    // makes it impossible to die
    public static boolean starPower = false;
    // what powerup is active
    public static int value;
    // used to speed up the gameplay
    public static int gameLoopSpeed = 5;

    public static int gameLoopCheck = 0;

    // instantiates the powerup brick
    public Powerup(double x, double y, int WIDTH, int HEIGHT) {
        super(x, y, WIDTH, HEIGHT);
    }

    public void powerUpRun() {
        if (Breakout.type == 1) {
            // Turns the powerup on and changes the speed of the ball
            starPower = true;
            gameLoopSpeed = 2;
            gameLoopCheck = 10000;
        }
    }

}