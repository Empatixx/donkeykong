package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import javafx.geometry.Point2D;

public interface ClimbEntity extends AABB {
    Point2D getPosition();
    void setPosition(final float x, final float y);
    int getWidth();
    int getHeight();
}
