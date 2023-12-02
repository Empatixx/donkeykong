package cz.krokviak.donkeykong.hud;

import java.util.List;

public interface PersistanceService {
    void save(List<GameScore> scores);
    void addScore(GameScore score);
    List<GameScore> load();
}
