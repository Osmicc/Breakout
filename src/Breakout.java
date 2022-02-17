import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import java.awt.*;
import java.awt.event.MouseEvent;
import svu.csc213.Dialog;
import acm.util.RandomGenerator;



public class Breakout extends GraphicsProgram {


    private Ball ball;
    private Paddle paddle;
    private int lives = 3;
    private int point;
    GLabel pointLabel;
    private GLabel livesLabel;
    private final int[] hitLives = {5, 5, 4, 4, 3, 3, 2, 2, 1, 1};
    public static int type;

    private final Color[] rowColors = {Color.cyan, Color.cyan, Color.BLUE, Color.BLUE, Color.GREEN, Color.GREEN, Color.yellow, Color.yellow, Color.red, Color.red};



    @Override
    public void init() {
        pointLabel = new GLabel("Points:" + (point));
        add(pointLabel, 0, 15);

        livesLabel = new GLabel("Lives:" + " " + String.valueOf(lives));
        add(livesLabel, 75, 15);

        ball = new Ball(getWidth() / 2, 350, 10, this.getGCanvas());
        add(ball);

        paddle = new Paddle(230, 430, 50, 10);
        add(paddle);

        int numBricksInRow = getWidth() / (Brick.WIDTH + 5);

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < numBricksInRow; col++) {
                double brickX = 10 + col *(Brick.WIDTH+5);
                double brickY = Brick.HEIGHT +row * (Brick.HEIGHT + 5);

                Brick brick = new Brick(brickX, brickY, rowColors[row], row, hitLives[row]);
                add(brick);
            }
        }

    }

    @Override
    public void run() {
        addMouseListeners();
        waitForClick();
        gameLoop();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //constrain the paddle to the edges of the window

        if ((me.getX() < getWidth() - paddle.getWidth() / 2) && (me.getX() > paddle.getWidth() / 2)) {
            paddle.setLocation(me.getX() - paddle.getWidth() / 2, paddle.getY());
        }
    }

    private void gameLoop(){
        while(true){
            // move the ball
            ball.handleMove();

            // check if the ball is lost
            if(ball.lost){
                handleLoss();
            }
            if(point == 450){
                Dialog.showMessage("you won");
                reset();
            }
            handleCollisions();

            pause(Powerup.gameLoopSpeed);

            // making sure the powerup doesn't last forever
            if(Powerup.gameLoopCheck > 0){
                Powerup.gameLoopCheck -= Powerup.gameLoopSpeed;
                if(Powerup.gameLoopCheck <= 0){
                    Powerup.starPower = false;
                    Powerup.gameLoopSpeed = 5;
                }
            }
        }
    }

    private void handleLoss() {
        ball.lost = false;
        reset();
    }

    private void handleCollisions(){
        // create a container
        GObject obj = null;

        // see if we hit something
        if(obj == null){
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY());
        }

        if(obj == null){
            obj = this.getElementAt(ball.getX(), ball.getY());
        }

        if(obj == null){
            obj = this.getElementAt(ball.getX() , ball.getY() + ball.getHeight());
        }

        if(obj == null){
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY() + ball.getHeight());
        }
        // find out what that something was
        if (obj != null){
            // what did I hit
            if(obj instanceof Paddle){

                if(ball.getX() < (paddle.getX() + (paddle.getWidth() * .2))){
                    // if I hit the left edge of the paddle
                    ball.bounceLeft();
                } else if(ball.getX() > paddle.getX() + (paddle.getWidth() * .8)){
                    // if I hit the right edge of the paddle
                    ball.bounceRight();
                }else {
                    // I must have hit the middle of the paddle
                    ball.bounce();
                }

            }
        }
        // react accordingly
        if (obj instanceof Brick){
            ((Brick) obj).loseLife();
            point += 1;

            pointLabel.setLabel("Points:" + (point));

            switch (((Brick) obj).getLives()){
                case 1: obj.setColor(rowColors[9]);
                    break;
                case 2: obj.setColor(rowColors[7]);
                    break;
                case 3: obj.setColor(rowColors[5]);
                    break;
                case 4: obj.setColor(rowColors[3]);
                    break;
                case 5: obj.setColor(rowColors[1]);
                    break;
            }
            ball.bounce();

            if (((Brick) obj).getLives() == 0){
                type = RandomGenerator.getInstance().nextInt(1, 1);
                int value = RandomGenerator.getInstance().nextInt(1, 20);
                if(value == 20){
                    if(type == 1){

                        //instantiates the star power powerup after the brick hiding it is broken
                        Powerup powerup = new Powerup(((Brick) obj).getX(), (((Brick) obj)).getY(), 44, 20);
                        add(powerup);
                        powerup.setFillColor(Color.black);
                        powerup.sendToFront();

                    } else if (type == 2){
                        // not finised
                        Powerup powerup = new Powerup(((Brick) obj).getX(), (((Brick) obj)).getY(), 44, 200);
                        add(powerup);
                    }

                }
                remove(obj);
            }
        }
        // if we make it to the end and obj is still ull, that means we hit nothing
        if(obj instanceof Powerup){
            ball.bounce();
            ((Powerup) obj).powerUpRun();
            remove(obj);
        }
    }

    private void reset() {
        lives -= 1;
        livesLabel.setLabel("Lives: " + String.valueOf(lives));
        if(lives == 0){
            Dialog.showMessage("You lost loser");
            removeAll();
            lives = 3;
            point = 0;
            init();
            return;
        }
        //put the ball back where you found it
        ball.setLocation(getWidth()/2, 350);
        //put the paddle back
        paddle.setLocation(230, 430);
        //wait for click
        waitForClick();

    }

    public static void main(String[] args) {
        new Breakout().start();
    }
}