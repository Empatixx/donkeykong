package cz.krokviak.donkeykong.collision;

import javafx.geometry.Rectangle2D;

public class RectangleUtils {
    public static Rectangle2D intersection(final Rectangle2D a, final Rectangle2D b){
        final double x = Math.max(a.getMinX(), b.getMinX());
        final double y = Math.max(a.getMinY(), b.getMinY());
        final double w = Math.min(a.getMaxX(), b.getMaxX()) - x;
        final double h = Math.min(a.getMaxY(), b.getMaxY()) - y;
        if (w < 0 || h < 0){
            return null;
        }
        return new Rectangle2D(x, y, w, h);
    }
}
