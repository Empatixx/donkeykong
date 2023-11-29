package cz.krokviak.donkeykong.items;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.drawable.Updatable;

public interface Item extends Drawable, AABB, Updatable {
    boolean shouldRemove();
}
