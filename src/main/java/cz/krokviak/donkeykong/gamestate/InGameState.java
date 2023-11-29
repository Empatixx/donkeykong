package cz.krokviak.donkeykong.gamestate;

import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.drawable.Background;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.hud.Scoreboard;
import cz.krokviak.donkeykong.input.InputHandler;
import cz.krokviak.donkeykong.items.ItemService;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import cz.krokviak.donkeykong.maps.LevelOneGenerator;
import cz.krokviak.donkeykong.maps.MapGeneration;
import cz.krokviak.donkeykong.maps.MapService;
import cz.krokviak.donkeykong.objects.Monkey;
import cz.krokviak.donkeykong.objects.Player;
import cz.krokviak.donkeykong.objects.Princess;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.util.List;

public class InGameState extends GameStateSupport implements GameState {
    private final Player player;
    private final Canvas canvas = new Canvas(DonkeyKongApplication.WIDTH, DonkeyKongApplication.HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final CollisionService collisionService;
    private final ItemService itemService;
    private final MapService mapService;
    private final Scoreboard scoreboard;

    public InGameState(final GameStateManager gsm,
                       final InputHandler inputHandler,
                       final Pane pane) {
        super(gsm, pane);
        collisionService = new CollisionService();
        scoreboard = new Scoreboard();

        final LevelOneGenerator levelOneGenerator = new LevelOneGenerator(inputHandler, collisionService);
        final MapGeneration generation = levelOneGenerator.generate();

        this.mapService = new MapService(generation, collisionService);
        player = generation.player();
        itemService = new ItemService(generation.items(), collisionService);
    }

    @Override
    public void update(float dt) {
        mapService.update(dt);
        collisionService.update();
        itemService.update(dt);
        scoreboard.update(dt);
    }

    @Override
    public void draw() {
        gc.clearRect(0, 0, DonkeyKongApplication.WIDTH, DonkeyKongApplication.HEIGHT);

        mapService.draw(gc);
        scoreboard.draw(gc);
        collisionService.draw(gc);
    }

    @Override
    public void init() {
        root.getChildren().clear();
        root.getChildren().add(canvas);
        canvas.setFocusTraversable(true);
    }

}
