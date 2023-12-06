package cz.krokviak.donkeykong.gamestate;

import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.input.InputHandler;
import cz.krokviak.donkeykong.items.ItemService;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import cz.krokviak.donkeykong.maps.LevelService;
import cz.krokviak.donkeykong.maps.MapGeneration;
import cz.krokviak.donkeykong.maps.MapService;
import cz.krokviak.donkeykong.objects.player.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class InGameState extends GameStateSupport implements GameState {
    private final GamestateShareService gamestateShareService;
    private Player player;
    private final Canvas canvas = new Canvas(DonkeyKongApplication.WIDTH, DonkeyKongApplication.HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final CollisionService collisionService;
    private final ItemService itemService;
    private final LevelService levelService;
    private MapService mapService;

    public InGameState(final GameStateManager gsm,
                       final GamestateShareService gamestateShareService,
                       final InputHandler inputHandler,
                       final Pane pane) {
        super(gsm, pane);
        this.gamestateShareService = gamestateShareService;
        collisionService = new CollisionService();
        itemService = new ItemService(collisionService);
        this.levelService = new LevelService(collisionService, inputHandler);
    }

    @Override
    public void update(float dt) {
        mapService.update(dt);
        collisionService.update();
        itemService.update(dt);
        checkPlayerState();
    }

    private void checkPlayerState() {
        if (!player.isAlive()) {
            if (player.hasExtraLifes() && player.canRespawn()) {
                resetLevel(player);
            } else if (!player.hasExtraLifes() && player.canRespawn()){
                gamestateShareService.addScore(player.getTotalScore());
                gsm.setState(GameStateType.MENU);
            }
            return;
        }
        if (player.isAtTop()) {
            nextLevel(player);
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
        resetLevel(null);

        root.getChildren().clear();
        root.getChildren().add(canvas);
        canvas.setFocusTraversable(true);
    }
    private void resetLevel(final Player lastPlayer){
        levelService.nextLevel();

        setupMap();
        player.setPreviousDeadPlayer(lastPlayer);
    }
    public void nextLevel(final Player lastPlayer){
        levelService.nextLevel();
        setupMap();
        player.setPreviousAlivePlayer(lastPlayer);
    }
    public void setupMap(){
        collisionService.clear();
        itemService.clear();
        final MapGeneration generation = levelService.generate();
        this.mapService = new MapService(generation, collisionService, itemService);
        player = generation.player();
    }

}
