package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.hud.Score;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CakeEnemy implements Enemy, Drawable {
    private final static int WIDTH = 16;
    private final static int HEIGHT = 9;
    private final static int SCALE = 3;
    private Point2D position;
    private Image image;
    private boolean dead = false;
    public CakeEnemy(){
        this.position = new Point2D(0,0);
        this.image = new Image("cake.png");
    }
    public void setPosition(final double x, final double y){
        this.position = new Point2D(x, y);
    }
    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), WIDTH * SCALE, HEIGHT * SCALE);
    }

    @Override
    public void onCollision(AABB other) {

    }

    @Override
    public int deathScore() {
        return Score.LOW_SCORE;
    }

    @Override
    public void kill() {
        dead = true;
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        if (dead){
            return;
        }
        gc.drawImage(image, position.getX(), position.getY(), WIDTH * SCALE, HEIGHT * SCALE);
    }

    public boolean shouldRemove() {
        return dead;
    }
}
