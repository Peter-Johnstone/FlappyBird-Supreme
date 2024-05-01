/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2024
 * Instructor: Prof. Lily Romano / Prof. Joshua Stough
 *
 * Name: Peter Johnstone
 * Section: YOUR SECTION
 * Date: 4/11/2024
 * Time: 3:57 PM
 *
 * Project: csci205_final_project
 * Package: org.finalproject
 * Class: GameController
 *
 * Description:
 *
 * ****************************************
 */
package org.finalproject;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

/**
 * The GameController class is responsible for controlling the game. It has access to the gameView and gameState, and
 * updates the game every tick.
 */
public class GameController {
    // The gameView and gameState objects
    final private GameView gameView;
    final GameState gameState;
    // The game loop
    private AnimationTimer gameLoop;
    // These variables tell us what stage of the game we are in
    private boolean gameStarted;
    private boolean gameOver;

    /**
     * Initializes a new gameController object. This object will control/have access to the gameView and gameState.
     *
     * @param scene the scene we will run our game on
     */
    public GameController(Scene scene) {
        StackPane root = (StackPane) scene.getRoot();

        // Create the game loop, but don't start it yet. We start after a player presses a key, so that is done in event handling.
        createGameLoop();

        gameView = new GameView(root, this);
        gameState = new GameState();

        // These constants tell the controller at what stage of the game we are. Useful because it tells us how the computer should interpret inputs
        gameStarted = false;
        gameOver = false;

        // Event handlers
        initEventHandlers(scene);

        // Update the game once, before the game actually starts, to position the objects in their starting locations visually.
        updateGame();
    }

    /**
     * Initializes event handlers for user input.
     */
    private void initEventHandlers(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (!gameStarted) {
                // We pressed a key, but the game hasn't started yet. This is the que to start the game.
                gameLoop.start();
                triggerRestart(true);
                gameStarted = true;
            } else if (gameOver)
                // Pressing a key when the game already over means we want to restart
                triggerRestart(true);
            else if (event.getCode() == KeyCode.SPACE)
                gameState.getYellowBird().flapWings();
            else if (event.getCode() == KeyCode.ENTER)
                gameState.getBlueBird().flapWings();
        });
    }

    /**
     * Handles the continuous events, such as bird falling, or pipe approaching
     */
    private void createGameLoop() {

        final int NANO_SECONDS_PER_TICK = 1_000_000_000 / Config.TICKS_PER_SECOND;
        gameLoop = new AnimationTimer() {
            long lastUpdate = System.nanoTime();

            // This handles the TPS (ticks per second) of the game.
            @Override
            public void handle(long now) {
                long elapsedTime = now - lastUpdate;
                if (elapsedTime >= NANO_SECONDS_PER_TICK) { //
                    lastUpdate = now;
                    updateGame();
                }
            }

        };
    }

    /**
     * This is the function that is called every tick of the game
     */
    private void updateGame() {

        // Updates the locations in the data
        gameState.updateLocations();

        // Updates the locations visually
        gameView.updateBird(gameState.getYellowBird()); // Pass updated bird position
        if (Config.TWO_PLAYER)
            gameView.updateBird(gameState.getBlueBird());
        gameView.updateApproachingObjects(gameState.getApproachingObjects()); // Pass updated pipe positions
        gameView.updateScores(gameState.getCurrentScore(), gameState.getHighScore()); // Pass updated score

        // check powerUp collisions
        checkPowerUpCollision();

        // Checks whether the game is over
        checkGameOver();

        if (gameOver) {
            if (gameState.isExtraLife())
                // Check if we have an extra life, we just restart with the same score
                triggerRestart(false);
            else
                // we didn't have an extra life so the game ends
                triggerGameOver();
        }

    }

    /**
     * Triggers the game over screen.
     */
    void triggerGameOver() {
        gameView.displayDeath();
        gameLoop.stop();
        gameView.getParTrans().pause();
    }


    /**
     * Checks if the game is over.
     */
    private void checkGameOver() {
        Bird[] birds = {gameState.getYellowBird()};
        if (Config.TWO_PLAYER)
            birds = new Bird[]{gameState.getYellowBird(), gameState.getBlueBird()};

        for (Bird bird : birds) {
            for (ImageView[] upperLowerPipe : gameView.getPipeImages()) {
                if (GameState.checkBirdTouching(bird, upperLowerPipe[0]) || GameState.checkBirdTouching(bird, upperLowerPipe[1]) || bird.y >= Config.HALF_BOARD_HEIGHT - Config.SPRITE_SIZE / 2.0)
                    gameOver = true;
            }
        }
    }

    /**
     * Checks if the bird has collided with the powerUp
     */
    private void checkPowerUpCollision() {
        Bird[] birds = {gameState.getYellowBird()};
        if (Config.TWO_PLAYER)
            birds = new Bird[]{gameState.getYellowBird(), gameState.getBlueBird()};

        for (Bird bird : birds) {
            ImageView powerUpImageView = gameView.getPowerUpImageView();
            if (powerUpImageView.isVisible() && GameState.checkBirdTouching(bird, powerUpImageView)) {
                // TRIGGER UPDATE IN BIRD TO MODIFIER
                gameState.triggerPowerUp(bird);
                gameView.updatePowerUpTrackerIcon(powerUpImageView);
                powerUpImageView.setVisible(false);
            }
        }
    }


    /**
     * This is called when we press a key after we have died, which is the que to restart. When we restart,
     * we reset the current score unless the boolean says false,
     * the bird y position, and the pipe positions. We must also restart the gameLoop.
     */
    void triggerRestart(boolean resetScore) {
        gameView.getPowerUpTrackerImageView().setVisible(false);
        gameView.getPowerUpImageView().setVisible(false);
        gameState.setExtraLife(false);
        gameOver = false;
        gameView.hideDeathScreen();
        gameView.hideMainMenu();
        gameLoop.start();
        gameState.getYellowBird().currentFallVelocity = 0;
        gameState.getYellowBird().y = 0;
        gameState.getYellowBird().setCurrentPowerUpModifier(new double[]{0, 0, 0});
        if (Config.TWO_PLAYER) {
            gameState.getBlueBird().currentFallVelocity = 0;
            gameState.getBlueBird().setCurrentPowerUpModifier(new double[]{0, 0, 0});
            gameState.getBlueBird().y = 0;
        }
        if (resetScore)
            gameState.setCurrentScore(0);
        gameState.initPipes();
        gameView.getParTrans().play();
        gameState.getYellowBird().flapWings();
        if (Config.TWO_PLAYER)
            gameState.getBlueBird().flapWings();
    }
}