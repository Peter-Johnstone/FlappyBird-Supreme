/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2024
 * Instructor: Prof. Lily Romano / Prof. Joshua Stough
 *
 * Name: Peter Johnstone
 * Section: YOUR SECTION
 * Date: 4/5/2024
 * Time: 11:32 PM
 *
 * Project: csci205_final_project
 * Package: org.finalproject
 * Class: Pipe
 *
 * Description:
 *
 * ****************************************
 */
package org.finalproject;

import java.util.Random;

public class Pipe extends ApproachingObject {
    /**
     * A random number generator used for generating pipes with randomized properties.
     */
    public final static Random random = new Random();


    /**
     * Constructs a new Pipe object with the specified coordinates.
     *
     * @param x The x-coordinate of the pipe.
     * @param y The y-coordinate of the pipe.
     */
    public Pipe(double x, double y) {
        super(x, y);
    }


    /**
     * Generates a new Pipe object with randomized properties.
     * The x-coordinate is set to half of the board width + some buffer to make sure it's offscreen,
     * and the y-coordinate is randomly generated within a specified range.
     *
     * @return A new Pipe object with randomized properties.
     */
    public static Pipe generateRandomizedPipe() {
        return new Pipe(Config.HALF_BOARD_WIDTH + Config.PIPE_WIDTH_MODIFIER * Config.SPRITE_SIZE / 2.0, random.nextInt(320, 560));
    }
}