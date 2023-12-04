package cz.krokviak.donkeykong.objects.ladder;

import cz.krokviak.donkeykong.collision.AABB;
import javafx.geometry.Point2D;

public interface Ladder extends AABB {
    Point2D getDownPosition();
    Point2D getUpPosition();
}
