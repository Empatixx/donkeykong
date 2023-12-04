package cz.krokviak.donkeykong.objects.ladder;

import cz.krokviak.donkeykong.collision.AABB;
import javafx.geometry.Point2D;

public sealed interface Ladder extends AABB  permits DefaultLadder, CompositeLadder {
    Point2D getDownPosition();
    Point2D getUpPosition();
}
