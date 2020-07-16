package Server;

import Controller.LoginController;
import Controller.SaveAndLoadFiles;
import Model.Supporter;
import Model.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.UnknownServiceException;
import java.util.HashMap;

public class ServerRunner {
    public static HashMap<Supporter,ArrayList<User>> supporters=new HashMap<>();

    public static void main(String[] args) {
//        SaveAndLoadFiles.start();
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SaveAndLoadFiles.end();
    }

    public static void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket clientSocket;
            try {
                System.out.println("Waiting for Client...");
                clientSocket = serverSocket.accept();
                System.out.println("A client Connected!");
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                new ClientHandler(clientSocket, dataOutputStream, dataInputStream).start();
            } catch (Exception e) {
                System.err.println("Error in accepting client!");
                break;
            }
        }
    }

    static class ClientHandler<T> extends Thread {
        private Socket clientSocket;
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;
        private String token;
        private User user;
        private User tempUser;
        Process process = new Process();

        public ClientHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
            this.clientSocket = clientSocket;
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            token = "null";
            user = null;
            tempUser = new User();
        }

        @Override
        public void run() {
            handleRequest();
        }

        private void handleRequest() {
            while (true) {
                String request = null;
                String output = "";
                try {
                    request = dataInputStream.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(request);
                if (!jsonObject.get("token").toString().equals(token)) {
                    output = getStringOfWrongToken();
                } else {
                    output = process.answerClient(jsonObject, user).toString();
                }
                try {
                    dataOutputStream.writeUTF(output);
                    dataOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String getStringOfWrongToken() {
            System.out.println("token is invalid");
            JsonObject isInvalid = new JsonObject();
            isInvalid.addProperty("isValid", false);
            return String.valueOf(isInvalid);
        }
    }
}
