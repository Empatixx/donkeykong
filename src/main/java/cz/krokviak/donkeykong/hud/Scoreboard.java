package cz.krokviak.donkeykong.hud;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private final List<Score> scores = new ArrayList<>();
    private int totalScore;
    public void addScore(final int score){
        scores.add(new Score(score));
        totalScore += score;
    }
    public void update(final float dt){
        scores.forEach(score -> score.update(dt));
        scores.removeIf(Score::shouldRemove);
    }
    public void draw(final GraphicsContext gc){
        gc.setFill(Color.WHITE);
        gc.setFont(gc.getFont().font(20));
        gc.fillText("Score: " + totalScore, 700, 35);
        scores.forEach(score -> score.draw(gc));
    }
}

