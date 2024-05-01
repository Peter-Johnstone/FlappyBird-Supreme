package org.finalproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BirdTest {

    Bird bird;

    @BeforeEach
    void setUp() {
        bird = new Bird(0, -7, .3, -250, 1);
    }

    /* Test the flapWings method */
    @Test
    void flapWings() {
        // Set initial bird position
        bird.y = 200;

        // Call flapWings method
        bird.flapWings();

        // Check if the bird's position has been updated correctly
        assertEquals(200 - bird.JUMP_DISTANCE, bird.y, 0.001);

        // Check if the currentFallVelocity has been updated correctly
        assertEquals(bird.POST_JUMP_VELOCITY, bird.currentFallVelocity, 0.001);
    }

    /* Test the fall method */
    @Test
    void fall() {
        // Set initial bird position and velocity
        bird.y = 200;
        bird.currentFallVelocity = 0;

        // Call fall method
        bird.fall();

        // Check if the bird's position has been updated correctly
        assertEquals(200 + bird.currentFallVelocity, bird.y, 0.001);
    }
}