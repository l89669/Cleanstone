package rocks.cleanstone.net.packet.minecraft.send;

import rocks.cleanstone.game.world.region.Position;
import rocks.cleanstone.net.packet.PacketType;
import rocks.cleanstone.net.packet.SendPacket;
import rocks.cleanstone.net.packet.minecraft.MinecraftSendPacketType;
import rocks.cleanstone.net.packet.minecraft.enums.Direction;

import java.util.UUID;

public class SpawnPaintingPacket extends SendPacket {

    private final int entityID;
    private final UUID entityUUID;
    private final String title;
    private final Position location;
    private final Direction displayDirection;

    public SpawnPaintingPacket(int entityID, UUID entityUUID, String title, Position location, byte displayDirection) {
        this.entityID = entityID;
        this.entityUUID = entityUUID;
        this.title = title;
        this.location = location;
        this.displayDirection = Direction.fromDirectionID(displayDirection);
    }

    public SpawnPaintingPacket(int entityID, UUID entityUUID, String title, Position location, Direction displayDirection) {
        this.entityID = entityID;
        this.entityUUID = entityUUID;
        this.title = title;
        this.location = location;
        this.displayDirection = displayDirection;
    }

    public int getEntityID() {
        return entityID;
    }

    public UUID getEntityUUID() {
        return entityUUID;
    }

    public String getTitle() {
        return title;
    }

    public Position getLocation() {
        return location;
    }

    public Direction getDisplayDirection() {
        return displayDirection;
    }

    @Override
    public PacketType getType() {
        return MinecraftSendPacketType.SPAWN_PAINTING;
    }
}