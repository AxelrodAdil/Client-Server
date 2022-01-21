package TCP;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Ser {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(4999);
        System.out.println("Server awaiting connection...");
        System.out.println("Blocking call, this will wait until a connection is attempted on this port.");
        System.out.println("The tcp connection used by Socket needs to be connected before sending data.");

        while (true) {
            Socket s = ss.accept();
            System.out.println("Client connected. Connection from " + s);

            InputStream inputStream = s.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String str = dataInputStream.readUTF();
            System.out.println(str);
            if(str.equals("bye")){
                System.out.println("bye from server");
                ss.close();
                s.close();
            }
        }
    }
}