package cz.krokviak.donkeykong.utils;

import javafx.geometry.Point2D;

public class InterpolatedPoint2D {
    private Point2D to;
    private Point2D from;
    private final float totalProgress;
    private float currentProgress = 0;
    public InterpolatedPoint2D(final Point2D from, final Point2D to, final float totalProgress) {
        this.from = from;
        this.to = to;
        this.totalProgress = totalProgress;
    }
    public Point2D get() {
        return from.add(to.subtract(from).multiply(currentProgress / totalProgress));
    }
    public void update(final float dt) {
        currentProgress += dt;
    }
    public boolean isFinished() {
        return currentProgress >= totalProgress;
    }
}
