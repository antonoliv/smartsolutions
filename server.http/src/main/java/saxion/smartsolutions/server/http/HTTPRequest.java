/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saxion.smartsolutions.server.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class HTTPRequest extends Thread {

    private final Socket client;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final HTTPRequestParser parser;

    public HTTPRequest(Socket client, HTTPRequestParser parser) throws IOException {
        this.client = client;
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());
        this.parser = parser;
    }

    public void run() {
        try {
            HTTPMessage request = new HTTPMessage(in);
            HTTPMessage response = parser.parse(request);
            response.send(out);
        } catch (IOException ex) {
            // nothing in message or blank request
        } finally {
            try {
                client.close();
            } catch (IOException ex) {
                System.out.println("Problems closing client socket");
            }
        }
    }
}



