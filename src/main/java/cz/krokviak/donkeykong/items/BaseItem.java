package cz.krokviak.donkeykong.items;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.objects.player.Player;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class BaseItem implements Item{
    protected boolean isPicked = false;
    protected final Image image;
    private final int width;
    private final int height;
    private Point2D position;
    protected BaseItem(final String imagePath, final int scale){
        this.image = new Image(imagePath);
        this.width = (int) image.getWidth() * scale;
        this.height = (int) image.getHeight() * scale;
        position = new Point2D(0,0);
    }

    public void setPosition(double x, double y) {
        this.position = new Point2D(x, y);
    }

    public Point2D getPosition() {
        return position;
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        if (isPicked){
            return;
        }
        gc.drawImage(image, getPosition().getX(), getPosition().getY(), width, height);
    }

    @Override
    public boolean shouldRemove() {
        return isPicked;
    }
    protected void pickUp(){
        isPicked = true;
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(getPosition().getX(), getPosition().getY(), width, height);
    }

    @Override
    public void onCollision(AABB other) {
        if (other instanceof Player){
            pickUp();
        }
    }
}
