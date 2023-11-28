package cz.krokviak.donkeykong.items;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.drawable.Drawable;

public interface Item extends Drawable, AABB {
    boolean shouldRemove();
    void update(final float dt);

}
