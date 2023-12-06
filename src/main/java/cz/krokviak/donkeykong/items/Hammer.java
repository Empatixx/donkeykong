package cz.krokviak.donkeykong.items;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.objects.player.Player;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Hammer extends BaseItem {
    public Hammer(){
        super("hammer.png", 2);
    }
}
