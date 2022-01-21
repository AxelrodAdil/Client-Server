package TCP;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cl {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true){
            Socket s = new Socket("localhost", 4999);
            OutputStream outputStream = s.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            System.out.println("Sending data-string to the server.");

            String str = sc.nextLine();
            dataOutputStream.writeUTF(str);
            dataOutputStream.flush();
            dataOutputStream.close();
            if(str.equals("bye")){
                s.close();
            }
        }
    }
}