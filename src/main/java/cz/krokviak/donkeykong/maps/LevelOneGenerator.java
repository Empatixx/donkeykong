package cz.krokviak.donkeykong.maps;

import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.items.Item;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import cz.krokviak.donkeykong.objects.Barrels;
import cz.krokviak.donkeykong.objects.Hammer;
import cz.krokviak.donkeykong.objects.Platform;

import java.util.ArrayList;
import java.util.List;

public class LevelOneGenerator implements LevelGenerator {
    @Override
    public StaticGeneration generate() {
        final List<Platform> platforms = new ArrayList<>();
        // donkeykong platforms style level 1
        int riseY = 0;
        // first floor
        for (int i = 0; i < 7; i++) {
            platforms.add(new Platform(i * Platform.WIDTH, DonkeyKongApplication.HEIGHT - Platform.HEIGHT));
        }
        for (int i = 0; i < 10; i++) {
            platforms.add(new Platform((i + 7) * Platform.WIDTH, DonkeyKongApplication.HEIGHT - Platform.HEIGHT - riseY));
            riseY += 3;
        }

        riseY += Platform.HEIGHT * 3;

        // second floor
        for (int i = 2; i < 18; i++) {
            platforms.add(new Platform(DonkeyKongApplication.WIDTH - Platform.WIDTH - i * Platform.WIDTH, DonkeyKongApplication.HEIGHT - Platform.HEIGHT - riseY));
            riseY += 3;
        }

        riseY += Platform.HEIGHT * 3;

        // third floor
        for (int i = 2; i < 19; i++) {
            platforms.add(new Platform(i * Platform.WIDTH, DonkeyKongApplication.HEIGHT - Platform.HEIGHT - riseY));
            riseY += 3;
        }

        riseY += Platform.HEIGHT * 3;

        // fourth floor
        for (int i = 2; i < 19; i++) {
            platforms.add(new Platform(DonkeyKongApplication.WIDTH - Platform.WIDTH - i * Platform.WIDTH, DonkeyKongApplication.HEIGHT - Platform.HEIGHT - riseY));
            riseY += 3;
        }

        riseY += Platform.HEIGHT * 3;

        // fifth floor
        for (int i = 2; i < 19; i++) {
            platforms.add(new Platform(i * Platform.WIDTH, DonkeyKongApplication.HEIGHT - Platform.HEIGHT - riseY));
            riseY += 3;
        }

        riseY += Platform.HEIGHT * 3;


        // sixth floor same as first but from right rising to left
        for (int i = 2; i < 10; i++) {
            platforms.add(new Platform(DonkeyKongApplication.WIDTH - Platform.WIDTH - i * Platform.WIDTH, DonkeyKongApplication.HEIGHT - Platform.HEIGHT - riseY));
            riseY += 3;
        }
        for (int i = 0; i < 7; i++) {
            platforms.add(new Platform(i * Platform.WIDTH, DonkeyKongApplication.HEIGHT - Platform.HEIGHT - riseY));
        }


        riseY += Platform.HEIGHT * 3;

        // princess
        for (int i = 4; i < 7; i++) {
            platforms.add(new Platform(i * Platform.WIDTH, DonkeyKongApplication.HEIGHT - Platform.HEIGHT - riseY));
        }

        final Barrels barrels = new Barrels();
        barrels.setPosition(10, 95);

        final Hammer hammer = new Hammer();
        hammer.setPosition(50, 450);

        final Hammer hammer2 = new Hammer();
        hammer2.setPosition(200, 220);

        final ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.addAll(platforms);
        drawables.add(barrels);

        final ArrayList<Item> items = new ArrayList<>();
        items.add(hammer);
        items.add(hammer2);

        return new StaticGeneration(drawables, List.copyOf(platforms), items);
    }
}
