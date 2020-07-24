package Bank;

import java.io.*;
import java.net.Socket;

public class BankTest {
    private static DataInputStream bankDataInputStream;
    private static DataOutputStream bankDataOutputStream;

    public static void main(String[] args) {
        connectToBank();
        testCommands();
    }

    private static void testCommands() {
        //...
    }

    private static void connectToBank() {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            System.out.println("successfully connected to bank.");
            bankDataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            bankDataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("failed to connect to bank!");
        }
    }
}
