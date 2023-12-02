package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class FlameBarrel implements Barrel {
    private final DefaultBarrel barrel;
    public FlameBarrel(){
        barrel = new DefaultBarrel(Integer.MAX_VALUE);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return barrel.getBoundingBox();
    }

    @Override
    public void onCollision(AABB other) {
        barrel.onCollision(other);
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        barrel.drawInternal(gc);
    }

    @Override
    public void update(float dt) {
        barrel.update(dt);
    }

    @Override
    public boolean shouldRemove() {
        return barrel.shouldRemove();
    }

    @Override
    public void setPosition(double x, double y) {
        barrel.setPosition(x,y);
    }

    @Override
    public Point2D getPosition() {
        return barrel.getPosition();
    }

    @Override
    public int getWidth() {
        return barrel.getWidth();
    }

    @Override
    public int getHeight() {
        return barrel.getHeight();
    }

    public void setVelocity(int x, int y) {
        barrel.setVelocity(x,y);
    }
}
