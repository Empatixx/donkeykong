package cz.krokviak.donkeykong.gamestate;

import cz.krokviak.donkeykong.persistance.GameScore;

import java.util.*;

public class GamestateShareService {
    private String username;
    private final List<GameScore> scores;
    public GamestateShareService() {
        this.scores = new ArrayList<>();
    }
    public void setUsername(final String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void addScore(int totalScore) {
        scores.add(new GameScore(username, totalScore));
    }

    public List<GameScore> getScores() {
        return new ArrayList<>(scores);
    }
}
