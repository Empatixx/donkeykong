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
import cz.krokviak.donkeykong.objects.ladder.DefaultLadder;
import cz.krokviak.donkeykong.objects.player.Player;
import cz.krokviak.donkeykong.objects.princess.NextLevelBox;
import cz.krokviak.donkeykong.objects.princess.Princess;

import java.util.ArrayList;
import java.util.List;

public class LevelOneGenerator implements LevelGenerator {
    private final InputHandler inputHandler;
    private final CollisionService collisionService;

    public LevelOneGenerator(final CollisionService collisionService,
                             final InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        this.collisionService = collisionService;
    }

    @Override
    public MapGeneration generate() {
        final Barrels barrels = new Barrels();
        barrels.setPosition(10, 95);

        final List<Platform> platforms = createPlatforms();
        final List<Item> hammers = createHammers();
        final List<DefaultLadder> ladders = createLadders();

        final Player player = new Player(inputHandler);
        player.setPosition(50, 650);

        final Monkey monkey = new Monkey(collisionService);
        monkey.setPosition(30, 15);

        final Princess princess = new Princess();
        princess.setPosition(180, 10);
        final Background background = new Background();

        final Oil oil = new Oil(collisionService);
        oil.setPosition(32, 712);

        final NextLevelBox nextLevelBox = new NextLevelBox(300,0, 100, 150);

        final List<Drawable> drawables = new ArrayList<>(100);
        drawables.add(background);
        drawables.add(player);
        drawables.add(monkey);
        drawables.add(princess);
        drawables.add(barrels);
        drawables.addAll(ladders);
        drawables.addAll(platforms);
        drawables.addAll(hammers);
        drawables.add(oil);

        final List<Item> items = new ArrayList<>();
        items.addAll(hammers);

        final List<AABB> aabbs = new ArrayList<>();
        aabbs.add(player);
        aabbs.addAll(platforms);
        aabbs.addAll(hammers);
        aabbs.addAll(ladders);
        aabbs.add(nextLevelBox);
        aabbs.addAll(ladders.stream().map(DefaultLadder::getSubAABB).toList());


        return new MapGeneration(player, drawables, aabbs, items);
    }

    private List<DefaultLadder> createLadders() {
        final List<DefaultLadder> ladders = new ArrayList<>();
        final DefaultLadder ladder1 = new DefaultLadder(90);
        ladder1.setPosition(630, 185);
        ladders.add(ladder1);

        final DefaultLadder ladder2 = new DefaultLadder(85);
        ladder2.setPosition(150, 315);
        ladders.add(ladder2);

        final DefaultLadder ladder3 = new DefaultLadder(115);
        ladder3.setPosition(350, 295);
        ladders.add(ladder3);

        final DefaultLadder ladder4 = new DefaultLadder(110);
        ladder4.setPosition(500, 425);
        ladders.add(ladder4);

        final DefaultLadder ladder5 = new DefaultLadder(85);
        ladder5.setPosition(150, 555);
        ladders.add(ladder5);

        final DefaultLadder ladder6 = new DefaultLadder(120);
        ladder6.setPosition(430, 540);
        ladders.add(ladder6);


        final DefaultLadder ladder7 = new DefaultLadder(85);
        ladder7.setPosition(630, 675);
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
