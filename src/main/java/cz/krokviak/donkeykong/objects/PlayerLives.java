package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.drawable.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerLives implements Drawable {
    private int lives;

    public PlayerLives() {
        this.lives = 3;
    }

    public void addLife() {
        lives++;
    }

    public void removeLife() {
        lives--;
    }

    public boolean canRespawn() {
        return lives > 0;
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        for (int i = 0; i < lives; i++) {
            gc.setFill(Color.WHITE);
            gc.fillRect(700 + i * 20, 50, 10, 10);
        }

    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }
}
