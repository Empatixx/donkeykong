package cz.krokviak.donkeykong.collision;

import java.util.ArrayList;
import java.util.List;

public class CollisionService {
    private final List<AABB> aabbs;
    public CollisionService(){
        this.aabbs = new ArrayList<>();
    }
    public void addAABB(final AABB aabb){
        aabbs.add(aabb);
    }
    public void addAABB(final List<AABB> aabb){
        aabbs.addAll(aabb);
    }
    public void update(){
        for (int i = 0; i < aabbs.size(); i++) {
            for (int j = i + 1; j < aabbs.size(); j++) {
                final AABB a = aabbs.get(i);
                final AABB b = aabbs.get(j);
                if (a.getBoundingBox().intersects(b.getBoundingBox())){
                    a.onCollision(b);
                    b.onCollision(a);
                }
            }
        }
    }
}
