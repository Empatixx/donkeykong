package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;

public interface Enemy extends AABB {
    int deathScore();

    void kill();
}
