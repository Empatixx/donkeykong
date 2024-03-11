package cz.krokviak.donkeykong.main;

import cz.krokviak.donkeykong.gamestate.GameStateManager;
import cz.krokviak.donkeykong.input.InputHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Game {
    private static final Logger LOGGER = LogManager.getLogger(Game.class);
    private final AnimationTimer gameLoop;
    private final GameStateManager gsm;
    private long lastUpdate = 0;
    public Game(final Pane pane,
                final InputHandler inputHandler) {
        LOGGER.info("Creating game");
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    LOGGER.debug("Updating game");
                    final float dt = (now - lastUpdate) / 1_000_000_000f;
                    update(dt);
                }
                lastUpdate = now;
                LOGGER.debug("Drawing game");
                draw();
            }
        };
        try {
            gsm = new GameStateManager(inputHandler, pane);
        } catch (Exception e){
            LOGGER.error("Error creating game", e);
            throw e;
        }
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
