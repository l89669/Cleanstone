package rocks.cleanstone.net.mcpe.packet.outbound;

import rocks.cleanstone.net.mcpe.packet.MCPEOutboundPacketType;
import rocks.cleanstone.net.packet.Packet;
import rocks.cleanstone.net.packet.PacketType;

public class BossEventPacket implements Packet {

    private final long bossEntityID;
    private final int eventType;

    public BossEventPacket(long bossEntityID, int eventType) {
        this.bossEntityID = bossEntityID;
        this.eventType = eventType;
    }

    public long getBossEntityID() {
        return bossEntityID;
    }

    public int getEventType() {
        return eventType;
    }

    @Override
    public PacketType getType() {
        return MCPEOutboundPacketType.BOSS_EVENT;
    }
}

