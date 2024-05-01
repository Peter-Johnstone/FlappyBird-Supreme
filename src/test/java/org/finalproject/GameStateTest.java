package org.finalproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the GameState class.
 */
class GameStateTest {

    // The GameState object to be tested
    private GameState gameState;

    /**
     * Sets up the GameState object before each test.
     */
    @BeforeEach
    void setUp() {
        gameState = new GameState();
    }

    /**
     * Tests the initPipes method.
     */
    @Test
    void initPipes() {
        gameState.initPipes();
        assertNotNull(gameState.getApproachingObjects());
        assertFalse(gameState.getApproachingObjects().isEmpty());
    }

    /**
     * Tests the initScores method.
     */
    @Test
    void initScores() {
        gameState.initScores();
        assertEquals(0, gameState.getCurrentScore());
        assertEquals(0, gameState.getHighScore());
    }

    /**
     * Tests the initBirds method.
     */
    @Test
    void initBirds() {
        gameState.initBirds();
        assertNotNull(gameState.getYellowBird());
        assertNull(gameState.getBlueBird()); // Assuming TWO_PLAYER is false by default
    }

    /**
     * Tests the updateLocations method.
     */
    @Test
    void updateLocations() {
        gameState.initPipes();
        int initialSize = gameState.getApproachingObjects().size();
        gameState.updateLocations();
        assertTrue(gameState.getCurrentScore() >= 0);
        assertTrue(gameState.getHighScore() >= 0);
        assertEquals(initialSize, gameState.getApproachingObjects().size());
    }

    /**
     * Tests the isExtraLife method.
     */
    @Test
    void isExtraLife() {
        assertFalse(gameState.isExtraLife());
    }
}
