package server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


/**
 * Created by LJT on 17-10-9.
 * email: ljt1343@gmail.com
 */
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //super.exceptionCaught(ctx, cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        System.out.println(datagramPacket.content());
//        System.out.println();
        channelHandlerContext.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("abcd", CharsetUtil.UTF_8),
                datagramPacket.sender()));
    }
}
