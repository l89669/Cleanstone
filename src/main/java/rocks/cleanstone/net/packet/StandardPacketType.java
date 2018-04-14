package rocks.cleanstone.net.packet;

import rocks.cleanstone.net.packet.minecraft.send.KeepAlive;
import rocks.cleanstone.net.packet.protocol.ProtocolType;
import rocks.cleanstone.net.packet.protocol.StandardProtocolType;

public enum StandardPacketType implements PacketType {
    KEEP_ALIVE(0, KeepAlive.class, StandardProtocolType.MINECRAFT);

    private final int typeId;
    private final ProtocolType protocolType;
    private final Class<? extends Packet> packetClass;

    StandardPacketType(int typeId, Class<? extends Packet> packetClass, ProtocolType protocolType) {
        this.typeId = typeId;
        this.packetClass = packetClass;
        this.protocolType = protocolType;
    }

    @Override
    public int getTypeId() {
        return typeId;
    }

    @Override
    public ProtocolType getProtocolType() {
        return protocolType;
    }
}
