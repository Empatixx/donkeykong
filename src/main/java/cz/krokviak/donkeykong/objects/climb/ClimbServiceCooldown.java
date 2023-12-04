package cz.krokviak.donkeykong.objects.climb;

import cz.krokviak.donkeykong.objects.ClimbDirection;
import cz.krokviak.donkeykong.objects.ClimbEntity;
import cz.krokviak.donkeykong.objects.ladder.CompositeLadder;
import cz.krokviak.donkeykong.objects.ladder.Ladder;

import java.util.HashMap;
import java.util.Map;

public class ClimbServiceCooldown implements ClimbService {
    private final static float CLIMB_COOLDOWN_SECONDS = 5f;
    private final Map<Ladder, Float> ladderCooldowns;
    private final ClimbService climbService;

    public ClimbServiceCooldown(final ClimbEntity entity,
                                final ClimbDirection... directions) {
        ladderCooldowns = new HashMap<>();
        this.climbService = new ClimbServiceProbability(entity, directions);
    }

    @Override
    public void climb() {
        climbService.climb();
    }

    @Override
    public void stopClimbing() {
        climbService.stopClimbing();
    }

    @Override
    public void setLadder(final Ladder ladder) {
        if (ladder instanceof CompositeLadder compositeLadder){
            if (ladderCooldowns.containsKey(compositeLadder.getLadder())){
                return;
            }
        }
        if (ladderCooldowns.containsKey(ladder)) {
            return;
        }
        climbService.setLadder(ladder);
        ladderCooldowns.put(ladder, 0f);
    }

    @Override
    public Ladder getLadder() {
        return climbService.getLadder();
    }

    @Override
    public boolean isClimbing() {
        return climbService.isClimbing();
    }

    @Override
    public void update(float dt) {
        climbService.update(dt);
        ladderCooldowns.replaceAll((ladder, cooldown) -> cooldown + dt);
        ladderCooldowns.entrySet().removeIf(entry -> entry.getValue() > CLIMB_COOLDOWN_SECONDS);
    }
}
