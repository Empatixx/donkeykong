package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.drawable.Updatable;
import cz.krokviak.donkeykong.utils.ScheduledTask;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class Monkey implements Drawable, Updatable {
    private final static int HEIGHT = 64;
    private final static int WIDTH = 64;
    private final static int SCALE = 3;
    private final static int BARREL_SPAWN_TIME = 5;
    private final AnimatedSprite animatedSprite;
    private Point2D position;
    private boolean rightFacing = false;
    private CollisionService collisionService;
    private ScheduledTask takeBarrelTask;
    private ScheduledTask spawnBarrelTask;
    private List<Barrel> barrels;

    public Monkey(final CollisionService collisionService) {
        this.animatedSprite = AnimatedSprite.builder()
                .setFilePath("monkey.png")
                .positionSupplier(() -> position)
                .isFlippedSupplier(() -> rightFacing)
                .addAnimationSequence("idle", 1)
                .addAnimationSequence("side", 1)
                .setFrameHeight(HEIGHT)
                .setFrameWidth(WIDTH)
                .setFrameTime(0.1f)
                .scale(3)
                .build();
        animatedSprite.setCurrentAnimation("idle");
        barrels = new ArrayList<>();
        takeBarrelTask = new ScheduledTask(() -> {
            rightFacing = false;
            animatedSprite.setCurrentAnimation("side");
            animatedSprite.setFrameTimeCurrentAnimation(0.25f);
        }, BARREL_SPAWN_TIME - 0.5f);
        spawnBarrelTask = new ScheduledTask(() -> {
            rightFacing = true;
            animatedSprite.setCurrentAnimation("side");
            animatedSprite.setFrameTimeCurrentAnimation(0.25f);
            final Barrel barrel = new Barrel(6);
            barrel.setPosition(position.getX() + WIDTH * SCALE - 30, position.getY() + HEIGHT * SCALE - 50);
            barrel.setVelocity(100, 0);
            barrels.add(barrel);
            collisionService.addAABB(barrel);
        }, BARREL_SPAWN_TIME);
        this.collisionService = collisionService;
    }

    public void setPosition(final float x, final float y) {
        this.position = new Point2D(x, y);
    }

    @Override
    public void update(float dt) {
        if (animatedSprite.getCurrentAnimation().equals("side") && animatedSprite.hasFinishedAnimation()) {
            animatedSprite.setCurrentAnimation("idle");
            animatedSprite.setFrameTimeCurrentAnimation(0.1f);
        }
        animatedSprite.update(dt);
        takeBarrelTask.update(dt);
        spawnBarrelTask.update(dt);
        barrels.forEach(barrel -> barrel.update(dt));
        for (Barrel barrel : barrels) {
            if (barrel.shouldRemove()) {
                collisionService.removeAABB(barrel);
            }
        }
        barrels.removeIf(Barrel::shouldRemove);
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        animatedSprite.draw(gc);
        barrels.forEach(barrel -> barrel.draw(gc));
    }
}
