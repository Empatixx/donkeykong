package cz.krokviak.donkeykong.gamestate;

import cz.krokviak.donkeykong.persistance.FilePersistanceService;
import cz.krokviak.donkeykong.persistance.GameScore;
import cz.krokviak.donkeykong.persistance.PersistanceService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Comparator;
import java.util.List;

public class MenuState extends GameStateSupport implements GameState {
    private final Text title;
    private final Button startButton;
    private final Button exitButton;
    private final TableView<GameScore> scoreTable;
    private final TextField nameField;
    private final GamestateShareService gamestateShareService;
    private final PersistanceService persistanceService;

    public MenuState(final GameStateManager gsm,
                     final GamestateShareService gamestateShareService,
                     final Pane pane) {
        super(gsm, pane);
        this.gamestateShareService = gamestateShareService;
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
        nameField.textProperty().addListener((observable, oldValue, newValue) -> gamestateShareService.setUsername(newValue));

        // Start button setup
        startButton = new Button("Start");
        startButton.setPrefSize(100, 50);
        startButton.setLayoutX(350);
        startButton.setLayoutY(250);
        startButton.disableProperty().bind(nameField.textProperty().isEmpty());

        startButton.setOnAction(event -> gsm.setState(GameStateType.IN_GAME));

        // Score table setup
        scoreTable = new TableView<>();
        String[] columnNames = {"Name", "Score"};
        for (String columnName : columnNames) {
            scoreTable.getColumns().add(new TableColumn<>(columnName));
            scoreTable.getColumns().get(scoreTable.getColumns().size() - 1).setPrefWidth(350);
        }
        scoreTable.getColumns().get(0).setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().name()));
        scoreTable.getColumns().get(1).setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().score()));
        scoreTable.setPrefSize(716, 300);
        scoreTable.setLayoutY(450);
        scoreTable.setLayoutX(50);

        final List<GameScore> scores = persistanceService.load();
        scoreTable.getItems().addAll(scores);
        scoreTable.getItems().sort(Comparator.comparing(GameScore::score).reversed());

        // Exit button setup
        exitButton = new Button("Exit");
        exitButton.setPrefSize(100, 50);
        exitButton.setLayoutX(350);
        exitButton.setLayoutY(350);
        exitButton.setOnAction(event -> {
            persistanceService.save(scoreTable.getItems());
            System.exit(0);
        });
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

        final List<GameScore> scores = scoreTable.getItems();
        scores.addAll(gamestateShareService.getScores());
        scores.sort(Comparator.comparing(GameScore::score).reversed());
        gamestateShareService.getScores().clear();

    }
}
