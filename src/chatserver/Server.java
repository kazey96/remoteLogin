/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author xetiz
 */
public class Server extends Thread {

    public static int port = 3456;
    public static Server server;
    public ServerSocket serverSocket;
    public ObjectOutputStream objectOutputStream;
    public static JFrame jframe;

    public static void main(String[] args) {
        server = new Server();
        server.start();

      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               jframe= new NewJFrame(server);
               jframe.setVisible(true);
            }
        });
        /*Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter message");
            String line = scanner.nextLine();
            server.sendMessage(line);
        }*/
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Socket connected");
                new MessageWaiter(socket).start();
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (objectOutputStream != null) {
            try {
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    public class MessageWaiter extends Thread {

        public ObjectInputStream objectInputStream;

        public MessageWaiter(Socket socket) {
            try {
                objectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            System.out.println("Message waiter");
            while (true) {
                try {
                    String message = (String) objectInputStream.readObject();
                    System.out.println(message);
                    
                    //display in jframe from here
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
