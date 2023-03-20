import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class FileSender {
    private static final int PACKET_SIZE = 1024;
    private static final int PORT = 1234;
    private static final String MULTICAST_ADDRESS = "224.0.0.1";
    
    public static void main(String[] args) throws IOException {
       
        File file = new File("rr.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        
        MulticastSocket socket = new MulticastSocket();
        InetAddress groupAddress = InetAddress.getByName(MULTICAST_ADDRESS);
        socket.joinGroup(groupAddress);
        
      
        byte[] buffer = new byte[PACKET_SIZE];
        
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            DatagramPacket packet = new DatagramPacket(buffer, bytesRead, groupAddress, PORT);
            socket.send(packet);
            System.out.println("File sent successfully.");

        }
        
        socket.leaveGroup(groupAddress);
        socket.close();
        fileInputStream.close();
        
    }
}
