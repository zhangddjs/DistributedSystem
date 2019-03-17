package com.zdd;

import java.io.*;
import java.net.*;

/**
 * @author zdd
 * @date 2018-11-28 8:39
 */
public class GreetingClient extends Thread{
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void run()
    {
        while(true)
        {
            String serverName = "127.0.0.1";
            int port = 8989;
            try
            {
                System.out.println("连接到主机：" + serverName + " ，端口号：" + port);
                Socket client = new Socket(serverName, port);
                System.out.println("远程主机地址：" + client.getRemoteSocketAddress());
                OutputStream outToServer = client.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);

                out.writeUTF(client.getLocalSocketAddress() + " " + this.getStatus());
                InputStream inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);
                System.out.println("服务器响应： " + in.readUTF());
                client.close();
            }catch(IOException e)
            {
                e.printStackTrace();
            }
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * status
     */
    private int status = 0;
    /**
     * client
     */
    private Socket client;
    public GreetingClient() throws IOException
    {
        client = new Socket("127.0.0.1", 8989);
    }

    public static void main(String [] args)
    {
        Thread t = null;
        try {
            t = new GreetingClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        t.start();
    }
}
