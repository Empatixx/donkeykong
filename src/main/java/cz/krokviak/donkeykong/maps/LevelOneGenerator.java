package cz.krokviak.donkeykong.maps;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.drawable.Background;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.input.InputHandler;
import cz.krokviak.donkeykong.items.Hammer;
import cz.krokviak.donkeykong.items.Item;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import cz.krokviak.donkeykong.objects.*;

import java.util.ArrayList;
import java.util.List;

public class LevelOneGenerator implements LevelGenerator {
    private final InputHandler inputHandler;
    private final CollisionService collisionService;

    public LevelOneGenerator(final InputHandler inputHandler,
                             final CollisionService collisionService) {
        this.inputHandler = inputHandler;
        this.collisionService = collisionService;
    }

    @Override
    public MapGeneration generate() {
        final Barrels barrels = new Barrels();
        barrels.setPosition(10, 95);

        final List<Platform> platforms = createPlatforms();
        final List<Item> hammers = createHammers();
        final List<Ladder> ladders = createLadders();

        final Player player = new Player(inputHandler);
        player.setPosition(50, 550);

        final Monkey monkey = new Monkey(collisionService);
        monkey.setPosition(30, 15);

        final Princess princess = new Princess();
        princess.setPosition(180, 10);
        final Background background = new Background();

        final List<Drawable> drawables = new ArrayList<>(100);
        drawables.add(background);
        drawables.add(player);
        drawables.add(monkey);
        drawables.add(princess);
        drawables.add(barrels);
        drawables.addAll(ladders);
        drawables.addAll(platforms);
        drawables.addAll(hammers);

        final List<Item> items = new ArrayList<>();
        items.addAll(hammers);

        final List<AABB> aabbs = new ArrayList<>();
        aabbs.add(player);
        aabbs.addAll(platforms);
        aabbs.addAll(hammers);
        aabbs.addAll(ladders);


        return new MapGeneration(player, drawables, aabbs, items);
    }

    private List<Ladder> createLadders() {
       final List<Ladder> ladders = new ArrayList<>();
        final Ladder ladder1 = new Ladder(90);
        ladder1.setPosition(630, 185);
        ladders.add(ladder1);

        final Ladder ladder2 = new Ladder(90);
        ladder2.setPosition(150, 315);
        ladders.add(ladder2);

        final Ladder ladder3 = new Ladder(120);
        ladder3.setPosition(350, 295);
        ladders.add(ladder3);

        final Ladder ladder4 = new Ladder(120);
        ladder4.setPosition(500, 425);
        ladders.add(ladder4);

        final Ladder ladder5 = new Ladder(90);
        ladder5.setPosition(150, 555);
        ladders.add(ladder5);

        final Ladder ladder6 = new Ladder(120);
        ladder6.setPosition(430, 540);
        ladders.add(ladder6);


        final Ladder ladder7 = new Ladder(90);
        ladder7.setPosition(630, 685);
        ladders.add(ladder7);

        return ladders;
    }

    private List<Item> createHammers() {
        final List<Item> hammers = new ArrayList<>();
        final Hammer hammer = new Hammer();
        hammer.setPosition(50, 450);
        hammers.add(hammer);

        final Hammer hammer2 = new Hammer();
        hammer2.setPosition(200, 220);
        hammers.add(hammer2);
        return hammers;
    }

    private List<Platform> createPlatforms() {
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
        return platforms;
    }
}
