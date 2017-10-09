package server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.CompletableFuture;

/**
 * Created by LJT on 17-10-9.
 * email: ljt1343@gmail.com
 */
public class UdpServer {

    private volatile boolean isFirst = false;

    private UdpServer() {

    }

    public static UdpServer getInstance() {
        return UdpServerHolder.udpServer;
    }

    private static class UdpServerHolder {
        private static UdpServer udpServer = new UdpServer();
    }

    public void init(int port) {
        if (!isFirst) {

            synchronized (this) {

                if (!isFirst) {
                    CompletableFuture.runAsync(() -> {
                        EventLoopGroup group = new NioEventLoopGroup();
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.group(group)
                                .channel(NioDatagramChannel.class)
                                .localAddress(port)
//                                .option(ChannelOption.SO_BROADCAST, true)
                                .handler(new ChannelInitializer<NioDatagramChannel>() {

                                    @Override
                                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                                        ch.pipeline().addLast(new UdpServerHandler());
                                        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                                    }
                                });
                        try {
                            bootstrap.bind(port).sync().channel().closeFuture().await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });

                    isFirst = true;
                }

            }
        }
    }

}
