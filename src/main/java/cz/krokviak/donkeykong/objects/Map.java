package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Platform> platforms;
    private Barrels barrels;

    public void draw(final GraphicsContext gc) {
        platforms.forEach(platform -> platform.draw(gc));
        barrels.draw(gc);
    }


    public void load() {
        platforms = new ArrayList<>();
        // donkeykong platforms style level 1
        int riseY = 0;
        // first floor
        for (int i = 0; i < 7; i++) {
            platforms.add(new Platform(i * Platform.WIDTH, DonkeyKongApplication.HEIGHT / Platform.SCALE - Platform.HEIGHT));
        }
        for (int i = 0; i < 10; i++) {
            platforms.add(new Platform((i + 7) * Platform.WIDTH, DonkeyKongApplication.HEIGHT / Platform.SCALE - Platform.HEIGHT - riseY));
            riseY += 1;
        }

        riseY += Platform.HEIGHT * 3;

        // second floor
        for (int i = 1; i < 18; i++) {
            platforms.add(new Platform(DonkeyKongApplication.WIDTH / Platform.SCALE - Platform.WIDTH - i * Platform.WIDTH, DonkeyKongApplication.HEIGHT / Platform.SCALE - Platform.HEIGHT - riseY));
            riseY += 1;
        }

        riseY += Platform.HEIGHT * 3;

        // third floor
        for (int i = 1; i < 19; i++) {
            platforms.add(new Platform(i * 16, DonkeyKongApplication.HEIGHT / Platform.SCALE - Platform.HEIGHT - riseY));
            riseY += 1;
        }

        riseY += Platform.HEIGHT * 3;

        // fourth floor
        for (int i = 1; i < 19; i++) {
            platforms.add(new Platform(DonkeyKongApplication.WIDTH / Platform.SCALE - Platform.WIDTH - i * Platform.WIDTH, DonkeyKongApplication.HEIGHT / Platform.SCALE - Platform.HEIGHT - riseY));
            riseY += 1;
        }

        riseY += Platform.HEIGHT * 3;

        // fifth floor
        for (int i = 1; i < 19; i++) {
            platforms.add(new Platform(i * 16, DonkeyKongApplication.HEIGHT / Platform.SCALE - Platform.HEIGHT - riseY));
            riseY += 1;
        }

        riseY += Platform.HEIGHT * 3;


        // sixth floor same as first but from right rising to left
        for (int i = 1; i < 10; i++) {
            platforms.add(new Platform(DonkeyKongApplication.WIDTH / Platform.SCALE - Platform.WIDTH - i * Platform.WIDTH, DonkeyKongApplication.HEIGHT / Platform.SCALE - Platform.HEIGHT - riseY));
            riseY += 1;
        }
        for (int i = 0; i < 7; i++) {
            platforms.add(new Platform(i * Platform.WIDTH, DonkeyKongApplication.HEIGHT / Platform.SCALE - Platform.HEIGHT - riseY));
        }


        riseY += Platform.HEIGHT * 3;

        // princess
        for (int i = 4; i < 7; i++) {
            platforms.add(new Platform(i * Platform.WIDTH, DonkeyKongApplication.HEIGHT / Platform.SCALE - Platform.HEIGHT - riseY));
        }

        barrels = new Barrels();
        barrels.setPosition(5,30);
    }

    public List<AABB> getAABBs() {
        return List.copyOf(platforms);
    }
}
