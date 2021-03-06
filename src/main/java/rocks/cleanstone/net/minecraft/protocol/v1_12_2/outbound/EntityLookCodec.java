package rocks.cleanstone.net.minecraft.protocol.v1_12_2.outbound;

import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;
import rocks.cleanstone.net.minecraft.packet.outbound.EntityLookPacket;
import rocks.cleanstone.net.protocol.OutboundPacketCodec;
import rocks.cleanstone.net.utils.ByteBufUtils;

@Component
public class EntityLookCodec implements OutboundPacketCodec<EntityLookPacket> {

    @Override
    public ByteBuf encode(ByteBuf byteBuf, EntityLookPacket packet) {

        ByteBufUtils.writeVarInt(byteBuf, packet.getEntityID());
        byteBuf.writeByte((int) (packet.getYaw() / 360.0 * 256));
        byteBuf.writeByte((int) (packet.getPitch() / 360.0 * 256));
        byteBuf.writeBoolean(packet.isOnGround());

        return byteBuf;
    }
}
