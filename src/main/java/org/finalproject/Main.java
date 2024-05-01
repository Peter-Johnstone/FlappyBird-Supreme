/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2024
 * Instructor: Prof. Lily Romano / Prof. Joshua Stough
 *
 * Name: Peter Johnstone
 * Section: YOUR SECTION
 * Date: 4/6/2024
 * Time: 5:58 PM
 *
 * Project: csci205_final_project
 * Package: org.finalproject
 * Class: main
 *
 * Description:
 *
 * ****************************************
 */
package org.finalproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The Main class is the entry point of the application. It launches the JavaFX application.
 */
public class Main extends Application {


    /**
     * Start method called when the JavaFX application is launched. It sets up the game scene
     * and initializes the game controller.
     *
     * @param primaryStage The primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, Config.BOARD_WIDTH, Config.BOARD_HEIGHT);
        primaryStage.setScene(scene);
        Image icon = new Image("yellowbird-2.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Flappy Bird");
        primaryStage.show();
        new GameController(scene);
    }
    /**
     * The entry point of the application. It launches the JavaFX application.
     *
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
