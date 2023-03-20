import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class FileReceiver {
    private static final int PACKET_SIZE = 1024;
    private static final int PORT = 1234;
    private static final String MULTICAST_ADDRESS = "224.0.0.1";
    
    public static void main(String[] args) throws IOException {
        MulticastSocket socket = new MulticastSocket(PORT);
        InetAddress groupAddress = InetAddress.getByName(MULTICAST_ADDRESS);
        socket.joinGroup(groupAddress);
        
        byte[] buffer = new byte[PACKET_SIZE];
        
        FileOutputStream fileOutputStream = new FileOutputStream("rrr.txt");
        
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, PACKET_SIZE);
            socket.receive(packet);
            
            byte[] data = packet.getData();
            int length = packet.getLength();
            fileOutputStream.write(data, 0, length);
            
            if (length < PACKET_SIZE) {
                break;
            }
        }
        
        socket.leaveGroup(groupAddress);
        socket.close();
        fileOutputStream.close();
        
        System.out.println("File received successfully.");
    }
}
