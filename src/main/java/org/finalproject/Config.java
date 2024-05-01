/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2024
 * Instructor: Prof. Lily Romano / Prof. Joshua Stough
 *
 * Name: Peter Johnstone
 * Section: YOUR SECTION
 * Date: 4/4/2024
 * Time: 4:20 PM
 *
 * Project: csci205_final_project
 * Package: org.finalproject
 * Class: Config
 *
 * Description:
 *
 * ****************************************
 */
package org.finalproject;


/**
 * This is the class that handles the customizable values of our program. Helps to assure
 * that our code in not populated by random magic numbers.
 */
public class Config {

    // CONFIGURABLE CONSTANTS, CAN BE CHANGED
    /**
     * The height of the game board.
     */
    final static int BOARD_HEIGHT = 500;

    /**
     * The number of ticks per second, used for game timing.
     */
    final static int TICKS_PER_SECOND = 120;

    /**
     * The velocity at which pipes approach the player.
     */
    final static int APPROACH_VELOCITY = 4;

    /**
     * Modifier for adjusting the size of sprites in the game.
     */
    final static int SPRITE_SIZE = 60;

    /**
     * Modifier for adjusting the space between upper and lower pipes.
     */
    final static int SPACE_BETWEEN_UPPER_LOWER_PIPE = 880;


    /**
     * This represents the time between the bird flaps. A higher number will mean that the bird is less animated.
     */
    final static int TIME_BETWEEN_BIRD_FLAP_ANIMATION_CHANGE = 6;

    /**
     * A modifier for the pipes' width. Bigger number means fatter pipes.
     */
    final static int PIPE_WIDTH_MODIFIER = 2;

    public static final int UNLIKELINESS_OF_POWERUP = 8;

    /**
     * Determines how long it takes the background to do a full cycle. Larger numbers make the background appear slower.
     */
    final static int BACKGROUND_SCROLL_DURATION = 30;

    final static int MAX_NUMBER_OF_PIPE_IMAGE_OBJECTS = 2;

    static boolean TWO_PLAYER = false;


    // DERIVED CONSTANTS, DO NOT CHANGE
    /**
     * The width of the game board, calculated based on the height.
     */
    public static final int BOARD_WIDTH = (int) (BOARD_HEIGHT * (7.0 / 5.0));

    /**
     * Half of the game board width.
     */
    public static final int HALF_BOARD_WIDTH = BOARD_WIDTH / 2;

    /**
     * Half of the game board height.
     */
    public static final int HALF_BOARD_HEIGHT = BOARD_HEIGHT / 2;



}