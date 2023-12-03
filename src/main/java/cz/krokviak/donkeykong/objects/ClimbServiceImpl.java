package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.utils.InterpolatedPoint2D;

import java.util.Set;

public class ClimbServiceImpl implements ClimbService {
    private final static float CLIMB_TIME = 0.5f;
    private final Set<ClimbDirection> directions;
    private Ladder ladder;
    private InterpolatedPoint2D transformation;
    private ClimbEntity entity;

    public ClimbServiceImpl(final ClimbEntity climbEntity,
                            final ClimbDirection... possibleDirections) {
        this.entity = climbEntity;
        this.directions = Set.of(possibleDirections);
        if(directions.isEmpty()){
            throw new IllegalStateException("Must be at least one climb direction!");
        }
    }

    @Override
    public void climb() {
        if (ladder instanceof DefaultLadder && directions.contains(ClimbDirection.UP)){
            transformation = new InterpolatedPoint2D(
                    entity.getPosition(),
                    ladder.getUpPosition().subtract(entity.getWidth() / 2f, entity.getHeight() / 2f),
                    CLIMB_TIME);
        } else if (ladder instanceof CompositeLadder && directions.contains(ClimbDirection.DOWN)){
            transformation = new InterpolatedPoint2D(
                    entity.getPosition(),
                    ladder.getDownPosition().subtract(entity.getWidth() / 2f, entity.getHeight() / 2f),
                    CLIMB_TIME);
        }
    }

    @Override
    public void stopClimbing() {

    }

    @Override
    public void setLadder(Ladder ladder) {
        this.ladder = ladder;
    }

    @Override
    public Ladder getLadder() {
        return ladder;
    }

    @Override
    public boolean isClimbing() {
        return transformation != null;
    }

    @Override
    public void update(float dt) {
        if (transformation == null){
            return;
        }
        if (transformation.isFinished()){
            transformation = null;
            return;
        }
        transformation.update(dt);
        entity.setPosition((float) transformation.get().getX(), (float) transformation.get().getY());
    }
}
