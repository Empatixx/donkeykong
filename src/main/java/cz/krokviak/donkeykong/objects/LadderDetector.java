package cz.krokviak.donkeykong.objects;

public class LadderDetector {
    private final Player player;
    private Ladder ladder;
    public LadderDetector(final Player player) {
        this.player = player;
    }
    public void setLadder(Ladder ladder) {
        this.ladder = ladder;
    }

    public Ladder getLadder() {
        return ladder;
    }

    public void update(float dt) {
        if (ladder == null) {
            return;
        }
        if (!player.getBoundingBox().intersects(ladder.getBoundingBox())) {
            ladder = null;
        }
    }
}
