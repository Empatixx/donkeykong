package cz.krokviak.donkeykong.objects;

public interface ClimbService {
    void climb();
    void stopClimbing();
    void setLadder(final Ladder ladder);
    Ladder getLadder();
    boolean isClimbing();
    void update(final float dt);
}
