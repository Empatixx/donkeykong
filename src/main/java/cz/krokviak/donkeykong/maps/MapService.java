package cz.krokviak.donkeykong.maps;

import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.drawable.Updatable;
import javafx.scene.canvas.GraphicsContext;

import java.io.FileInputStream;
import java.util.List;

public class MapService {
    private final List<Updatable> updatableList;
    private final List<Drawable> drawableList;

    public MapService(final MapGeneration mapGeneration,
                      final CollisionService collisionService) {
        this.updatableList = List.copyOf(mapGeneration
                .drawables()
                .stream()
                .filter(Updatable.class::isInstance)
                .map(Updatable.class::cast)
                .toList());
        this.drawableList = List.copyOf(mapGeneration.drawables());
        collisionService.addAABB(mapGeneration.collisions());
    }

    public void draw(final GraphicsContext gc) {
        for (final Drawable drawable : drawableList) {
            drawable.draw(gc);
        }
    }

    public void update(final float dt) {
        for (final Updatable updatable : updatableList) {
            updatable.update(dt);
        }
    }
}
