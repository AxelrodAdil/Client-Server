package UDP;

import java.io.IOException;
import java.net.*;
import java.util.Calendar;
import java.util.Scanner;

public class Client {
    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private byte[] buffer;
    static int countMaxMessage = 0;
    static Calendar timeBan = Calendar.getInstance();
    static byte callCount = 0;
    static boolean bool = true;

    public Client(DatagramSocket datagramSocket, InetAddress inetAddress) {
        this.datagramSocket = datagramSocket;
        this.inetAddress = inetAddress;
    }

    static void timer() {
        Calendar now = Calendar.getInstance();
        int compareToTime = now.compareTo(timeBan);
        //System.out.println("Boolean compareTo: " + compareToTime);
        if (compareToTime >= 0) {
            System.out.println("\n\nNOW YOU CAN SEND THE MESSAGE. DON'T SPAM!");
            bool = true;
            callCount = 0;
        }
    }

    static void change() {
        countMaxMessage = 0;
        bool = false;

        Calendar now = Calendar.getInstance();
        //now.add(Calendar.MINUTE, 1);
        now.add(Calendar.SECOND, 10);
        timeBan = now;
        System.out.println("\nYou have been banned to spam for 10 second (5 minute). You can send message after: "
                + timeBan.get(Calendar.HOUR_OF_DAY)
                + ":"
                + timeBan.get(Calendar.MINUTE)
                + ":"
                + timeBan.get(Calendar.SECOND));
    }

    public void sendThanReceive() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            if (countMaxMessage <= 10 && bool) {
                try {
                    String messageToSend = sc.nextLine();
                    buffer = messageToSend.getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 1234);
                    datagramSocket.send(datagramPacket);
                    datagramSocket.receive(datagramPacket);
                    String messageFromServer = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                    System.out.println("The server says you said: " + messageFromServer);
                    countMaxMessage++;
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    break;
                }
            } else {
                if (callCount == 0) {
                    change();
                    callCount = 1;
                }
                timer();
            }
        }
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("localhost");
        Client client = new Client(datagramSocket, inetAddress);
        System.out.println("Send datagram packets to a server.");
        client.sendThanReceive();
    }
}