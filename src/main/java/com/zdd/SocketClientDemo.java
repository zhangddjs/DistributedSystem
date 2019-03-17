package com.zdd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketClientDemo {
private static final int PORT = 1234;
	
	private static final int BUFFER_SIZE = 1024;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket ss = new ServerSocket(PORT);
			Socket s = ss.accept();
			System.out.println("我是客户端，开放控制端口"+PORT);
			byte[] recData = null;
			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();
			while(true) {
				recData = new byte[BUFFER_SIZE];
				int r = in.read(recData);
				//int r = in.read(recData);
				if(r>-1) {
					String data = new String(recData);
					if(data.trim().equals("over")) {
						s.close();
					}
					System.out.println("读取到服务器发送的来数据："+data);
					out.write("这是客户端发给服务器的数据：".getBytes());
					out.write(recData);
				}else {
					System.out.println("数据读取完毕！");
					s.close();
					System.exit(0);
					//ss.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
