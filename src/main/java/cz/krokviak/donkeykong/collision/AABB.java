package cz.krokviak.donkeykong.collision;

import javafx.geometry.Rectangle2D;

public interface AABB {
    Rectangle2D getBoundingBox();
    void onCollision(AABB other);
}
