package cz.krokviak.donkeykong.objects.barrels;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.collision.RectangleUtils;
import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.drawable.Updatable;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public interface Barrel extends Drawable, AABB, Updatable {
    boolean shouldRemove();
    void setPosition(final double x,final double y);
    Point2D getPosition();
    int getWidth();
    int getHeight();
}
