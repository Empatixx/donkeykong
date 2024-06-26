package cz.krokviak.donkeykong.objects.ladder;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.drawable.Drawable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class DefaultLadder implements Drawable, AABB, Ladder {
    private final static int OFFSET = 20;
    public static final int WIDTH = 32;
    private final int height;
    private Point2D position;
    private CompositeLadder ladderBarrelBox;
    public DefaultLadder(final int height) {
        this.height = height;
        this.position = new Point2D(0, 0);
        ladderBarrelBox = new CompositeLadder(this);
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

    @Override
    public Point2D getUpPosition() {
        return new Point2D(position.getX(), position.getY()).add(WIDTH/2f,-OFFSET);
    }
    @Override
    public Point2D getDownPosition() {
        return new Point2D(position.getX(), position.getY()).add(WIDTH/2f,height - OFFSET);
    }

    public Point2D getPosition() {
        return position;
    }

    public AABB getSubAABB() {
        return ladderBarrelBox;
    }
}
