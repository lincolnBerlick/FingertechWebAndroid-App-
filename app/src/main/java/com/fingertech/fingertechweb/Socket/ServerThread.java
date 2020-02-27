package com.fingertech.fingertechweb.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Handler;

public class ServerThread implements Runnable {

    private ServerSocket serverSocket;

    Handler updateConversationHandler;

    Thread serverThread = null;


    @Override
    public void run() {
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(9000);
        }catch (Exception e){

        }

        while (!Thread.currentThread().isInterrupted()){
            try {
                socket = serverSocket.accept();
                CommunicationThread   commthread = new CommunicationThread(socket);
            }catch (Exception e){

            }
        }

    }
    class CommunicationThread implements Runnable {

        private Socket clientSocket;

        private BufferedReader input;

        public CommunicationThread(Socket clientSocket) {

            this.clientSocket = clientSocket;

            try {

                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                try {

                    String read = input.readLine();

                    //updateConversationHandler.post(new updateUIThread(read));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class updateUIThread implements Runnable {
        private String msg;

        public updateUIThread(String str) {
            this.msg = str;
        }

        @Override
        public void run() {
            //text.setText(text.getText().toString()+"Client Says: "+ msg + "\n");
        }
    }
}
