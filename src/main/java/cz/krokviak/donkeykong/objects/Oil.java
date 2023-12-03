package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.drawable.Updatable;
import cz.krokviak.donkeykong.utils.ScheduledTask;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Oil implements Drawable, Updatable {
    private final static int WIDTH = 32;
    private final static int HEIGHT = 48;
    private final static int SPAWN_TIME = 5;
    private Point2D position;
    private ScheduledTask spawnFlameTask;
    private final List<FlameEnemy> flames;
    private final CollisionService collisionService;
    public Oil(final CollisionService collisionService){
        this.collisionService = collisionService;
        flames = new ArrayList<>();
        spawnFlameTask = new ScheduledTask(() -> {
            final FlameEnemy enemy = new FlameEnemy();
            enemy.setPosition((float) position.getX(), (float) position.getY());
            flames.add(enemy);
            collisionService.addAABB(enemy);
        }, SPAWN_TIME);
    }
    public void setPosition(final float x, final float y){
        this.position = new Point2D(x,y);
    }
    @Override
    public void drawInternal(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(position.getX(), position.getY(), WIDTH, HEIGHT);
        flames.forEach(flame -> flame.drawInternal(gc));
    }

    @Override
    public void update(float dt) {
        spawnFlameTask.update(dt);
        flames.forEach(flame -> flame.update(dt));
        flames.stream()
                .filter(FlameEnemy::shouldRemove)
                .forEach(collisionService::removeAABB);
        flames.removeIf(FlameEnemy::shouldRemove);
    }
}
