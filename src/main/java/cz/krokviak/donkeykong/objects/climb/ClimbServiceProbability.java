package cz.krokviak.donkeykong.objects.climb;

import cz.krokviak.donkeykong.objects.ClimbDirection;
import cz.krokviak.donkeykong.objects.ClimbEntity;
import cz.krokviak.donkeykong.objects.ladder.Ladder;

import java.util.concurrent.ThreadLocalRandom;

public class ClimbServiceProbability implements ClimbService{
    private final static float CLIMB_PROBABILITY = 0.25f;
    private final ClimbService climbServiceImpl;
    public ClimbServiceProbability(final ClimbEntity entity,
                                   final ClimbDirection... directions){
        this.climbServiceImpl = new ClimbServiceImpl(entity, directions);
    }
    @Override
    public void climb() {
        climbServiceImpl.climb();
    }

    @Override
    public void stopClimbing() {
        climbServiceImpl.stopClimbing();
    }

    @Override
    public void setLadder(Ladder ladder) {
        final Ladder lastLadder = climbServiceImpl.getLadder();
        if (lastLadder == null || !lastLadder.equals(ladder) ) {
            climbServiceImpl.setLadder(ladder);
            if (ThreadLocalRandom.current().nextFloat() < CLIMB_PROBABILITY) {
                climb();
            }
        }
    }

    @Override
    public Ladder getLadder() {
        return climbServiceImpl.getLadder();
    }

    @Override
    public boolean isClimbing() {
        return climbServiceImpl.isClimbing();
    }

    @Override
    public void update(float dt) {
        climbServiceImpl.update(dt);
    }
}
