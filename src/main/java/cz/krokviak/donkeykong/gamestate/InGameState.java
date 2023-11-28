package cz.krokviak.donkeykong.gamestate;

import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.drawable.Background;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.input.InputHandler;
import cz.krokviak.donkeykong.items.ItemService;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import cz.krokviak.donkeykong.maps.LevelOneGenerator;
import cz.krokviak.donkeykong.maps.StaticGeneration;
import cz.krokviak.donkeykong.objects.Monkey;
import cz.krokviak.donkeykong.objects.Player;
import cz.krokviak.donkeykong.objects.Princess;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.util.List;

public class InGameState extends GameStateSupport implements GameState{
    private final Player player;
    private final Monkey monkey;
    private final Princess princess;
    private final Background background;
    private final Canvas canvas = new Canvas(DonkeyKongApplication.WIDTH, DonkeyKongApplication.HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final CollisionService collisionService;
    private final ItemService itemService;
    private final List<Drawable> staticDrawables;
    public InGameState(final GameStateManager gsm,
                       final InputHandler inputHandler,
                       final Pane pane) {
        super(gsm, pane);
        collisionService = new CollisionService();

        final LevelOneGenerator levelOneGenerator = new LevelOneGenerator();
        final StaticGeneration generation = levelOneGenerator.generate();
        collisionService.addAABB(generation.staticCollisions());
        staticDrawables = generation.staticDrawables();

        itemService = new ItemService(generation.items(), collisionService);

        player = new Player(inputHandler);
        monkey = new Monkey(collisionService);
        monkey.setPosition(30, 15);
        princess = new Princess();
        princess.setPosition(180, 10);
        background = new Background();
        collisionService.addAABB(player);

        player.setPosition(20, 700);
    }

    @Override
    public void update(float dt) {
        player.update(dt);
        monkey.update(dt);
        princess.update(dt);
        collisionService.update();
        itemService.update(dt);
    }

    @Override
    public void draw() {
        gc.clearRect(0, 0, DonkeyKongApplication.WIDTH, DonkeyKongApplication.HEIGHT);

        background.draw(gc);
        player.draw(gc);
        monkey.draw(gc);
        itemService.draw(gc);
        staticDrawables.forEach(drawable -> drawable.draw(gc));
        princess.draw(gc);

        collisionService.draw(gc);
    }

    @Override
    public void init() {
        root.getChildren().clear();
        root.getChildren().add(canvas);
        canvas.setFocusTraversable(true);
    }

}
