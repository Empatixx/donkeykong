package cz.krokviak.donkeykong.main;

import cz.krokviak.donkeykong.gamestate.GameStateManager;
import cz.krokviak.donkeykong.input.InputHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class Game {
    private final AnimationTimer gameLoop;
    private final GameStateManager gsm;
    private long lastUpdate = 0;
    public Game(final Pane pane,
                final InputHandler inputHandler) {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    final float dt = (now - lastUpdate) / 1_000_000_000f;
                    update(dt);
                }
                lastUpdate = now;
                draw();
            }
        };
        gsm = new GameStateManager(inputHandler, pane);
    }

    public void start() {
        gameLoop.start();
    }
    private void update(final float deltaTime) {
        gsm.update(deltaTime);
    }

    private void draw() {
        gsm.draw();
    }
}
