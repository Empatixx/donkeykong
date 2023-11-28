package cz.krokviak.donkeykong.maps;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.items.Item;

import java.util.List;

public record StaticGeneration(
        List<Drawable> staticDrawables,
        List<AABB> staticCollisions,
        List<Item> items
){
}
