package cz.krokviak.donkeykong.items;

import cz.krokviak.donkeykong.collision.CollisionService;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class ItemService {
    private final List<Item> items;
    private final CollisionService collisionService;
    public ItemService(final List<Item> items,
                       final CollisionService collisionService) {
        this.items = items;
        this.collisionService = collisionService;
        collisionService.addAABB(List.copyOf(items));
    }
    public void update(final float dt) {
        for (final Item item : items) {
            if (item.shouldRemove()) {
                collisionService.removeAABB(item);
            }
        }
        items.removeIf(Item::shouldRemove);
    }
}
