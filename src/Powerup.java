import acm.graphics.GRect;
import acm.util.RandomGenerator;
import acm.graphics.GLabel;
import acm.program.GraphicsProgram;
import java.awt.*;

public class Powerup extends GRect {


    public static final int WIDTH = 44;
    public static final int HEIGHT = 20;
    public static boolean starPower = false;
    public int value;
    public int clock = 0;


    public Powerup(double x, double y){
        super(x, y, WIDTH, HEIGHT);
    }

    public void powerUpRun(){
            starPower = true;
            pause(10000);
            starPower = false;
    }


}
