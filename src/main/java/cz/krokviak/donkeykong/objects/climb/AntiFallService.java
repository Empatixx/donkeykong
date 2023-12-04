package cz.krokviak.donkeykong.objects.climb;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.objects.Platform;
import javafx.geometry.Rectangle2D;

import java.util.List;

public class AntiFallService {
    private final static int STAIR_HEIGHT_THRESHOLD = 3; // Maximum height the player can step up automatically
    private final static int THRESHOLD = STAIR_HEIGHT_THRESHOLD + 3;
    public final CollisionService collisionService;
    public final AABB nextStep;
    public AntiFallService(final AABB nexStep,
                           final CollisionService collisionService) {
        this.nextStep = nexStep;
        this.collisionService = collisionService;
    }
    public boolean nextStepWillFall(){
        final List<Platform> platforms = collisionService.getAabbs().stream()
                .filter(aabb -> aabb instanceof Platform)
                .map(aabb -> (Platform) aabb)
                .toList();
        for (final Platform platform : platforms) {
            final Rectangle2D platformBoundingBox = platform.getBoundingBox();
            final Rectangle2D nextStepBoundingBox = nextStep.getBoundingBox();

            final Rectangle2D nextStepBoundingBoxWithThreshold = new Rectangle2D(
                    nextStepBoundingBox.getMinX(),
                    nextStepBoundingBox.getMinY(),
                    nextStepBoundingBox.getWidth(),
                    nextStepBoundingBox.getHeight() + THRESHOLD
            );
            if (platformBoundingBox.intersects(nextStepBoundingBoxWithThreshold)) {
                return false;
            }
        }
        return true;
    }

}
