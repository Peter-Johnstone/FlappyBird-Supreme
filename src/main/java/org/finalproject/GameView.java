/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2024
 * Instructor: Prof. Lily Romano / Prof. Joshua Stough
 *
 * Name: Peter Johnstone
 * Section: YOUR SECTION
 * Date: 4/11/2024
 * Time: 3:54 PM
 *
 * Project: csci205_final_project
 * Package: org.finalproject
 * Class: GameView
 *
 * Description:
 *
 * ****************************************
 */
package org.finalproject;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.List;

/**
 * The GameView class is responsible for displaying the game window and all its components.
 */
public class GameView {

    /**
     * The StackPane root that we will add all our objects to.
     */
    final private StackPane root;
    // The bird images
    private ImageView yellowBirdImage;
    private ImageView blueBirdImage;
    private Image[] yellowBirdImages;
    private Image[] blueBirdImages;
    private ImageView[][] pipeImages;
    // The score text
    private Text numberScoreText;
    // The high score text
    private Text numberHighScoreText;
    private ParallelTransition parTrans;
    // The end score text
    private Text numberEndScoreText;
    private Clip clip;
    private StackPane gameOverPane;
    private Button volumeButton;
    private ImageView powerUpImageView;
    private ImageView powerUpTrackerImageView;
    private StackPane mainMenuScreen;

    /**
     * Constructor for the GameView class. Initializes the root and calls the initUI method.
     *
     * @param root The StackPane root that we will add all our objects to.
     */
    public GameView(StackPane root, GameController gameController) {
        this.root = root;

        initBackgroundScroll();
        initBird();
        initPipe();
        initPowerUps();
        initScores();
        initBackgroundSound();
        initDeathScreen(gameController);
        initMainMenuScreen(gameController);
        initVolumeButton();
    }


    /**
     * Starts the background scrolling animation.
     */
    private void initBackgroundScroll() {
        Image backgroundImage = new Image("background-image.png");
        ImageView backgroundImageView1 = new ImageView(backgroundImage);
        ImageView backgroundImageView2 = new ImageView(backgroundImage);
        backgroundImageView1.setTranslateX(root.getWidth());
        backgroundImageView2.setTranslateX(root.getWidth());
        root.getChildren().addAll(backgroundImageView1, backgroundImageView2);
        backgroundImageView2.setTranslateX(backgroundImageView1.getFitWidth());
        TranslateTransition trans1 = new TranslateTransition(Duration.seconds(Config.BACKGROUND_SCROLL_DURATION), backgroundImageView1);
        TranslateTransition trans2 = new TranslateTransition(Duration.seconds(Config.BACKGROUND_SCROLL_DURATION), backgroundImageView2);
        trans1.setByX(-root.getWidth());
        trans2.setByX(-root.getWidth());
        trans1.setCycleCount(Animation.INDEFINITE);
        trans2.setCycleCount(Animation.INDEFINITE);
        parTrans = new ParallelTransition(trans1, trans2);
        parTrans.play();
    }


    /**
     * Initializes the bird image and adds it to the game window.
     */
    private void initBird() {
        yellowBirdImage = new ImageView(new Image("yellowbird-1.png"));
        yellowBirdImages = new Image[]{new Image("yellowbird-1.png"), new Image("yellowbird-2.png"), new Image("yellowbird-3.png")};
        setImageSize(yellowBirdImage, Config.SPRITE_SIZE);
        root.getChildren().add(yellowBirdImage);


        blueBirdImage = new ImageView(new Image("bluebird-1.png"));
        blueBirdImages = new Image[]{new Image("bluebird-1.png"), new Image("bluebird-2.png"), new Image("bluebird-3.png")};
        setImageSize(blueBirdImage, Config.SPRITE_SIZE);
        if (!Config.TWO_PLAYER)
            blueBirdImage.setVisible(false);
        root.getChildren().add(blueBirdImage);
    }

    /**
     * Initializes the pipe images and adds them to the game window.
     */
    private void initPipe() {

        pipeImages = new ImageView[Config.MAX_NUMBER_OF_PIPE_IMAGE_OBJECTS][2];
        for (int i = 0; i < Config.MAX_NUMBER_OF_PIPE_IMAGE_OBJECTS; i++) {
            // bottom pipe
            pipeImages[i][0] = new ImageView(new Image("pipe-green.png"));
            setImageSize(pipeImages[i][0], Config.SPRITE_SIZE);
            setWidth(pipeImages[i][0], Config.PIPE_WIDTH_MODIFIER * Config.SPRITE_SIZE);

            // top pipe
            pipeImages[i][1] = new ImageView(new Image("pipe-green-flipped.png"));
            setImageSize(pipeImages[i][1], Config.SPRITE_SIZE);
            setWidth(pipeImages[i][1], Config.PIPE_WIDTH_MODIFIER * Config.SPRITE_SIZE);

            root.getChildren().addAll(pipeImages[i][0], pipeImages[i][1]);

        }
    }

    private void initPowerUps() {
        powerUpImageView = new ImageView();
        setImageSize(powerUpImageView, Config.SPRITE_SIZE);

        powerUpTrackerImageView = new ImageView(new Image("arrow-up.png"));
        setImageSize(powerUpTrackerImageView, Config.SPRITE_SIZE);
        setLocation(powerUpTrackerImageView, 300, -200);
        powerUpTrackerImageView.setVisible(false);
        root.getChildren().addAll(powerUpImageView, powerUpTrackerImageView);
    }

    /**
     * Initializes the score text objects and adds them to the game window.
     */
    public void initScores() {

        // Create Text objects to display the scores
        numberScoreText = addTextStyle(new Text(""));

        setLocation(numberScoreText, 0, -Config.HALF_BOARD_HEIGHT / 1.2);

        root.getChildren().add(numberScoreText);
    }

    /**
     * Stylizes the text
     *
     * @param text the Text object
     * @return the stylized text object
     */
    private Text addTextStyle(Text text) {
        // Create a pixel font style
        Font pixelFont = Font.font("System", FontWeight.BOLD, 40);
        text.setFont(pixelFont);
        text.setFill(Color.WHITE);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(1);
        dropShadow.setOffsetY(1);
        text.setEffect(dropShadow);
        return text;
    }

    /**
     * Method to play background music
     * <p>
     * Acquired from HelloPokemon.java
     */
    private void initBackgroundSound() {
        try {
            String path = "src/main/resources/background-music.wav";
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }

    /**
     * Visually updates the approachingObjects' location based on the new position
     *
     * @param approachingObjects the list of approachingObjects to update
     */
    public void updateApproachingObjects(List<ApproachingObject> approachingObjects) {
        for (int i = 0; i < Config.MAX_NUMBER_OF_PIPE_IMAGE_OBJECTS; i++) {
            ImageView curLowerPipe = pipeImages[i][0];
            ImageView curUpperPipe = pipeImages[i][1];

            if (i < approachingObjects.size()) {
                ApproachingObject approachingObject = approachingObjects.get(i);
                if (approachingObject instanceof Pipe pipe) {
                    setLocation(curLowerPipe, pipe.x, pipe.y);
                    setLocation(curUpperPipe, pipe.x, pipe.y - Config.SPACE_BETWEEN_UPPER_LOWER_PIPE);
                } else if (approachingObject instanceof PowerUp powerUp) {
                    // Update power-up location
                    handlePowerUpApproach(powerUp);
                }
            } else {
                curLowerPipe.setTranslateX(Config.BOARD_WIDTH * 100);
                curUpperPipe.setTranslateX(Config.BOARD_WIDTH * 100);
            }
        }
    }

    /**
     * Sets the image for the powerUpImageView if it has just been generated. Otherwise, just approach.
     *
     * @param powerUp the powerUp
     */
    private void handlePowerUpApproach(PowerUp powerUp) {
        if (powerUp.justGenerated) {
            powerUpImageView.setVisible(true);
            powerUp.setJustGenerated(false);
            switch (powerUp.powerUpType) {
                case 1:
                    powerUpImageView.setImage(new Image("arrow-up.png"));
                    break;
                case 2:
                    powerUpImageView.setImage(new Image("arrow-up-from-dotted-line.png"));
                    break;
                case 3:
                    powerUpImageView.setImage(new Image("elixir.png"));
                    break;
            }
        }
        setLocation(powerUpImageView, powerUp.x, powerUp.y);
    }

    public void updatePowerUpTrackerIcon(ImageView powerUpImageView) {
        powerUpTrackerImageView.setVisible(true);
        powerUpTrackerImageView.setImage(powerUpImageView.getImage());
    }

    /**
     * Updates the score tracker with the current score.
     */
    public void updateScores(int currentScore, int highScore) {
        numberScoreText.setText(currentScore + "");
        numberHighScoreText.setText("High: " + highScore);
        numberEndScoreText.setText("Score: " + currentScore);
    }


    /**
     * Initializes the death screen components.
     *
     * @param gameController The game controller to handle the restart and main menu buttons.
     */
    public void initDeathScreen(GameController gameController) {
        gameOverPane = new StackPane();
        createLayout();
        createGameOverText();
        createScoreTexts();
        createButtons(gameController);
        root.getChildren().add(gameOverPane);
        initScores();
        gameOverPane.setVisible(false);
    }

    /**
     * Creates the layout for the game over screen.
     */
    private void createLayout() {
        // The game over text
        Rectangle backgroundOverlay = new Rectangle(Config.BOARD_WIDTH, Config.BOARD_HEIGHT, Color.rgb(0, 0, 0, 0.5));
        Rectangle deathScreen = new Rectangle(Config.BOARD_WIDTH / 2.4, (Config.BOARD_HEIGHT / 1.9) + 50, Color.BEIGE);
        gameOverPane.getChildren().addAll(backgroundOverlay, deathScreen);
    }

    /**
     * Creates the game over text.
     */
    private void createGameOverText() {
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        gameOverText.setFill(Color.RED);
        setLocation(gameOverText, 0, -110); // Adjusted Y-coordinate
        gameOverPane.getChildren().add(gameOverText);
    }

    /**
     * Creates the score texts.
     */
    private void createScoreTexts() {
        numberHighScoreText = addTextStyle(new Text(""));
        numberEndScoreText = addTextStyle(new Text(""));
        setLocation(numberHighScoreText, 0, 20);
        setLocation(numberEndScoreText, 0, -40);
        gameOverPane.getChildren().addAll(numberEndScoreText, numberHighScoreText);
    }

    /**
     * Creates the buttons for the game over screen.
     *
     * @param gameController The game controller to handle the restart and main menu buttons.
     */
    private void createButtons(GameController gameController) {
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setOnAction(event -> gameController.triggerRestart(true));
        setLocation(playAgainButton, -70, 110);
        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(event -> displayMainMenu());
        setLocation(mainMenuButton, 70, 110);
        gameOverPane.getChildren().addAll(playAgainButton, mainMenuButton);
    }

    /**
     * Initializes the volume control button.
     */
    private void initVolumeButton() {
        // Load the image
        Image volumeIcon = new Image("volume-button.png");
        Image mutedIcon = new Image("muted-button.png");

        // Create an ImageView with the image
        ImageView volumeImageView = new ImageView(volumeIcon);
        volumeImageView.setFitWidth(40);
        volumeImageView.setFitHeight(40);

        // Initialize volume control button
        volumeButton = new Button();
        volumeButton.setGraphic(volumeImageView);
        volumeButton.setVisible(false); // Initially hidden
        setLocation(volumeButton, 316, -220);

        volumeButton.setPrefWidth(50); // Set button width to make it square
        volumeButton.setPrefHeight(50); // Set button height to make it square

        // Add event handler to toggle between images when button is clicked
        volumeButton.setOnAction(event -> {
            // Toggle between gray and color images
            if (volumeImageView.getImage() == volumeIcon) {
                volumeImageView.setImage(mutedIcon); // Change to color image
                clip.stop();

            } else {
                volumeImageView.setImage(volumeIcon); // Change to gray image
                clip.start();
            }
        });
        root.getChildren().add(volumeButton);
    }

    /**
     * Initializes a main menu screen
     *
     * @param gameController the gameController object
     */
    private void initMainMenuScreen(GameController gameController) {
        mainMenuScreen = new StackPane(); // Spacing between components

        // Create main menu components
        mainMenuScreen.setBackground(new Background(new BackgroundFill(Color.TEAL, CornerRadii.EMPTY, Insets.EMPTY)));

        ImageView titleImage = getTitleImage();
        Button mainMenuPlayAgain = getMainMenuPlayAgain(gameController);
        ToggleButton playerModeButton = getPlayerModeButton();
        ImageView birdImage1 = getBirdImage("yellowbird-2.png");
        ImageView birdImage2 = getBirdImage("bluebird-2.png");
        Text credits = addTextStyle(new Text("By Peter, Jack, and Nikita"));

        // set locations
        setLocation(titleImage, 0, -50);
        setLocation(mainMenuPlayAgain, -50, 70);
        setLocation(playerModeButton, 50, 70);
        setLocation(birdImage1, -230, -170);
        setLocation(birdImage2, 230, -170);
        setLocation(credits, -100, 200);


        // Add components to main menu screen
        mainMenuScreen.getChildren().addAll(titleImage, mainMenuPlayAgain, playerModeButton, birdImage1, birdImage2, credits);

        // Add main menu screen to root pane
        root.getChildren().add(mainMenuScreen);
        mainMenuScreen.setVisible(false);
    }

    /**
     * Quick helper method to get a bird image
     *
     * @param s string name of image
     * @return imageView of image
     */
    private ImageView getBirdImage(String s) {
        ImageView birdImage = new ImageView(new Image(s));
        setImageSize(birdImage, 100);
        return birdImage;
    }

    /**
     * Quick helper method to get the title
     *
     * @return the title image
     */
    private ImageView getTitleImage() {
        ImageView titleImage = new ImageView(new Image("title.png"));
        setImageSize(titleImage, 350);
        return titleImage;
    }

    /**
     * Makes a button on the main menu that restarts the game
     *
     * @param gameController the game controller object
     * @return a working button that restarts the game
     */
    private Button getMainMenuPlayAgain(GameController gameController) {
        Button mainMenuPlayAgain = new Button("Play Again");
        mainMenuPlayAgain.setOnAction(event -> gameController.triggerRestart(true));
        mainMenuScreen.setPadding(new Insets(20));
        mainMenuPlayAgain.setStyle("-fx-background-color: #ff6347; -fx-text-fill: white;");
        return mainMenuPlayAgain;
    }

    /**
     * Makes a button that can toggle between multiplayer and singleplayer.
     *
     * @return the multiplayer/singleplayer switching button
     */
    private ToggleButton getPlayerModeButton() {
        ToggleButton playerModeButton = new ToggleButton("One Player");
        playerModeButton.setSelected(true); // Default to "One Player" mode

        // Add event listener to handle changes in player mode
        playerModeButton.setOnAction(event -> {
            if (playerModeButton.isSelected()) {
                playerModeButton.setText("One Player");
                blueBirdImage.setVisible(false);
                Config.TWO_PLAYER = false;
            } else {
                playerModeButton.setText("Two Player");
                blueBirdImage.setVisible(true);
                Config.TWO_PLAYER = true;
            }
        });
        playerModeButton.setStyle("-fx-background-color: #ff6347; -fx-text-fill: white;");

        return playerModeButton;
    }

    /**
     * Displays the main menu
     */
    public void displayMainMenu() {
//        System.out.println("getting here");
        mainMenuScreen.setVisible(true);
    }

    /**
     * Hides the main menu
     */
    public void hideMainMenu() {
        mainMenuScreen.setVisible(false);
    }

    /**
     * Displays the death screen.
     */
    public void displayDeath() {
        numberScoreText.setVisible(false);
        powerUpImageView.setVisible(false);

        gameOverPane.setVisible(true);
        volumeButton.setVisible(true);
    }

    /**
     * Hides the death screen.
     */
    public void hideDeathScreen() {
        numberScoreText.setVisible(true);

        gameOverPane.setVisible(false);
        volumeButton.setVisible(false);
    }

    /**
     * Visually updates the bird's location based on the new position. Also, handles the flap animation.
     *
     * @param bird the bird we want to update
     */
    public void updateBird(Bird bird) {

        switch (bird.ID) {
            case 1:
                animateBirdFlap(bird, yellowBirdImage, yellowBirdImages);
                break;
            case 2:
                animateBirdFlap(bird, blueBirdImage, blueBirdImages);
                break;
        }

        setLocation(getBirdImage(bird), bird.X, bird.y);
    }

    /**
     * Animates the bird flap. Also changes the angle of the image depending on falling speed.
     *
     * @param bird the bird we are animating
     */
    private void animateBirdFlap(Bird bird, ImageView birdImage, Image[] birdImages) {
        final int SPEED_WHERE_BIRD_STOPS_FLAPPING = 6;
        final int SPEED_WHERE_ANGLE_CHANGES = 4;
        final int ANGLE_CHANGE = 20;

        if (bird.currentFallVelocity < SPEED_WHERE_BIRD_STOPS_FLAPPING)
            // tick down until next animation.
            bird.tickTimeUntilBirdAnimationSwitchDown();

        if (bird.getTimeUntilBirdAnimationSwitch() == 0) {
            // Since the time reached 0, we reached the next animation, we must trigger it.

            // Reset the time, for the next animation
            bird.resetTimeUntilBirdAnimationSwitch();

            // Switch the image to the next image in the array
            birdImage.setImage(birdImages[bird.nextAnimation()]);

            // Change the bird image angle based on the speed its flying/falling.
            if (bird.currentFallVelocity < SPEED_WHERE_ANGLE_CHANGES)
                birdImage.setRotate(-ANGLE_CHANGE);
            else {
                birdImage.setRotate(ANGLE_CHANGE);
            }
        }
    }

    /**
     * Returns the bird image based on the bird ID.
     *
     * @param bird the bird
     * @return the bird image
     */
    private ImageView getBirdImage(Bird bird) {
        if (bird.ID == 1)
            return yellowBirdImage;
        return blueBirdImage;
    }

    /**
     * Getter for the parallel transition.
     *
     * @return the parallel transition
     */
    public ParallelTransition getParTrans() {
        return parTrans;
    }

    /**
     * Moves a node to the specified coordinates.
     *
     * @param node The node to move.
     * @param x    The x-coordinate.
     * @param y    The y-coordinate.
     */
    private void setLocation(Node node, double x, double y) {
        node.setTranslateX(x);
        node.setTranslateY(y);
    }

    /**
     * Resizes an image to the specified size.
     *
     * @param imageView The image view to resize.
     * @param size      The size to set.
     */
    private void setImageSize(ImageView imageView, double size) {
        imageView.setFitWidth(size);
        imageView.setPreserveRatio(true);
    }

    /**
     * Sets the width of an image view.
     *
     * @param imageView The image view to set the width of.
     * @param size      The size to set.
     */
    private void setWidth(ImageView imageView, double size) {
        imageView.setFitWidth(size);
    }

    /**
     * Getter for the pipe images.
     *
     * @return the pipe images
     */
    public ImageView[][] getPipeImages() {
        return pipeImages;
    }

    /**
     * Getter for the powerUpImageView.
     *
     * @return the powerUpImageView
     */
    public ImageView getPowerUpImageView() {
        return powerUpImageView;
    }

    public ImageView getPowerUpTrackerImageView() {
        return powerUpTrackerImageView;
    }
}