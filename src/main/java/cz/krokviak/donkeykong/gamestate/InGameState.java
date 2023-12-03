package cz.krokviak.donkeykong.gamestate;

import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.input.InputHandler;
import cz.krokviak.donkeykong.items.ItemService;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import cz.krokviak.donkeykong.maps.LevelOneGenerator;
import cz.krokviak.donkeykong.maps.MapGeneration;
import cz.krokviak.donkeykong.maps.MapService;
import cz.krokviak.donkeykong.objects.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class InGameState extends GameStateSupport implements GameState {
    private final GamestateShareService gamestateShareService;
    private final InputHandler inputHandler;
    private Player player;
    private final Canvas canvas = new Canvas(DonkeyKongApplication.WIDTH, DonkeyKongApplication.HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private CollisionService collisionService;
    private ItemService itemService;
    private MapService mapService;

    public InGameState(final GameStateManager gsm,
                       final GamestateShareService gamestateShareService,
                       final InputHandler inputHandler,
                       final Pane pane) {
        super(gsm, pane);
        this.inputHandler = inputHandler;
        this.gamestateShareService = gamestateShareService;
    }

    @Override
    public void update(float dt) {
        mapService.update(dt);
        collisionService.update();
        itemService.update(dt);
        if (!player.isAlive()) {
            if (player.hasExtraLifes() && player.canRespawn()) {
                gameInit(player.getLifes() - 1);
            } else if (!player.hasExtraLifes() && player.canRespawn()){
                gamestateShareService.addScore(player.getTotalScore());
                gsm.setState(GameStateType.MENU);
            }
        }
    }

    @Override
    public void draw() {
        gc.clearRect(0, 0, DonkeyKongApplication.WIDTH, DonkeyKongApplication.HEIGHT);

        mapService.draw(gc);
        collisionService.draw(gc);
    }

    @Override
    public void init() {
        gameInit(1);

        root.getChildren().clear();
        root.getChildren().add(canvas);
        canvas.setFocusTraversable(true);
    }
    private void gameInit(final int lives){
        collisionService = new CollisionService();

        final LevelOneGenerator levelOneGenerator = new LevelOneGenerator(inputHandler, collisionService);
        final MapGeneration generation = levelOneGenerator.generate();

        this.mapService = new MapService(generation, collisionService);
        player = generation.player();
        player.setLives(lives);
        itemService = new ItemService(generation.items(), collisionService);
    }

}
