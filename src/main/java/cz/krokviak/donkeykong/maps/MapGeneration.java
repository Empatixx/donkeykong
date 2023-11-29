package cz.krokviak.donkeykong.maps;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.items.Item;
import cz.krokviak.donkeykong.objects.Player;

import java.util.List;

public record MapGeneration(
        Player player,
        List<Drawable> drawables,
        List<AABB> collisions,
        List<Item> items
){
}
