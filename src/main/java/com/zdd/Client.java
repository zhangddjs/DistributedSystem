package com.zdd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

/**
 * @author zdd
 * @date 2018-11-29 9:32
 */
class Client implements Runnable {
    private Socket s;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private boolean bConnected = false;

    public Client(Socket s) {
        this.s = s;
        try {
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            bConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String str) {
        try {
            dos.writeUTF(str);
        } catch (IOException e) {
            GreetingServer.clients.remove(this);
            System.out.println("对方退出了！我从List里面去掉了！");
        }
    }

    public void run() {
        try {
            while (bConnected) {
                String str = dis.readUTF();
                System.out.println("------------来自本地服务器:" + str);
                for (int i = 0; i < GreetingServer.clients.size(); i++) {
                    Client c = GreetingServer.clients.get(i);
                    c.send(str);
                }
            }
        } catch (EOFException e) {
            System.out.println("Client closed!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           /* try {
                if (dis != null)
                    dis.close();
                if (dos != null)
                    dos.close();
                if (s != null) {
                    s.close();
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }*/

        }
    }
}