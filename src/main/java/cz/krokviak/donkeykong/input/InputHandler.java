package cz.krokviak.donkeykong.input;

import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public interface InputHandler {
    boolean isActive(final GameAction action);
    void setActive(final GameAction action, final boolean active);
}
