package rocks.cleanstone.net.netty.pipeline.outbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import rocks.cleanstone.net.Connection;
import rocks.cleanstone.net.utils.ByteBufUtils;

public class ByteStreamEncoder extends MessageToByteEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) {
        Connection connection = ctx.channel().attr(AttributeKey.<Connection>valueOf("connection")).get();

        if (!connection.isCompressionEnabled()) {
            int packetLength = in.readableBytes();
            ByteBufUtils.writeVarInt(out, packetLength);
            out.writeBytes(in);
        } else {
            int uncompressedPacketLength = ctx.channel().attr(
                    AttributeKey.<Integer>valueOf("uncompressedPacketLength")).get();
            int packetLength = in.readableBytes() + ByteBufUtils.getVarIntSize(uncompressedPacketLength);
            ByteBufUtils.writeVarInt(out, packetLength);
            ByteBufUtils.writeVarInt(out, uncompressedPacketLength);
            out.writeBytes(in);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
