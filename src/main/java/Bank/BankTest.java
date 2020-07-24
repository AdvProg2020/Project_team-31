package Bank;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BankTest {
    private static DataInputStream bankDataInputStream;
    private static DataOutputStream bankDataOutputStream;

    public static void main(String[] args) {
        connectToBank();
        testCommands();
    }

    private static void testCommands() {
        Scanner input = new Scanner(System.in);
        while (true) {
            try {
                bankDataOutputStream.writeUTF(input.nextLine());
                bankDataOutputStream.flush();
                System.out.println("input:" + bankDataInputStream.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
