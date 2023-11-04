package cz.krokviak.donkeykong.gamestate;

import cz.krokviak.donkeykong.input.InputHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.util.Map;

public class GameStateManager implements GameState {
    private final Map<GameStateType, GameState> states;
    private GameState currentState;

    public GameStateManager(final InputHandler inputHandler,
                            final Pane pane) {
        states = Map.of(
                GameStateType.IN_GAME, new InGameState(this, inputHandler, pane),
                GameStateType.MENU, new MenuState(this, pane),
                GameStateType.GAME_OVER, new GameOverState()
        );
        setState(GameStateType.MENU);
    }

    public void setState(final GameStateType stateType){
        currentState = states.get(stateType);
        currentState.init();
    }

    @Override
    public void update(float dt) {
        currentState.update(dt);
    }

    @Override
    public void draw() {
        currentState.draw();
    }
    @Override
    public void init() {
        currentState.init();
    }
}
