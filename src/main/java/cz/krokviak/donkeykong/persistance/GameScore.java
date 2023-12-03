package cz.krokviak.donkeykong.persistance;

public record GameScore(
        String name,
        int score
){
    @Override
    public String toString() {
        return name + " " + score;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
