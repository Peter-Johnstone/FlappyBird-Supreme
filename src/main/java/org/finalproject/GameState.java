/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2024
 * Instructor: Prof. Lily Romano / Prof. Joshua Stough
 *
 * Name: Peter Johnstone
 * Section: YOUR SECTION
 * Date: 4/4/2024
 * Time: 4:12 PM
 *
 * Project: csci205_final_project
 * Package: org.finalproject
 * Class: GameState
 *
 * Description:
 *
 * ****************************************
 */
package org.finalproject;

import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the state of the game.
 */
public class GameState {
    /**
     * A random number generator used for determining whether to generate pipes or power-ups.
     */
    public final static Random random = new Random();

    /**
     * The integer current score of the user.
     */
    private int currentScore;

    /**
     * The integer high score of the user.
     */
    private int highScore;
    /**
     * The y-coordinate of the bird.
     */
    private Bird yellowBird;

    private Bird blueBird;

    private PowerUp powerUp;

    private boolean extraLife = false;
    /**
     * The list of pipes currently present in the game.
     */
    private List<ApproachingObject> approachingObjects;

    /**
     * Initializes the initial positions of the bird and pipes.
     */
    public void initPipes() {
        approachingObjects = new ArrayList<>(List.of(Pipe.generateRandomizedPipe()));
    }

    /**
     * Initializes the starting score to 0.
     */
    public void initScores() {
        currentScore = 0;
        highScore = 0;
    }

    /**
     * This makes the main bird of the program. This is bird1, and is manipulated by the space bar.
     *
     */
    public void initBirds() {
        yellowBird = new Bird(0, -7, .3, -250, 1);

        blueBird = new Bird(0, -7, .3, -150, 2);

    }

    /**
     * Our constructor for the GameState class. When we make a new GameState we most initialize the locations and scores.
     */
    public GameState() {
        initPipes();
        initScores();
        initBirds();
    }

    /**
     * Updates the positions of the bird and pipes in the game.
     * Handles pipe movement and generation, as well as bird falling.
     */
    public void updateLocations() {
        yellowBird.fall();
        if (Config.TWO_PLAYER)
            blueBird.fall();


        for (ApproachingObject approachingObject : approachingObjects) {
            if (approachingObject.x + Config.PIPE_WIDTH_MODIFIER * Config.SPRITE_SIZE / 2.0 <= -Config.HALF_BOARD_WIDTH) {
                // removes the approaching object because it reaches left side of board
                approachingObjects.remove(approachingObject);
                if (approachingObject instanceof Pipe)
                    highScore = Math.max(++currentScore, highScore);
                if (approachingObject instanceof PowerUp)
                    powerUp = null;
            } else
                approachingObject.approach();
        }

        if (approachingObjects.get(0).x < 0 && approachingObjects.size() == 1) {

            if (random.nextInt(1, Config.UNLIKELINESS_OF_POWERUP) == 1 && approachingObjects.get(0) instanceof Pipe) {
                powerUp = PowerUp.generateRandomizedPowerUp();
                approachingObjects.add(powerUp);
            }
            else approachingObjects.add(Pipe.generateRandomizedPipe());
        }
    }

    /**
     * Adds the current powerUp (the one found by the update locations function) to the bird.
     * There are 3 options for the powerUps.
     *
     * @param bird the bird we want to add the powerup to
     */
    public void triggerPowerUp(Bird bird) {
        switch (powerUp.powerUpType) {
            case 1:
                // change gravity constants
                bird.setCurrentPowerUpModifier(new double[]{0, 3, -.1});
                extraLife = false;
                break;
            case 2:
                // flip gravity constants
                bird.setCurrentPowerUpModifier(new double[]{-2 * bird.getJUMP_DISTANCE(), -2 * bird.getPOST_JUMP_VELOCITY(), -2 * bird.getGRAVITY()});
                extraLife = false;
                break;
            case 3:
                // add another life
                bird.setCurrentPowerUpModifier(new double[]{0, 0, 0});
                extraLife = true;
                break;
        }
    }


    /**
     * Checks if the bird is touching a pipe.
     *
     * @param imageView The pipe image.
     * @return True if the bird is touching the pipe, false otherwise.
     */
    public static boolean checkBirdTouching(Bird bird, ImageView imageView) {
        // represent the hitBox of the bird.
        double birdHitBoxRadiusX = Config.SPRITE_SIZE / 2.1;
        double birdHitBoxRadiusY = Config.SPRITE_SIZE / 3.0;

        for (double angle = 0; angle < 360; angle += 3) {
            double x = bird.X + Config.HALF_BOARD_WIDTH + birdHitBoxRadiusX * Math.cos(Math.toRadians(angle));

            double y = bird.y + Config.HALF_BOARD_HEIGHT + birdHitBoxRadiusY * Math.sin(Math.toRadians(angle));

            // This Math.max(y, 0) makes it so that even if the bird is way above the top of the screen, for collision purposes,
            // it treats the bird as if it's at 0 (top of screen). Essentially, this handles the bug that you can fly above the pipes.
            if (isPointInImageBounds(x, Math.max(y, 0), imageView))
                return true;
        }
        return false;
    }

    /**
     * Checks if a point is within the bounds of an image.
     *
     * @param x         The x-coordinate of the point.
     * @param y         The y-coordinate of the point.
     * @param imageView The image view.
     * @return True if the point is within the bounds of the image, false otherwise.
     */
    private static boolean isPointInImageBounds(double x, double y, ImageView imageView) {
        return x >= imageView.getBoundsInParent().getMinX() &&
                x <= imageView.getBoundsInParent().getMaxX() &&
                y >= imageView.getBoundsInParent().getMinY() &&
                y <= imageView.getBoundsInParent().getMaxY();
    }

    /**
     * Getter for the yellow bird.
     *
     * @return The yellow bird.
     */
    public Bird getYellowBird() {
        return yellowBird;
    }

    /**
     * Getter for the current score.
     *
     * @return The current score.
     */
    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Setter for the current score.
     *
     * @param currentScore The new current score.
     */
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    /**
     * Getter for the high score.
     *
     * @return The high score.
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Getter for the approaching objects.
     *
     * @return The approaching objects.
     */
    public List<ApproachingObject> getApproachingObjects() {
        return approachingObjects;
    }

    /**
     * Getter for the blue bird.
     *
     * @return the blue bird.
     */
    public Bird getBlueBird() {
        return blueBird;
    }

    /**
     * Getter for the extraLife.
     *
     * @return the extraLife
     */
    public boolean isExtraLife() {
        return extraLife;
    }

    /**
     * Setter for the extraLife.
     *
     * @param extraLife the new extraLife
     */
    public void setExtraLife(boolean extraLife) {
        this.extraLife = extraLife;
    }
}