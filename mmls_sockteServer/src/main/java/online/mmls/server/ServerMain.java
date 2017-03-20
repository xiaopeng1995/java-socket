package online.mmls.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by xiaopeng on 2017/3/15.
 */
public class ServerMain {
    private static final Logger logger = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) {
        /**
         * Socket服务端
         */
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            logger.info("服务端已启动，等待客户端连接..");
            while (true) {
                Socket socket = serverSocket.accept();//侦听并接受到此套接字的连接,返回一个Socket对象
                //根据输入输出流和客户端连接
                InputStream inputStream = socket.getInputStream();//得到一个输入流，接收客户端传递的信息
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);//提高效率，将自己字节流转为字符流
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//加入缓冲区
                String temp;
                String info = "";

                while ((temp = bufferedReader.readLine()) != null) {
                    info += temp;
                    logger.info("客户端ip为：" + socket.getInetAddress().getHostAddress()+"收到信息：" + info  );
                }
                OutputStream outputStream = socket.getOutputStream();//获取一个输出流，向服务端发送信息
                PrintWriter printWriter = new PrintWriter(outputStream);//将输出流包装成打印流
                printWriter.print("收到您发的消息是:"+info);
                printWriter.flush();

                socket.shutdownOutput();//关闭输出流
                //关闭相对应的资源
                printWriter.close();
                outputStream.close();
                bufferedReader.close();
                inputStream.close();
                socket.close();
                if (info.contains("close")) {
                    serverSocket.close();
                    logger.info("socket服务关闭..");
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
