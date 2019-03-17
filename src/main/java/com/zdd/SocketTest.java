package com.zdd;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author zdd
 * @date 2018-11-27 23:01
 */
public class SocketTest {

    @Test
    public void testSocket() throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        //创建Socket对象；同时也向服务端发出请求
        Socket socket = new Socket(address, 8989);

        //通过输入输出流和服务端进行交互
        InputStream in = socket.getInputStream();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(in));
        System.out.println("^_^:" + reader.readLine());

        in.close();
        reader.close();

        //关闭socket资源
        socket.close();
    }

    @Test
    public void testServerSocket() throws IOException {
        //创建ServerSocket对象
        ServerSocket serverSocket = new ServerSocket(8989);
        //接受客户端的请求，并得到Socket对象
        Socket socket = serverSocket.accept();
        //通过输入输出流和客户端进行交互
        OutputStream out = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(out);
        writer.write("来自服务器的问候.");

        writer.close();
        out.close();

        //遍历socket资源
        socket.close();
        serverSocket.close();
    }




    /**
     * InetAddress: 表示互联网（或局域网）的一台主机的地址
      */
    @Test
    public void testInetAddress() throws UnknownHostException {
        //通过域名获取
//        InetAddress address = InetAddress.getByName("www.baidu.com");
//        System.out.println(address);
        //获取本地
//        InetAddress address2 = InetAddress.getLocalHost();
//        System.out.println(address2);


    }
}
