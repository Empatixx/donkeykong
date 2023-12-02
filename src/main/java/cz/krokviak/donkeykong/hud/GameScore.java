package cz.krokviak.donkeykong.hud;

public record GameScore(
        String name,
        int score
){
    @Override
    public String toString() {
        return name + " " + score;
    }
}
