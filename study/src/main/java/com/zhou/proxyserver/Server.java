package com.zhou.proxyserver;

import com.zhou.proxyserver.utils.CloseUtil;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private boolean isRunning = true;
    private ServerSocket server;

    public void stop() {
        isRunning = false;
        CloseUtil.close(server);
    }

    private void services() throws IOException {
        while (isRunning){
            new Thread(new Dispatch(server.accept())).start();
        }
    }

    public void start(){
        try {
            server = new ServerSocket(8443);
            this.services();
        } catch (IOException e) {
            this.stop();
        }

    }

    public static void main(String[] args) {
        new Server().start();
    }
}
