package cz.krokviak.donkeykong.maps;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.drawable.Background;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.input.InputHandler;
import cz.krokviak.donkeykong.items.*;
import cz.krokviak.donkeykong.objects.Box;
import cz.krokviak.donkeykong.objects.Monkey;
import cz.krokviak.donkeykong.objects.Oil;
import cz.krokviak.donkeykong.objects.Platform;
import cz.krokviak.donkeykong.objects.ladder.DefaultLadder;
import cz.krokviak.donkeykong.objects.ladder.Ladder;
import cz.krokviak.donkeykong.objects.player.Player;
import cz.krokviak.donkeykong.objects.princess.NextLevelBox;
import cz.krokviak.donkeykong.objects.princess.Princess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LevelTwoGenerator implements LevelGenerator {
    private static String PLATFORM_TEXTURE = "platform2.png";
    private static String SECOND_PLATFORM_TEXTURE = "platform3.png";
    private final CollisionService collisionService;
    private final InputHandler inputHandler;

    public LevelTwoGenerator(final CollisionService collisionService,
                             final InputHandler inputHandler) {
        this.collisionService = collisionService;
        this.inputHandler = inputHandler;
    }

    @Override
    public MapGeneration generate() {
        final List<Platform> platforms = generatePlatforms();
        final Player player = generatePlayer();
        final Background background = new Background();
        final List<DefaultLadder> ladders = createLadders();

        final List<Hammer> hammers = createHammers();
        final PrincessBag princessBag = createPrincessBag();
        final PrincessHat princessHat = createPrincessHat();
        final Umbrella umbrella = createUmbrella();

        final Princess princess = createPrincess();
        final Oil oil = createOil();
        final Box box = createBox();
        final Monkey monkey = createMonkey();
        final NextLevelBox nextLevelBox = new NextLevelBox(300,0, 200, 150);

        final List<Drawable> drawables = new ArrayList<>(100);
        drawables.add(background);
        drawables.addAll(ladders);
        drawables.addAll(platforms);
        drawables.add(player);
        drawables.addAll(hammers);
        drawables.add(princess);
        drawables.add(oil);
        drawables.add(box);
        drawables.add(monkey);
        drawables.add(princessBag);
        drawables.add(princessHat);
        drawables.add(umbrella);

        final List<AABB> collisions = new ArrayList<>(100);
        collisions.addAll(platforms);
        collisions.add(player);
        collisions.addAll(ladders);
        collisions.addAll(hammers);
        collisions.add(box);
        collisions.add(nextLevelBox);
        collisions.addAll(ladders.stream().map(DefaultLadder::getSubAABB).toList());
        collisions.add(princessBag);
        collisions.add(princessHat);
        collisions.add(umbrella);

        final List<Item> items = new ArrayList<>(10);
        items.addAll(hammers);
        items.add(princessBag);
        items.add(princessHat);
        items.add(umbrella);

        return new MapGeneration(player, drawables, collisions, items);
    }

    private PrincessBag createPrincessBag() {
        final PrincessBag princessBag = new PrincessBag();
        princessBag.setPosition(400, 755);
        return princessBag;
    }
    private PrincessHat createPrincessHat() {
        final PrincessHat princessHat = new PrincessHat();
        princessHat.setPosition(250, 490);
        return princessHat;
    }
    private Umbrella createUmbrella() {
        final Umbrella umbrella = new Umbrella();
        umbrella.setPosition(730, 490);
        return umbrella;
    }

    private Box createBox() {
        final Box box = new Box();
        box.setPosition(360, 450);
        return box;
    }

    private Oil createOil() {
        final Oil oil = new Oil(collisionService, true);
        oil.setPosition(390, 385);
        return oil;
    }

    private Princess createPrincess() {
        final Princess princess = new Princess();
        princess.setPosition(320, 70);
        return princess;
    }

    private List<Platform> generatePlatforms() {
        final List<Platform> platforms = new ArrayList<>();
        // first floor - princess
        for (int i = 7; i < 10; i++) {
            final Platform platform = new Platform(i * Platform.WIDTH, 150, SECOND_PLATFORM_TEXTURE);
            platforms.add(platform);
        }
        // second floor - monkey
        for (int i = 1; i < 16; i++) {
            final Platform platform = new Platform(i * Platform.WIDTH, 275, PLATFORM_TEXTURE);
            platforms.add(platform);
        }
        // third floor - oil - left
        for (int i = 0; i < 8;i++){
            final Platform platform = new Platform(i * Platform.WIDTH, 400, PLATFORM_TEXTURE);
            platforms.add(platform);
        }
        // third floor - oil - right
        for (int i = 9; i < 17;i++){
            final Platform platform = new Platform(i * Platform.WIDTH, 400, PLATFORM_TEXTURE);
            platforms.add(platform);
        }
        // fourth floor - flames - left
        for (int i = 1; i < 4; i++) {
            final Platform platform = new Platform(i * Platform.WIDTH, 525, SECOND_PLATFORM_TEXTURE);
            platforms.add(platform);
        }
        // fourth floor - flames - center
        for (int i = 5; i < 12; i++) {
            final Platform platform = new Platform(i * Platform.WIDTH, 525, SECOND_PLATFORM_TEXTURE);
            platforms.add(platform);
        }
        // fourth floor - flames - right
        for (int i = 13; i < 16; i++) {
            final Platform platform = new Platform(i * Platform.WIDTH, 525, SECOND_PLATFORM_TEXTURE);
            platforms.add(platform);
        }
        // fifth floor - cakes
        for (int i = 1; i < 16; i++) {
            final Platform platform = new Platform(i * Platform.WIDTH, 650, PLATFORM_TEXTURE);
            platforms.add(platform);
        }
        // sixth floor - player
        for (int i = 0; i < 17; i++) {
            final Platform platform = new Platform(i * Platform.WIDTH, 775, SECOND_PLATFORM_TEXTURE);
            platforms.add(platform);
        }
        return platforms;


    }
    public Player generatePlayer() {
        final Player player = new Player(inputHandler);
        player.setPosition(50, 650);
        return player;
    }
    public List<DefaultLadder> createLadders(){
        final List<DefaultLadder> ladders = new ArrayList<>();
        // first floor - princess
        final DefaultLadder ladder = new DefaultLadder(125);
        ladder.setPosition(445, 155);
        ladders.add(ladder);

        // second floor
        final DefaultLadder ladder0 = new DefaultLadder(125);
        ladder0.setPosition(55, 280);
        ladders.add(ladder0);

        final DefaultLadder ladder1 = new DefaultLadder(125);
        ladder1.setPosition(725, 280);
        ladders.add(ladder1);

        // third floor
        final DefaultLadder ladder2 = new DefaultLadder(125);
        ladder2.setPosition(105, 405);
        ladders.add(ladder2);

        final DefaultLadder ladder3 = new DefaultLadder(125);
        ladder3.setPosition(300, 405);
        ladders.add(ladder3);

        final DefaultLadder ladder4 = new DefaultLadder(125);
        ladder4.setPosition(485, 405);
        ladders.add(ladder4);

        final DefaultLadder ladder5 = new DefaultLadder(125);
        ladder5.setPosition(680, 405);
        ladders.add(ladder5);

        // fourth floor
        final DefaultLadder ladder6 = new DefaultLadder(125);
        ladder6.setPosition(250, 530);
        ladders.add(ladder6);

        final DefaultLadder ladder7 = new DefaultLadder(125);
        ladder7.setPosition(535, 530);
        ladders.add(ladder7);

        // fifth floor
        final DefaultLadder ladder8 = new DefaultLadder(125);
        ladder8.setPosition(105, 655);
        ladders.add(ladder8);

        final DefaultLadder ladder9 = new DefaultLadder(125);
        ladder9.setPosition(300, 655);
        ladders.add(ladder9);

        final DefaultLadder ladder10 = new DefaultLadder(125);
        ladder10.setPosition(485, 655);
        ladders.add(ladder10);

        final DefaultLadder ladder11 = new DefaultLadder(125);
        ladder11.setPosition(680, 655);
        ladders.add(ladder11);

        return ladders;
    }
    private List<Hammer> createHammers(){
        final List<Hammer> hammers = new ArrayList<>();
        final Hammer hammer = new Hammer();
        hammer.setPosition(50, 430);
        hammers.add(hammer);

        final Hammer hammer2 = new Hammer();
        hammer2.setPosition(390, 550);
        hammers.add(hammer2);

        return hammers;
    }
    private Monkey createMonkey(){
        final Monkey monkey = new Monkey(collisionService);
        monkey.disableBarrels();
        monkey.setPosition(100, 130);
        return monkey;
    }
}
