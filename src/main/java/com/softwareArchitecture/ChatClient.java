package com.softwareArchitecture;

/**
 * @author zdd
 * @date 2018-11-29 11:11
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import static java.lang.Thread.sleep;

/**
 * @author Michael Huang
 *
 */
public class ChatClient extends Frame {
    Socket s = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;
    private int status = 0;
    private static final int PORT = 8888;

    TextField tfTxt = new TextField();
    TextArea taContent = new TextArea();
    Button btn = new Button("connect");


    Thread tRecv = new Thread(new RecvThread());
    Thread tSend = new Thread(new SendThread());

    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }

    public void launchFrame() {
        setLocation(400, 300);
        this.setSize(300, 300);
  //      add(tfTxt, BorderLayout.SOUTH);
        add(btn, BorderLayout.SOUTH);       //连接按钮
        add(taContent, BorderLayout.NORTH);
        pack();
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent arg0) {
                if (bConnected)
                    disconnect();
                System.exit(0);
            }

        });
   //     tfTxt.addActionListener(new TFListener());
        btn.addActionListener(new TFListener());
        setVisible(true);
    /*    connect(port);

        tRecv.start();*/
    }

    public void connect() {
        try {
            status = 1;
            s = new Socket("127.0.0.1", PORT);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            System.out.println("~~~~~~~~连接成功~~~~~~~~!");
            bConnected = true;
        } catch (UnknownHostException e) {
            System.out.println("~~~~~~~~连接失败，没有host~~~~~~~~!");
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println("~~~~~~~~连接失败，IO问题~~~~~~~~!");
            //e.printStackTrace();
        }

    }

    public void disconnect() {
        try {
            dos.close();
            dis.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class TFListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            connect();
            if (bConnected)
            {
                btn.setLabel("connected");
                btn.setEnabled(false);
                tRecv.start();
                tSend.start();
            }
        }
    }

    private class RecvThread implements Runnable {

        public void run() {
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    if(Integer.parseInt(str) != status)
                    //taContent.setText(taContent.getText() + str + '\n');
                    {
                        status = Integer.parseInt(str);
                        taContent.setText(taContent.getText() + '\n' + "status change to:"+ status);
                    } else {
                        taContent.setText(taContent.getText() + '\n' + "status:"+ status);
                    }
                }
            } catch (SocketException e) {
                System.out.println("退出了，bye!");
                System.exit(0);
            } catch (EOFException e) {
                System.out.println("退出了，bye!");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private class SendThread implements Runnable {

        public void run() {
            while (bConnected) {
                String str = String.valueOf(status);
                tfTxt.setText("");

                try {
                    dos.writeUTF(str);
                    dos.flush();
                    sleep(5000);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
