import acm.graphics.GRect;

import java.awt.*;

public class Brick extends GRect {


    public static final int WIDTH = 44;
    public static final int HEIGHT = 20;

    public int brickLife;

    public Brick(double x, double y, Color color, int row, int brickLives){
        super(x, y, WIDTH, HEIGHT);
        setColor(color);
        setFilled(true);
        this.brickLife = brickLives;

    }

    public void loseLife(){
        brickLife -= 1;

    }

    public int getLives(){
        return brickLife;
    }


}
