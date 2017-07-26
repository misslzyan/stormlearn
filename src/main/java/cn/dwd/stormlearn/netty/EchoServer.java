package cn.dwd.stormlearn.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.FutureListener;

import java.net.InetSocketAddress;

/**
 * Created by bjduanweidong on 2017/7/3.
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) {
        int port = 8080;
        new EchoServer(port).start();
    }

    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("exception",new ChannelOutboundHandlerAdapter(){
                                        @Override
                                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                            cause.printStackTrace();
                                            ctx.close();
                                        }
                                    })
                                    .addLast("decoder",new HttpRequestDecoder())
                                    .addLast("encoder",new io.netty.handler.codec.http.HttpResponseEncoder())
                                 .addLast("agg",new HttpObjectAggregator(512*1024))
                                    .addLast("a",new ChannelInboundHandlerAdapter(){
                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            try {
                                                System.out.println(msg.getClass());
                                                FullHttpRequest request = (FullHttpRequest) msg;
                                                System.out.println(request.content().toString(CharsetUtil.UTF_8));
                                                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,request.content());
                                                ctx.write(response);
//                                                throw new NullPointerException("msg");
                                            }catch (Error e){
                                                e.printStackTrace();
//                                                throw  e;
                                            }
                                        }

                                        @Override
                                        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                                        }

                                        @Override
                                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                            System.out.println("exception");
                                            cause.printStackTrace();
                                            ctx.close();
                                        }
                                    }).addLast("b",new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    try {

                                        System.out.println(msg.getClass());
                                        FullHttpRequest request = (FullHttpRequest) msg;
                                        System.out.println(request.content().toString(CharsetUtil.UTF_8));
                                        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,request.content());
                                        ctx.write(response);
                                        throw new NullPointerException("msg");
                                    }catch (Error e){
                                        e.printStackTrace();
//                                        throw  e;
                                    }
                                }

                                @Override
                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    System.out.println("exception");
                                    cause.printStackTrace();
                                    ctx.close();
                                }
                            });
//                            .addLast(new ChannelInboundHandlerAdapter() {
//                                @Override
//                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                    ByteBuf in = (ByteBuf)msg;
//                                    String res = in.toString(CharsetUtil.UTF_8);
//                                    System.out.println(res);
//                                    ctx.write(in);
////                                    ctx.write(Unpooled.copiedBuffer(res.getBytes()));
//                                }
//
//                                @Override
//                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//                                    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(new ChannelFutureListener() {
//                                        public void operationComplete(ChannelFuture future) {
//                                            System.out.println("close");
//                                            future.channel().close();
//                                        }});
//                                }
//
//                                @Override
//                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//                                    cause.printStackTrace();
//                                    ctx.close();
//                                }
//                            });
                        }
                    });
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();

        }
    }
}
