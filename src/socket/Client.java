/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author S.KasımYurtaslan
 */
public class Client {

    String nickName;
    Socket socket;

    BufferedReader incommingConn;
    BufferedReader console;
    PrintStream outputConn;

    Runnable reader = new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println("Connected to Server");
                while (true) {
                    String line = incommingConn.readLine();
                    System.out.println(line);

                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.init();
        client.run();
    }

    private void init() throws IOException {

        console = new BufferedReader(new InputStreamReader(System.in));
        readNickName();

        try {
            socket = new Socket("localhost", 80);
        } catch (Exception e) {
            System.out.println("[ERROR] Server is not alive");
            System.exit(1);
        }
        incommingConn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputConn = new PrintStream(socket.getOutputStream());
        new Thread(reader).start();
    }

    private void readNickName() throws IOException {
        System.out.print("Nick Name>");
        nickName = console.readLine();
    }

    private void run() throws IOException {
        while (true) {
            System.out.print(nickName + ">");
            String line =console.readLine();
            send(line);
        }
    }

    private void send(String line) throws IOException {
        outputConn.println(nickName + ">" + line);
        outputConn.flush();
    }
}
