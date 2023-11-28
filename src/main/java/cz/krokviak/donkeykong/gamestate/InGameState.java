package cz.krokviak.donkeykong.gamestate;

import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.drawable.Background;
import cz.krokviak.donkeykong.input.InputHandler;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import cz.krokviak.donkeykong.objects.Map;
import cz.krokviak.donkeykong.objects.Monkey;
import cz.krokviak.donkeykong.objects.Player;
import cz.krokviak.donkeykong.objects.Princess;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class InGameState extends GameStateSupport implements GameState{
    private final Player player;
    private final Monkey monkey;
    private final Princess princess;
    private final Background background;
    private final Map map;
    private final Canvas canvas = new Canvas(DonkeyKongApplication.WIDTH, DonkeyKongApplication.HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final CollisionService collisionService;
    public InGameState(final GameStateManager gsm,
                       final InputHandler inputHandler,
                       final Pane pane) {
        super(gsm, pane);
        player = new Player(inputHandler);
        monkey = new Monkey();
        monkey.setPosition(30, 0);
        princess = new Princess();
        princess.setPosition(180, -5);
        background = new Background();
        map = new Map();
        collisionService = new CollisionService();

        map.load();

        collisionService.addAABB(map.getAABBs());
        collisionService.addAABB(player);

        player.setPosition(200, 200);
    }

    @Override
    public void update(float dt) {
        player.update(dt);
        monkey.update(dt);
        princess.update(dt);
        collisionService.update();
    }

    @Override
    public void draw() {
        gc.clearRect(0, 0, DonkeyKongApplication.WIDTH, DonkeyKongApplication.HEIGHT);

        background.draw(gc);
        player.draw(gc);
        monkey.draw(gc);
        map.draw(gc);
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