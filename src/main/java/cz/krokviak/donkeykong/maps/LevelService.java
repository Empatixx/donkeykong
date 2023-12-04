package cz.krokviak.donkeykong.maps;

import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.input.InputHandler;

import java.util.Map;

public class LevelService {
    private final Map<Level, LevelGenerator> generators;
    private Level currentLevel;
    public LevelService(final CollisionService collisionService,
                        final InputHandler inputHandler){
        this.generators = Map.of(
                Level.ONE, new LevelOneGenerator(collisionService, inputHandler),
                Level.TWO, new LevelTwoGenerator(collisionService, inputHandler)
        );
        this.currentLevel = Level.ONE;
    }

    public MapGeneration generate(){
        return generators.get(currentLevel).generate();
    }
    public void setLevel(final Level level){
        this.currentLevel = level;
    }

}
