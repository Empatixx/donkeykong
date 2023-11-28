package cz.krokviak.donkeykong.main;

import cz.krokviak.donkeykong.input.KeyboardHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DonkeyKongApplication extends Application {
    private final static String NAME = "Donkey Kong";
    public final static int WIDTH = 816;
    public final static int HEIGHT = 800;
    private Game game;

    @Override
    public void start(final Stage primaryStage) {
        Pane root = new Pane();
        root.setFocusTraversable(true);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        final KeyboardHandler keyboardHandler = new KeyboardHandler();

        root.setOnKeyPressed(keyboardHandler);
        root.setOnKeyReleased(keyboardHandler);

        primaryStage.setTitle(NAME);
        primaryStage.setScene(scene);
        primaryStage.show();

        game = new Game(root, keyboardHandler);

        game.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}