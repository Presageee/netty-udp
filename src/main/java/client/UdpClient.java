package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * Created by LJT on 17-10-9.
 * email: ljt1343@gmail.com
 */
public class UdpClient {

    private Channel channel;

    public void init() {

            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    .remoteAddress("127.0.0.1", 6666)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        protected void initChannel(NioDatagramChannel ch) throws Exception {
                            ch.pipeline().addLast(new UdpClientHandler());
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                        }
                    });

            try {
                channel = bootstrap.bind(0).sync().channel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }

    public void send() {
        channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("abcdefg", CharsetUtil.UTF_8), new InetSocketAddress("127.0.0.1", 6666)));
    }
}
