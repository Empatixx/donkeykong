package cz.krokviak.donkeykong.gamestate;

import javafx.scene.layout.Pane;

public abstract class GameStateSupport {
    protected final GameStateManager gsm;
    protected final Pane root;
    public GameStateSupport(final GameStateManager gsm,
                            final Pane root) {
        this.gsm = gsm;
        this.root = root;
    }
}
