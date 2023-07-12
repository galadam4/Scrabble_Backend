package Part3;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static java.lang.Thread.sleep;

/*
 * MyServer is responsible for creating and managing
 * the server socket to handle client connections
 */
public class MyServer {
    private int port;
    private ClientHandler ch;
    private volatile boolean stop;

    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
        stop = false;
    }


    /*
     * this method starts the server in a new thread,
     * and invokes runServer() which contains the main logic for the server
     */
    public void start()  {
        new Thread(() -> {
            try {
                runServer();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();


    }



    /*
     * This method is executed in a loop until the stop variable is set to true.
     * It creates a new ServerSocket instance bound to the specified port.
     * It sets the timeout to 1000, which is the time the server will accept new client
     */
    private void runServer() throws Exception {
        ServerSocket server = new ServerSocket(port);
        server.setSoTimeout(1000);
        while (!stop) {
            try {
                Socket aClient = server.accept();
                try {
                    ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                    aClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    ch.close();

                }
            } catch (SocketTimeoutException r) {
                r.printStackTrace();
            }
        }
        server.close();
    }

    public void stop() {
        stop = true;
    }

    public void close() {
        stop();
    }
}
