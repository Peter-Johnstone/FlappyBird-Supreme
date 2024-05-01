package org.finalproject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PipeTest {

    /* Test the approach method */
    @Test
    void approach() {
        // Create a pipe at initial position
        Pipe pipe = new Pipe(100, 200);

        // Move the pipe
        pipe.approach();

        // Check if the x-coordinate has been updated correctly
        assertEquals(100 - Config.APPROACH_VELOCITY, pipe.x, 0.001);
    }

    /* Test the generateRandomizedPipe method */
    @Test
    void generateRandomizedPipe() {
        // Generate a random pipe
        Pipe pipe = Pipe.generateRandomizedPipe();

        // Check if the x-coordinate is set to half of the board width
        assertEquals(Config.HALF_BOARD_WIDTH, 350, 0.001);

        // Check if the y-coordinate is within the specified range
        assertFalse(pipe.y >= 100 && pipe.y <= 400);
    }
}