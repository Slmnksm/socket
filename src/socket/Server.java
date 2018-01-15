/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author S.KasımYurtaslan
 */
public class Server {

    ServerSocket serverSocket;
    List<RequestHandler> requestHandlers = new ArrayList();
    AtomicInteger clientIdGenerator = new AtomicInteger(1);

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.init();
        server.serverCycle();
    }

    public void serverCycle() throws IOException {
        System.out.println("Server acik");
        do {
            Socket socket = serverSocket.accept();

            RequestHandler requestHandler = new RequestHandler(clientIdGenerator.incrementAndGet(), this, socket);
            System.out.println("Yeni baglanti : " + socket.getInetAddress().toString() + ",clientId:" + requestHandler.getClientId());
            requestHandlers.add(requestHandler);
            requestHandler.start();

        } while (true);
    }

    private void init() throws IOException {
        serverSocket = new ServerSocket(80);  //1-2u16
    }

    public void broadCast(int clientId, String line) {

        for (int i = requestHandlers.size() - 1; i >= 0; i--) {
            RequestHandler requestHandler = requestHandlers.get(i);
            if (!requestHandler.isConnected()) {
                requestHandlers.remove(i);
                continue;
            }

            if (clientId == requestHandler.getClientId()) {
                continue;
            }

            requestHandler.print(line);

        }
    }
}
