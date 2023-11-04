package cz.krokviak.donkeykong.gamestate;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MenuState extends GameStateSupport implements GameState {
    private final Button startButton;
    private final Button exitButton;

    public MenuState(final GameStateManager gsm,
                     final Pane pane) {
        super(gsm, pane);

        startButton = new Button("Start");
        startButton.setPrefSize(100, 50);
        startButton.setLayoutX(350);
        startButton.setLayoutY(250);
        startButton.setOnAction(event -> gsm.setState(GameStateType.IN_GAME));

        exitButton = new Button("Exit");
        exitButton.setPrefSize(100, 50);
        exitButton.setLayoutX(350);
        exitButton.setLayoutY(350);
        exitButton.setOnAction(event -> System.exit(0));
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void draw() {

    }

    @Override
    public void init() {
        root.getChildren().clear();
        root.getChildren().addAll(startButton, exitButton);
    }

}
