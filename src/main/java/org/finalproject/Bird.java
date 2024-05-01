/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2024
 * Instructor: Prof. Lily Romano / Prof. Joshua Stough
 *
 * Name: Peter Johnstone
 * Section: YOUR SECTION
 * Date: 4/5/2024
 * Time: 3:20 PM
 *
 * Project: csci205_final_project
 * Package: org.finalproject
 * Class: Bird
 *
 * Description:
 *
 * ****************************************
 */
package org.finalproject;

/**
 * Represents the bird object in the game.
 */
public class Bird {

    /**
     * Represents the height the bird will jump. Bigger numbers mean the bird will jump higher. In the future,
     * this should be dependent on the size of the screen.
     */
    final int JUMP_DISTANCE;

    /**
     * Represents the momentum the bird will have after a jump. Negative because the bird will continue going up for
     * a short amount of time. Larger magnitude number will make the jump feel more floaty, experiment.
     */
    final int POST_JUMP_VELOCITY;


    /**
     * The acceleration of the bird as it falls. Will be called every tick to increase the velocity of bird falling.
     */
    final double GRAVITY;

    /**
     * X coordinate of the bird. Doesn't change.
     */
    final int X;

    /**
     * ID of the bird. Tells the GameView which images to use.
     */
    final int ID;

    /**
     * Tells the GameView which animation the bird is currently on.
     */
    int currentBirdAnimation = 0;


    /**
     * The current vertical velocity of the bird. Positive number means falling down, negative number means flying up.
     */
    double currentFallVelocity = 0;

    /**
     * Y coordinate of the bird.
     */
    double y = 0;

    private int timeUntilBirdAnimationSwitch;

    /**
     * index 0: modifier for jumpDistance
     * index 1: modifier for postJumpVelocity
     * index 2: modifier for gravity
     */
    // New attribute for power-up modifiers
    double[] currentPowerUpModifier = new double[3];

    /**
     * Constructor for the bird.
     * Constants defined above.
     *
     * @param jumpDistance jumpDistance of bird
     * @param postJumpVelocity postJumpVelocity of bird
     * @param gravity gravity of bird
     * @param birdX bird's x
     * @param id id of the bird
     */
    public Bird(int jumpDistance, int postJumpVelocity, double gravity, int birdX, int id) {
        JUMP_DISTANCE = jumpDistance;
        POST_JUMP_VELOCITY = postJumpVelocity;
        GRAVITY = gravity;
        X = birdX;
        ID = id;
        timeUntilBirdAnimationSwitch = Config.TIME_BETWEEN_BIRD_FLAP_ANIMATION_CHANGE;
    }

    /**
     * Function called when space bar is clicked. Will discretely change the birds location up, as well as change the bird's
     * velocity, such that it continues to fly up for a moment.
     */
    public void flapWings() {
        // Apply power-up modifier to jump
        currentFallVelocity = POST_JUMP_VELOCITY + currentPowerUpModifier[1];
        y -= JUMP_DISTANCE + currentPowerUpModifier[0];
    }


    /**
     * Called every game tick. Will increase the bird's falling speed and then change the position of the bird based on the new
     * falling speed.
     */
    public void fall() {
        // Here we rescale our gravity modifier to the height of our screen.
        currentFallVelocity += (GRAVITY + currentPowerUpModifier[2]);
        y += currentFallVelocity;
    }

    /**
     * Increments the currentBirdAnimation, resetting to 0 if we reach 3.
     *
     * @return the new currentBirdAnimation
     */
    public int nextAnimation() {
        currentBirdAnimation++;
        if (currentBirdAnimation == 3)
            currentBirdAnimation = 0;
        return currentBirdAnimation;
    }

    /**
     * Getter for the currentBirdAnimation.
     *
     * @return the currentBirdAnimation
     */
    public int getTimeUntilBirdAnimationSwitch() {
        return timeUntilBirdAnimationSwitch;
    }

    /**
     * Decrements the timeUntilBirdAnimationSwitch.
     */
    public void tickTimeUntilBirdAnimationSwitchDown() {
        timeUntilBirdAnimationSwitch--;
    }

    /**
     * Resets the timeUntilBirdAnimationSwitch to the default value.
     */
    public void resetTimeUntilBirdAnimationSwitch() {
        timeUntilBirdAnimationSwitch = Config.TIME_BETWEEN_BIRD_FLAP_ANIMATION_CHANGE;
    }
    /**
     * Setter for the currentPowerUpModifier.
     */
    public void setCurrentPowerUpModifier(double[] currentPowerUpModifier) {
        this.currentPowerUpModifier = currentPowerUpModifier;
    }

    /**
     * Getter for the jumpDistance.
     *
     * @return the jumpDistance
     */
    public int getJUMP_DISTANCE() {
        return JUMP_DISTANCE;
    }

    /**
     * Getter for the postJumpVelocity.
     *
     * @return the postJumpVelocity
     */
    public int getPOST_JUMP_VELOCITY() {
        return POST_JUMP_VELOCITY;
    }

    /**
     * Getter for the gravity.
     *
     * @return the gravity
     */
    public double getGRAVITY() {
        return GRAVITY;
    }
}