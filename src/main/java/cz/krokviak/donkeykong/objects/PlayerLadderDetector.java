package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.utils.InterpolatedPoint2D;

public class PlayerLadderDetector {
    private InterpolatedPoint2D position;
    private boolean climbing = false;
    private final Player player;
    private DefaultLadder ladder;

    public PlayerLadderDetector(final Player player) {
        this.player = player;
    }

    public void setLadder(DefaultLadder ladder) {
        this.ladder = ladder;
    }

    public DefaultLadder getLadder() {
        return ladder;
    }

    public void climb() {
        climbing = true;
        position = new InterpolatedPoint2D(player.getPosition(), ladder.getUpPosition().subtract(Player.WIDTH * Player.SCALE / 2f, Player.HEIGHT * Player.SCALE / 2f),0.5f);
    }

    public void update(float dt) {
        if (ladder == null) {
            return;
        }
        if (climbing && !position.isFinished()) {
            position.update(dt);
            player.setPosition((float) position.get().getX(), (float) position.get().getY());
            if (position.isFinished()) {
                climbing = false;
            }
            return;
        }
        if (!player.getBoundingBox().intersects(ladder.getBoundingBox())) {
            ladder = null;
        }
    }

    public boolean isClimbing() {
        return climbing;
    }

    public void stopClimbing() {
        climbing = false;
    }
}
