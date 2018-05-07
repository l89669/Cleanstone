package rocks.cleanstone.net.packet.protocol;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import rocks.cleanstone.net.packet.Packet;

public interface PacketCodec {
    Packet decode(ByteBuf byteBuf) throws IOException;

    ByteBuf encode(ByteBuf byteBuf, Packet packet) throws IOException, ClientLayerTooHighException;

    ByteBuf upgradeByteBuf(ByteBuf previousLayerByteBuf);

    ByteBuf downgradeByteBuf(ByteBuf nextLayerByteBuf);
}
