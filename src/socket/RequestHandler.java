/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author S.KasımYurtaslan
 */
public class RequestHandler extends Thread {

    Server server;
    Socket socket;

    BufferedReader incommingConn;
    PrintStream outputConn;
    int clientId;

    public RequestHandler(int clientId, Server server, Socket socket) throws IOException {
        this.clientId = clientId;
        this.server = server;
        this.socket = socket;
        incommingConn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputConn = new PrintStream(socket.getOutputStream());
        print("Welcome to Server!!!");
    }

    @Override
    public void run() {
        try {
            while (true) {
                String line = incommingConn.readLine();
                server.broadCast(clientId, line);
            }
        } catch (IOException ex) {
        }
    }

    public void print(String line) {
        outputConn.println(line);
        outputConn.flush();
    }

    public int getClientId() {
        return clientId;
    }

    public boolean isConnected() {
        return !socket.isClosed();
    }
}
