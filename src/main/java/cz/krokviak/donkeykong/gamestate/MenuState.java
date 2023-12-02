package cz.krokviak.donkeykong.gamestate;

import cz.krokviak.donkeykong.hud.FilePersistanceService;
import cz.krokviak.donkeykong.hud.GameScore;
import cz.krokviak.donkeykong.hud.PersistanceService;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuState extends GameStateSupport implements GameState {
    private final Text title;
    private final Button startButton;
    private final Button exitButton;
    private final TableView<GameScore> scoreTable;
    private final TextField nameField;
    private final PersistanceService persistanceService;

    public MenuState(final GameStateManager gsm,
                     final Pane pane) {
        super(gsm, pane);
        persistanceService = new FilePersistanceService();

        title = new Text("Donkey Kong");
        title.setFont(new Font(30)); // Set the font size
        title.setTextAlignment(TextAlignment.CENTER);
        title.setLayoutY(100);
        // Center the title (adjust after adding to scene)
        title.layoutXProperty().bind(pane.widthProperty().subtract(title.prefWidth(-1)).divide(2));

        // Name field setup
        nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setLayoutX(295);
        nameField.setLayoutY(200);

        // Start button setup
        startButton = new Button("Start");
        startButton.setPrefSize(100, 50);
        startButton.setLayoutX(350);
        startButton.setLayoutY(250);
        startButton.disableProperty().bind(nameField.textProperty().isEmpty());

        startButton.setOnAction(event -> gsm.setState(GameStateType.IN_GAME));

        // Exit button setup
        exitButton = new Button("Exit");
        exitButton.setPrefSize(100, 50);
        exitButton.setLayoutX(350);
        exitButton.setLayoutY(350);
        exitButton.setOnAction(event -> System.exit(0));

        // Score table setup
        scoreTable = new TableView<>();
        String[] columnNames = {"Name", "Score"};
        for (String columnName : columnNames) {
            scoreTable.getColumns().add(new TableColumn<>(columnName));
            scoreTable.getColumns().get(scoreTable.getColumns().size() - 1).setPrefWidth(350);
        }
        scoreTable.setPrefSize(716, 300);
        scoreTable.setLayoutY(450);
        scoreTable.setLayoutX(50);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void draw() {

    }

    @Override
    public void init() {
        root.getStylesheets().add("style.css");
        root.getChildren().clear();
        root.getChildren().addAll(startButton, exitButton, title, scoreTable, nameField);

        scoreTable.getItems().clear();
        scoreTable.getItems().addAll(persistanceService.load());
    }


}
