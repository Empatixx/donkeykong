package cz.krokviak.donkeykong.objects.player;

import cz.krokviak.donkeykong.drawable.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerLifes implements Drawable {
    public static final int TOTAL_LIFES = 3;
    private int lifes;

    public PlayerLifes() {
        this.lifes =TOTAL_LIFES;
    }

    public void addLife() {
        lifes++;
    }

    public void removeLife() {
        lifes--;
    }

    public boolean isLastLife() {
        return lifes == 1;
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        for (int i = 0; i < lifes; i++) {
            gc.setFill(Color.WHITE);
            gc.fillRect(700 + i * 20, 50, 10, 10);
        }

    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public int getLifes() {
        return lifes;
    }
}
