package cz.krokviak.donkeykong.input;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KeyboardHandler implements InputHandler, EventHandler<KeyEvent> {
    private final Set<GameAction> actions = new HashSet<>();
    private final static Map<KeyCode, GameAction> ACTIONS = Map.of(
            KeyCode.UP, GameAction.MOVE_UP,
            KeyCode.DOWN, GameAction.MOVE_DOWN,
            KeyCode.LEFT, GameAction.MOVE_LEFT,
            KeyCode.RIGHT, GameAction.MOVE_RIGHT
    );
    public void pressed(final KeyCode keyCode){
        actions.add(ACTIONS.get(keyCode));
    }
    public void released(final KeyCode keyCode){
        actions.remove(ACTIONS.get(keyCode));
    }
    @Override
    public boolean isActive(final GameAction action) {
        return actions.contains(action);
    }

    @Override
    public void setActive(GameAction action, boolean active) {
        if (active){
            actions.add(action);
        } else {
            actions.remove(action);
        }
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
            pressed(keyEvent.getCode());
        } else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED){
            released(keyEvent.getCode());
        }
    }
}
