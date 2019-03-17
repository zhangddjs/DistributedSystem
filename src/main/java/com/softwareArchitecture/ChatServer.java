package com.softwareArchitecture;

/**
 * @author zdd
 * @date 2018-11-29 11:10
 */

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class ChatServer {
    private MainWindow mainWindow;
    boolean started = false;
    ServerSocket ss = null;
    List<Client> clients = new ArrayList<Client>();
    DBTool dbTool = new DBTool();

    //窗体内容
/*    TextField tfTxt = new TextField();
    Button btn = new Button("close");
    public void launchFrame() {
        setLocation(400, 300);
        this.setSize(300, 300);
        add(tfTxt, BorderLayout.NORTH);
        add(btn, BorderLayout.SOUTH);       //连接按钮
        pack();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                for (int i = 0; i < clients.size(); i++) {
                    Client c = clients.get(i);
                    dbTool.updatePort(c.port,0);
                }
                System.exit(0);
            }
        });
        btn.addActionListener(new TFListener());
        setVisible(true);
        start();
    }
    private class TFListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int port = Integer.parseInt(tfTxt.getText().trim());
            if (btn.getLabel() == "close"){
                btn.setLabel("open");
                for (int i = 0; i < clients.size(); i++) {
                    Client c = clients.get(i);
                    if (c.port == port){
                        c.changeStatus(2);
                    }
                }
            } else {
                btn.setLabel("close");
                for (int i = 0; i < clients.size(); i++) {
                    Client c = clients.get(i);
                    if (c.port == port){
                        c.changeStatus(1);
                    }
                }
            }
        }
    }*/
    //end

    public ChatServer(){};
    public ChatServer(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }


    //捕捉到变化
    public void onChange(int port, int status) {
        for (int i = 0; i < clients.size(); i++) {
            Client c = clients.get(i);
            if (c.port == port) {
                c.changeStatus(status);
            }
        }
    }


 /*   public static void main(String[] args) {
        new ChatServer().start();
        //       new ChatServer().launchFrame();
    }*/

    public void start() {
        try {
            ss = new ServerSocket(8888);
            started = true;
            System.out.println("端口已开启,占用8888端口号....");
        } catch (
                BindException e) {
            System.out.println("端口使用中....");
            System.out.println("请关掉相关程序并重新运行服务器！");
            System.exit(0);
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        try {
            while (started) {
                Socket s = ss.accept();
                Client c = new Client(s);
                System.out.println("a client connected!:" + s.getRemoteSocketAddress());
                new Thread(c).start();
                clients.add(c);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Client implements Runnable {
        private Socket s;
        private DataInputStream dis = null;
        private DataOutputStream dos = null;
        private boolean bConnected = false;
        private SocketAddress socketAddress = null;
        private InetSocketAddress inetSocketAddress = null;

        //临时使用port
        private int port = 0;
        //应该加一个status属性
        private int status = 0;

        public Client(Socket s) {
            this.s = s;
            try {
                socketAddress = s.getRemoteSocketAddress();
                inetSocketAddress = (InetSocketAddress) socketAddress;
                //临时使用port
                port = inetSocketAddress.getPort();
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                bConnected = true;
                //db插入
                //临时使用port
                status = 1;
                //判断是否存在，不存在插入，存在更新，必须写上查询byip语句
                dbTool.insertPort(port, status);
                mainWindow.reInitData();
                mainWindow.getTableModel().fireTableDataChanged();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void changeStatus(int status) {
            try {
                dos.writeUTF(String.valueOf(status));
                //临时使用port
                dbTool.updatePort(port, status);
            } catch (IOException e) {
                clients.remove(this);
                System.out.println("对方退出了！我从List里面去掉了！");
            }
        }

        public void send(String str) {
            try {
                dos.writeUTF(str);
            } catch (IOException e) {
                clients.remove(this);
                System.out.println("对方退出了！我从List里面去掉了！");
            }
        }

        public void run() {
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    System.out.println("------------来自本地服务器" + inetSocketAddress + ":" + str);
                    for (int i = 0; i < clients.size(); i++) {
                        Client c = clients.get(i);
                        c.send(str);
                    }
                }
            } catch (EOFException e) {
                status = 0;
                //临时使用port
                dbTool.updatePort(port, status);
                System.out.println("Client closed!" + inetSocketAddress);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (dis != null)
                        dis.close();
                    if (dos != null)
                        dos.close();
                    if (s != null) {
                        s.close();
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }
}