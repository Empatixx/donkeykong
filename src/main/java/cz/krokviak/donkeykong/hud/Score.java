package cz.krokviak.donkeykong.hud;

import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.drawable.Updatable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.Set;

public final class Score implements Drawable, Updatable {
    public final static int LOW_SCORE = 100;
    public final static int MEDIUM_SCORE = 300;
    public final static int HIGH_SCORE = 500;
    private final static int TIME_TO_SHOW = 2;
    private final int score;
    private float timeShown = 0;
    private Point2D position;
    public Score(
            int score
    ) {
        if (!Set.of(LOW_SCORE, MEDIUM_SCORE, HIGH_SCORE).contains(score)){
            throw new IllegalArgumentException("Score must be one of: " + LOW_SCORE + ", " + MEDIUM_SCORE + ", " + HIGH_SCORE);
        }
        this.score = score;
    }
    @Override
    public void update(final float dt){
        timeShown += dt;
    }
    public boolean shouldRemove(){
        return timeShown >= TIME_TO_SHOW;
    }

    public int getScore() {
        return score;
    }

    public void setPosition(final float x, final float y) {
        this.position = new Point2D(x, y);
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        gc.fillText("+" + score, position.getX(), position.getY());
    }
}
