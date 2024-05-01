/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2024
 * Instructor: Prof. Lily Romano / Prof. Joshua Stough
 *
 * Name: Peter Johnstone
 * Section: YOUR SECTION
 * Date: 4/21/2024
 * Time: 11:48 PM
 *
 * Project: csci205_final_project
 * Package: org.finalproject
 * Class: Powerup
 *
 * Description:
 *
 * ****************************************
 */
package org.finalproject;

import java.util.Random;

/**
 * Represents a powerUp object in the game.
 */
public class PowerUp extends ApproachingObject {
    /**
     * A random number generator used for generating PowerUps with randomized properties.
     */
    public final static Random random = new Random();

    /**
     * Represents what the powerUp actually does.
     * 1: Switches (lightens) gravity.
     * 2: Flips gravity
     * 3: Gives a second life
     */
    int powerUpType;

    /**
     * Represents whether the powerUp was just generated.
     */
    boolean justGenerated;

    /**
     * Constructor for the PowerUp class.
     *
     * @param x           the x-coordinate of the powerUp
     * @param y           the y-coordinate of the powerUp
     * @param powerUpType the type of powerUp
     */
    public PowerUp(double x, double y, int powerUpType) {
        super(x, y);
        this.powerUpType = powerUpType;
        this.justGenerated = true;
    }

    /**
     * Sets the justGenerated boolean
     */
    public void setJustGenerated(boolean justGenerated) {
        this.justGenerated = justGenerated;
    }

    /**
     * Generates a new powerUp at the specified location on the right of the screen.
     *
     * @return the generated powerup
     */
    public static PowerUp generateRandomizedPowerUp() {
        return new PowerUp(Config.HALF_BOARD_WIDTH + Config.SPRITE_SIZE / 2.0, random.nextInt(-Config.HALF_BOARD_HEIGHT + 50, Config.HALF_BOARD_HEIGHT - 50), random.nextInt(1, 4));
    }
}