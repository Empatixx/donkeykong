package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.drawable.Updatable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class CakeParent implements Drawable, Updatable {
    private final List<CakeEnemy> cakes;
    private final CollisionService collisionService;
    public CakeParent(final CollisionService collisionService){
        cakes = new ArrayList<>();
        this.collisionService = collisionService;
    }

    public void addCake(final CakeEnemy cake){
        cakes.add(cake);
        collisionService.addAABB(cake);
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        cakes.forEach(cake -> cake.draw(gc));
    }

    @Override
    public void update(float dt) {
        cakes.stream()
                .filter(CakeEnemy::shouldRemove)
                .forEach(collisionService::removeAABB);
        cakes.removeIf(CakeEnemy::shouldRemove);
    }
}
