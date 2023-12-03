package cz.krokviak.donkeykong.persistance;

import java.util.List;

public interface PersistanceService {
    void save(List<GameScore> scores);
    void addScore(GameScore score);
    List<GameScore> load();
}
