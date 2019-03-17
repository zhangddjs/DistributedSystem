package com.zdd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zdd
 * @date 2018-11-28 8:36
 * @desperation 主线程创建一个监听机器的线程，监听到机器连接请求时，
 * 创建一个线程ThreadB，此线程循环监听该客户端的状态，设置超时时间为10秒。
 * 主线程创建一个监听前端的线程，前端控制机群开关机时，输出“开机|关机”，并改变对应
 */
public class GreetingServer extends Thread{
    //全局clients变量
    public static List<Client> clients = new ArrayList<Client>();

    private ServerSocket serverSocket;


    public GreetingServer(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(50000);
        clients = new ArrayList<Client>();
    }

    public void run()
    {
        while(true)
        {
            try
            {
                System.out.println("等待远程连接，端口号为：" + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                SocketAddress socketAddress = server.getRemoteSocketAddress();
                InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
                System.out.println("远程主机ip地址：" + inetSocketAddress.getHostName());
                System.out.println("远程主机端口：" + inetSocketAddress.getPort());
                //               System.out.println("远程主机地址：" + server.getRemoteSocketAddress());

                DataInputStream in = new DataInputStream(server.getInputStream());
                System.out.println(in.readUTF());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("谢谢连接我：" + server.getLocalSocketAddress() + "\nGoodbye!");

                in.close();
                out.close();
                server.close();
            }catch(SocketTimeoutException s)
            {
                System.out.println("Socket timed out!");
                break;
            }catch(IOException e)
            {
                e.printStackTrace();
                break;
            }
        }
    }
    public static void main(String [] args)
    {
        int port = 8989;
        try
        {
            Thread t = new GreetingServer(port);
            t.start();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
