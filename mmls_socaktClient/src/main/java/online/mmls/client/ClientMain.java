package online.mmls.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by xiaopeng on 2017/3/15.
 */
public class ClientMain {
    private static final Logger logger = LoggerFactory.getLogger(ClientMain.class);

    /**
     * Socket客户端
     */
    public static void main(String[] args) {
        try {
            //创建Socket对象
            int i = 0;
            while (true) {

                i++;
                Socket socket = new Socket("localhost", 8888);
                System.out.println("第" + i + "次,连接成功!");
                Scanner s=new Scanner(System.in);
                System.out.println("请输入你要发送的内容:");
                String msg=s.next();
                //根据输入输出流和服务端连接
                OutputStream outputStream = socket.getOutputStream();//获取一个输出流，向服务端发送信息
                PrintWriter printWriter = new PrintWriter(outputStream);//将输出流包装成打印流
                printWriter.print(msg);
                printWriter.flush();
                socket.shutdownOutput();//关闭输出流
                InputStream inputStream = socket.getInputStream();//获取一个输入流，接收服务端的信息
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);//包装成字符流，提高效率
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//缓冲区
                String info = "";
                String temp;//临时变量
                while ((temp = bufferedReader.readLine()) != null) {
                    info += temp;
                    System.out.println("客户端接收服务端发送信息：" + info);
                }

                //关闭相对应的资源
                bufferedReader.close();
                inputStream.close();
                printWriter.close();
                outputStream.close();
                socket.close();
                if (i==10)
                    return;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (ConnectException e) {
            System.out.println("连接失败!");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
