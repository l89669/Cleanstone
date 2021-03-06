package rocks.cleanstone.net.minecraft.protocol.v1_12_2.inbound;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;
import rocks.cleanstone.game.Position;
import rocks.cleanstone.game.block.Face;
import rocks.cleanstone.net.minecraft.packet.enums.DiggingStatus;
import rocks.cleanstone.net.minecraft.packet.inbound.PlayerDiggingPacket;
import rocks.cleanstone.net.protocol.InboundPacketCodec;
import rocks.cleanstone.net.utils.ByteBufUtils;
import rocks.cleanstone.utils.Vector;

import java.io.IOException;

@Component
public class PlayerDiggingCodec implements InboundPacketCodec<PlayerDiggingPacket> {

    @Override
    public PlayerDiggingPacket decode(ByteBuf byteBuf) throws IOException {
        final int diggingStatusID = ByteBufUtils.readVarInt(byteBuf);
        final Vector positionVector = ByteBufUtils.readVector(byteBuf);
        final byte faceID = byteBuf.readByte();

        final DiggingStatus diggingStatus = DiggingStatus.fromStatusID(diggingStatusID);
        Preconditions.checkNotNull(diggingStatus, "Invalid diggingStatusID " + diggingStatusID);

        final Position position = new Position(positionVector);
        final Face face = Face.fromFaceID(faceID);
        Preconditions.checkNotNull(face, "Invalid faceID " + faceID);

        return new PlayerDiggingPacket(diggingStatus, position, face);
    }
}
