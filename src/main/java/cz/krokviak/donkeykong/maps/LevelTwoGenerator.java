package cz.krokviak.donkeykong.maps;

import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.input.InputHandler;
import cz.krokviak.donkeykong.objects.Platform;
import cz.krokviak.donkeykong.objects.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LevelTwoGenerator implements LevelGenerator {
    private static String PLATFORM_TEXTURE = "platform2.png";
    private final CollisionService collisionService;
    private final InputHandler inputHandler;

    public LevelTwoGenerator(final CollisionService collisionService,
                             final InputHandler inputHandler) {
        this.collisionService = collisionService;
        this.inputHandler = inputHandler;
    }

    @Override
    public MapGeneration generate() {
        final List<Platform> platforms = generatePlatforms();
        final Player player = generatePlayer();
        return new MapGeneration(player, platforms.stream().collect(Collectors.toList()), List.of(), List.of());
    }
    private List<Platform> generatePlatforms() {
        final List<Platform> platforms = new ArrayList<>();
        for (int i = 6; i < 11; i++) {
            final Platform platform = new Platform(i * Platform.WIDTH, 100, PLATFORM_TEXTURE);
            platforms.add(platform);
        }
        return platforms;
    }
    public Player generatePlayer() {
        final Player player = new Player(inputHandler);
        player.setPosition(0, 0);
        return player;
    }
}
