package cz.krokviak.donkeykong.items;

import cz.krokviak.donkeykong.collision.CollisionService;

import java.util.List;

public class ItemService {
    private final List<Item> items;
    private final CollisionService collisionService;
    public ItemService(final List<Item> items,
                       final CollisionService collisionService) {
        this.items = items;
        this.collisionService = collisionService;
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
