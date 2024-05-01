/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2024
 * Instructor: Prof. Lily Romano / Prof. Joshua Stough
 *
 * Name: Peter Johnstone
 * Section: YOUR SECTION
 * Date: 4/21/2024
 * Time: 11:50 PM
 *
 * Project: csci205_final_project
 * Package: org.finalproject
 * Class: ApproachingObject
 *
 * Description:
 *
 * ****************************************
 */
package org.finalproject;

/**
 * Represents an object that approaches the player in the game.
 */
public class ApproachingObject {
    /**
     * The x-coordinate of the object.
     */
    double x;

    /**
     * The y-coordinate of the object.
     */
    double y;

    /**
     * Constructor for the ApproachingObject class.
     *
     * @param x the x-coordinate of the object
     * @param y the y-coordinate of the object
     */
    public ApproachingObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Moves the object towards the left, simulating its approach towards the player.
     * The velocity at which the object approaches is defined by the Config class.
     */
    public void approach() {
        this.x -= Config.APPROACH_VELOCITY;
    }

}