package rocks.cleanstone.game.world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import rocks.cleanstone.game.world.generation.WorldGenerator;

public class SimpleWorldManager implements WorldManager {

    private final WorldLoader worldLoader;
    private final Map<String, World> worldMap = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public SimpleWorldManager(WorldLoader worldLoader) {
        this.worldLoader = worldLoader;
    }

    @Override
    public Collection<World> getLoadedWorlds() {
        return worldMap.values();
    }

    @Override
    public boolean isWorldLoaded(String id) {
        return worldMap.containsKey(id);
    }

    @Nullable
    @Override
    public World getLoadedWorld(String id) {
        return worldMap.get(id);
    }

    @Override
    public ListenableFuture<World> loadWorld(String id) {
        ListenableFuture<World> worldListenableFuture = worldLoader.loadWorld(id);

        worldListenableFuture.addCallback(world -> {
            if (world == null) {
                logger.error("Error while loading world: {}", id);
                return;
            }

            worldMap.put(world.getID(), world);
        }, throwable -> logger.error("Error while loading world", throwable));

        return worldListenableFuture;
    }

    @Override
    public void unloadWorld(String id) {
        try {
            worldLoader.unloadWorld(id);

            worldMap.remove(id);
        } catch (Exception e) {
            logger.error("Error occurred while unloading World", e);
        }
    }

    @Override
    public void createWorld(String id, WorldGenerator generator) {
        World world;
        try {
            world = new SimpleGeneratedWorld(id, worldLoader.getDataSource(id), generator);
            worldMap.put(id, world);
        } catch (IOException e) {
            logger.error("Error occurred while loading World", e);
        }
    }

    @Override
    public void deleteWorld(String id) {
        unloadWorld(id);

        //TODO: Delete file
    }
}
