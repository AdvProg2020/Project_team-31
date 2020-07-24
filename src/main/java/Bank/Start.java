package Bank;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Start {
    public static int PORT_NUMBER = 8080;

    public static void main(String[] args) {
        try {
            if(Account.allAccount.size() == 0) {
                createMarketAccount();
            }
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createMarketAccount() {
        new Account("market", "account", "market", "1234");
    }

    public static void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
        Account.fileToLog();
        Receipt.fileToLog();
        while (true) {
            Socket clientSocket;
            try {
                System.out.println("Waiting for Client...");
                clientSocket = serverSocket.accept();
                System.out.println("A client Connected!");
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                new ClientHandler(dataInputStream, dataOutputStream).start();
            } catch (Exception e) {
                System.err.println("Error in accepting client!");
                break;
            }
        }
    }

    static class ClientHandler extends Thread {
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;

        public ClientHandler(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String request = dataInputStream.readUTF();
                    if(request == null) {
                        Account.logToFile();
                        Receipt.logToFile();
                    }
                    if (request.equals("exit")) {
                        Account.logToFile();
                        Receipt.logToFile();
                        break;

                    }
                    dataOutputStream.writeUTF(BankProcess.getInstance().answer(request));
                    dataOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    Account.logToFile();
                    Receipt.logToFile();
                }
            }
        }
    }
}
