package cz.krokviak.donkeykong.maps;

import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.input.InputHandler;

public class LevelTwoGenerator implements LevelGenerator {
    private final CollisionService collisionService;
    private final InputHandler inputHandler;

    public LevelTwoGenerator(final CollisionService collisionService,
                             final InputHandler inputHandler) {
        this.collisionService = collisionService;
        this.inputHandler = inputHandler;
    }

    @Override
    public MapGeneration generate() {
        return null;
    }
}
