package rocks.cleanstone.net.mcpe.packet.outbound;

import rocks.cleanstone.net.mcpe.packet.MCPEOutboundPacketType;
import rocks.cleanstone.net.packet.Packet;
import rocks.cleanstone.net.packet.PacketType;

public class MoveEntityDeltaPacket implements Packet {

    private final long runtimeEntityID;
    private final byte flags;

    public MoveEntityDeltaPacket(long runtimeEntityID, byte flags) {
        this.runtimeEntityID =  runtimeEntityID;
        this.flags =  flags;
    }

    public long getRuntimeEntityID() {
        return runtimeEntityID;
    }

    public byte getFlags() {
        return flags;
    }

    @Override
    public PacketType getType() {
        return MCPEOutboundPacketType.MOVE_ENTITY_DELTA;
    }
}

