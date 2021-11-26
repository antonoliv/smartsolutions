/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saxion.smartsolutions.server.http;


import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer extends Thread {

    private ServerSocket server;
    private final HTTPRequestParser parser;
    private static final String CERT = "ssl/server.jks";

    public HTTPServer(HTTPRequestParser parser) throws IOException {
        //System.setProperty("javax.net.ssl.keyStore", CERT);
        //System.setProperty("javax.net.ssl.keyStorePassword", "forgotten");
        ServerSocketFactory sslF = ServerSocketFactory.getDefault();
        server = sslF.createServerSocket(8080);
        this.parser = parser;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        while (true) {
            try {
                Socket client = this.server.accept();
                HTTPRequest request = new HTTPRequest(client, parser);
                request.start();
            } catch (IOException ex) {
                System.out.println("Connection Failed");
            }
        }
    }

}
