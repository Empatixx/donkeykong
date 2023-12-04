package cz.krokviak.donkeykong.items;

import cz.krokviak.donkeykong.collision.CollisionService;

import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private final List<Item> items;
    private final CollisionService collisionService;
    public ItemService(final CollisionService collisionService) {
        this.items = new ArrayList<>();
        this.collisionService = collisionService;
    }
    public void addItems(final List<Item> items) {
        this.items.addAll(items);
    }
    public void update(final float dt) {
        for (final Item item : items) {
            if (item.shouldRemove()) {
                collisionService.removeAABB(item);
            }
        }
        items.removeIf(Item::shouldRemove);
    }

    public void clear() {
        items.clear();
    }
}
