package Bank;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Start {
    public static int PORT_NUMBER = 8080;
    public static final String MARKET_USERNAME = "market";
    public static final String MARKET_PASSWORD = "1234";
    public static final int MARKET_ACCOUNT_ID = 1;

    public static void main(String[] args) {
        Account.fileToLog();
        Receipt.fileToLog();
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
        Account account =new Account("market", "account", MARKET_USERNAME, MARKET_PASSWORD);
        account.inventory = 100000;
    }

    public static void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
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
                } catch (Exception e) {
                    e.printStackTrace();
                    Account.logToFile();
                    Receipt.logToFile();
                    break;
                }
            }
        }
    }
}
