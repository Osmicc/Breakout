import acm.graphics.GCanvas;
import acm.graphics.GOval;

public class Ball extends GOval {
    private double deltaX = 1;
    private double deltaY = -1;
    private GCanvas screen;
    public boolean lost = false;

    public Ball(double x, double y, double size, GCanvas screen) {
        super(x, y, size, size);
        setFilled(true);
        this.screen = screen;
    }

    public void handleMove() {
        move(deltaX, deltaY);

        //check if the ball hits the top
        if (getY() <= 0) {
            deltaY *= -1;
        }

        //check if the ball hits the bottom
        if (Powerup.starPower != true && getY() >= screen.getHeight() - getHeight()) {
            lost = true;
        }else if (getY() >= screen.getHeight() - getHeight() && Powerup.starPower == true){
            bounce();
        }


        //check if the ball hits the left
        if (getX() <= 0) {
            deltaX *= -1;
        }

        //check if the ball hits the right
        if (getX() >= screen.getWidth() - getWidth()) {
            deltaX *= -1;
        }
    }

    public void bounce(){
        deltaY *= -1;
    }
    public void bounceLeft(){
        deltaY *= -1;
        deltaX = -Math.abs(deltaX);
    }
    public void bounceRight(){
        deltaY *= -1;
        deltaX = Math.abs(deltaX);
    }
}