package project_3_geo;

import java.awt.Graphics; //for draw method dont know if its correct.
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Implements a squash ball (class skeleton).
 *
 * @author Antonio Hernandez
 */
public class SquashBall implements Runnable {

    private Polygon squashBall; //squash ball represented as a regular polygon
    private boolean playing; //represents whether the game is stiil being played
    //or has stopped
    private Vector[] direction; //array of directions the ball can move along
    private int currentDirection; //current ball direction
    private Random random;      //random class 
    private Polygon player; // Reference to the player polygon
    private int hitCount = 0; // Counter for the number of hits
    private boolean inCollisionWithPlayer = false; //for collision purposes 

    /**
     * Parameterized constructor.
     */
    public SquashBall(Polygon b, Polygon player) {
        this.squashBall = b;
        this.player = player;

        playing = true;
        direction = new Vector[]{
            new Vector(1, -1),
            new Vector(-1, 0),
            new Vector(1, 1),
            new Vector(-1, -1),
            new Vector(1, 0),
            new Vector(0, 1),
            new Vector(0, -1),
            new Vector(-1, 1)

        };
        random = new Random();
        currentDirection = random.nextInt(direction.length);

    }

    /**
     * Change current vector direction to a random direction.
     */
    public void changeDirection() {
        //TO IMPLEMENT 
        currentDirection = random.nextInt(direction.length);

    }

    @Override
    /**
     * Life loop of this ball.
     */
    public void run() {
        while (playing) {
            Globals.delay(5);
            squashBall.translate(direction[currentDirection]);

            boolean currentlyColliding = squashBall.greatestY() >= player.smallestY()
                    && squashBall.smallestY() <= player.greatestY()
                    && squashBall.greatestX() >= player.smallestX()
                    && squashBall.smallestX() <= player.greatestX();

            if (currentlyColliding) {
                if (!inCollisionWithPlayer) {
                    // New collision detected
                    inCollisionWithPlayer = true;
                    hitCount++; // Increments the hit count thus the pts
                    System.out.println("Hit " + hitCount);

                    adjustPositionPlayer();
                    changeDirection();
                }
            } else {
                // No collision currently, reset the flag
                inCollisionWithPlayer = false;
            }

            // Collision with left or right walls
            if (squashBall.greatestX() >= Globals.WIDTH || squashBall.smallestX() <= 0) {
                adjustPositionHorizontal();
                changeDirectionHorizontal();
            }

            // Collision with top wall
            if (squashBall.smallestY() <= 0) {
                adjustPositionTop();
                changeDirectionVertical();
            }
            if (squashBall.greatestY() >= Globals.HEIGHT) {

                stopGame();
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(null, "Game Over! Your Score is " + hitCount + " pts", "The ball hit the bottom", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    }
                });
            }

//        System.out.println("player "+ player.greatestX());
//        System.out.println("pelot "+ squashBall.greatestY());
            if (squashBall.greatestY() >= player.smallestY()
                    && squashBall.smallestY() <= player.greatestY()
                    && squashBall.greatestX() >= player.smallestX()
                    && squashBall.smallestX() <= player.greatestX()) {

                //adjustPosition();
                adjustPositionPlayer();
                changeDirection();

            }

        }
    }

    private void adjustPositionTop() {
        Vector adjustment = new Vector(0, -squashBall.smallestY());
        squashBall.translate(adjustment);
    }

    private void changeDirectionHorizontal() {
        // Reflect the horizontal direction
        Vector currentDir = direction[currentDirection];
        direction[currentDirection] = new Vector(-currentDir.getX(), currentDir.getY());
    }

    private void changeDirectionVertical() {
        // Reflect the vertical direction
        Vector currentDir = direction[currentDirection];
        direction[currentDirection] = new Vector(currentDir.getX(), -currentDir.getY());
    }

    private void adjustPositionHorizontal() {
        // Adjust position if the ball hits the left wall
        if (squashBall.smallestX() < 0) {
            Vector adjustment = new Vector(-squashBall.smallestX(), 0);
            squashBall.translate(adjustment);
        }

        // Adjust position if the ball hits the right wall
        if (squashBall.greatestX() > Globals.WIDTH) {
            Vector adjustment = new Vector(Globals.WIDTH - squashBall.greatestX(), 0);
            squashBall.translate(adjustment);
        }
    }

    private void adjustPosition() {
        if (squashBall.greatestX() >= Globals.WIDTH) {
            // Adjust the position so the ball is just inside the boundary
            Vector adjustment = new Vector(Globals.WIDTH - squashBall.greatestX(), 0);
            squashBall.translate(adjustment);
        }
        if (squashBall.smallestX() < 0) {
            Vector adjustment = new Vector(-squashBall.smallestX(), 0);
            squashBall.translate(adjustment);
        }
        if (squashBall.greatestY() >= Globals.HEIGHT) {
            Vector adjustment = new Vector(0, Globals.HEIGHT - squashBall.greatestY());
            squashBall.translate(adjustment);
        }
        if (squashBall.smallestY() < 0) {
            Vector adjustment = new Vector(0, -squashBall.smallestY());
            squashBall.translate(adjustment);
        }
    }

    private void adjustPositionPlayer() {
        double overlapLeft = squashBall.greatestX() - player.smallestX();
        double overlapRight = player.greatestX() - squashBall.smallestX();
        double overlapTop = squashBall.greatestY() - player.smallestY();
        double overlapBottom = player.greatestY() - squashBall.smallestY();

        double minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));
        Vector adjustment = new Vector(0, 0);

        if (minOverlap == overlapLeft) {
            adjustment = new Vector(-overlapLeft, 0);
            direction[currentDirection].SetX(-direction[currentDirection].getX()); // Reflect horizontally
        } else if (minOverlap == overlapRight) {
            adjustment = new Vector(overlapRight, 0);
            direction[currentDirection].SetX(-direction[currentDirection].getX()); // Reflect horizontally
        } else if (minOverlap == overlapTop) {
            adjustment = new Vector(0, -overlapTop);
            direction[currentDirection].SetY(-direction[currentDirection].getY()); // Reflect vertically
        } else if (minOverlap == overlapBottom) {
            adjustment = new Vector(0, overlapBottom);
            direction[currentDirection].SetY(-direction[currentDirection].getY()); // Reflect vertically
        }

        squashBall.translate(adjustment);
    }

    /**
     * Squash game stops.
     */
    public void stopGame() {
        playing = false;
    }

    /**
     * Draws the squash ball.
     */
    public void draw(Graphics g) {
        squashBall.draw(g);
    }

}
