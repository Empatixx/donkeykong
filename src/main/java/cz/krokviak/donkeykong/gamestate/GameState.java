package cz.krokviak.donkeykong.gamestate;

import javafx.scene.canvas.GraphicsContext;

public interface GameState {
    void update(final float dt);
    void draw();
    void init();
}
