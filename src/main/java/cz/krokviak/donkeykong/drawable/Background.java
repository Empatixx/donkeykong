package cz.krokviak.donkeykong.drawable;

import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Background implements Drawable{
    @Override
    public void drawInternal(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, DonkeyKongApplication.WIDTH, DonkeyKongApplication.HEIGHT);
    }
}
