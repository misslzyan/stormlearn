package cn.dwd.stormlearn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by bjduanweidong on 2017/7/4.
 */
public class MyServer {

    public static void main(String[] args) throws IOException {
        MyServer myServer = new MyServer(8080);
        myServer.listen();
    }

    //接收和发送数据冲区
    private ByteBuffer send = ByteBuffer.allocateDirect(10);
    private ByteBuffer receive = ByteBuffer.allocateDirect(10);


    Selector selector;

    private int port;

    public MyServer(int port) throws IOException {
        this.port = port;
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server start -----");

    }

    public void listen() throws IOException {
        while(true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();

                iterator.remove();
                dealKey(selectionKey);
            }
        }
    }

    private void dealKey(SelectionKey selectionKey) throws IOException {
        System.out.println(selectionKey);
        ServerSocketChannel server = null;
        SocketChannel channel = null;
        String receiveText = null;
        String sendText = null;
        int count = 0;
        //建立好链接
        if(selectionKey.isAcceptable()){
            server = (ServerSocketChannel)selectionKey.channel();
            channel = server.accept();
            channel.configureBlocking(false);
            channel.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
        }else{
            if(selectionKey.isReadable()){
                channel = (SocketChannel) selectionKey.channel();
                receive.clear();
                channel.read(receive);
                byte[] data = new byte[receive.remaining()];
                receive.get(data);
                System.out.println(new String(data));
                selectionKey.interestOps(SelectionKey.OP_WRITE);
            }else if(selectionKey.isWritable()){
                send.flip();
                channel = (SocketChannel)selectionKey.channel();
                channel.write(send);
                selectionKey.interestOps(SelectionKey.OP_READ);
            }
        }
    }
}
