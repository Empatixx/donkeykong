package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.drawable.Drawable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ladder implements Drawable, AABB {
    private static final int WIDTH = 32;
    private final int height;
    private Point2D position;
    public Ladder(final int height) {
        this.height = height;
        this.position = new Point2D(0, 0);
    }
    @Override
    public void drawInternal(GraphicsContext gc) {
        gc.setStroke(Color.CYAN); // Nastavení barvy čar

        // Nastavení tloušťky čar
        gc.setLineWidth(3);

        // Nakreslení stran žebříku
        double leftSide = position.getX();
        double rightSide = position.getX() + WIDTH;
        double top = position.getY();
        double bottom = position.getY() + height;

        gc.strokeLine(leftSide, top, leftSide, bottom); // Levá strana žebříku
        gc.strokeLine(rightSide, top, rightSide, bottom); // Pravá strana žebříku

        // Dynamický výpočet počtu příček na základě výšky
        double minSpacing = 12.5; // Minimální mezera mezi příčkami
        int numberOfRungs = Math.max(1, (int) ((height - minSpacing) / minSpacing)); // Výpočet počtu příček

        double spacing = height / (numberOfRungs + 1f); // Vzdálenost mezi příčkami

        for (int i = 0; i < numberOfRungs; i++) {
            double y = top + spacing * (i + 1);
            gc.strokeLine(leftSide, y, rightSide, y); // Příčka
        }
    }

    public void setPosition(final float x, final float y) {
        this.position = new Point2D(x, y);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), WIDTH, height);
    }

    @Override
    public void onCollision(AABB other) {

    }

    public Point2D getUpPosition() {
        return new Point2D(position.getX(),
                position.getY() - Player.HEIGHT*Player.SCALE);
    }
}
